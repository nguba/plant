/*
    Copyright (C) 2019  Nicolai P. Guba

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package temperature.pwm;

import kernel.DomainEvent;
import kernel.Entity;
import kernel.EventPublisher;
import temperature.Temperature;
import temperature.pid.Output;
import temperature.pid.Pid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.UUID;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class Pwm implements Entity<UUID>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Pwm.class);

    public static Pwm with(final UUID identity, final Pid analogPid, final int windowSize)
    {
        return new Pwm(identity, analogPid, windowSize);
    }

    private final Pid      delegate;
    private final UUID     identity;
    private EventPublisher messageBus;
    private long           startTime = System.currentTimeMillis();
    private final long     windowSize;

    private Pwm(final UUID identity, final Pid delegate, final long windowSize)
    {
        this.identity = identity;
        this.delegate = delegate;
        this.windowSize = windowSize;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Pwm other = (Pwm) obj;
        return Objects.equals(identity, other.identity);
    }

    private void fireEvent(final long window, final Output pidTerm, final Boolean below)
    {
        if (below.booleanValue())
            raise(PwmIsOn.with(pidTerm, window));
        else raise(PwmIsOff.with(pidTerm, window));
    }

    @Override
    public UUID getIdentity()
    {
        return identity;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(identity);
    }

    private void raise(final DomainEvent event)
    {
        if (messageBus != null)
            messageBus.publish(event);
    }

    public void setMessageBus(final EventPublisher messageBus)
    {
        this.messageBus = messageBus;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("DigitalPid [identity=").append(identity).append(", delegate=")
                .append(delegate).append(", startTime=").append(startTime).append(", windowSize=")
                .append(windowSize).append(", messageBus=").append(messageBus).append("]");
        return builder.toString();
    }

    public Boolean update(final Temperature sP, final Temperature pV)
    {
        final long window = System.currentTimeMillis() - startTime;
        if (window > windowSize)
            startTime += windowSize;

        final Output  pidTerm = delegate.update(sP, pV);
        final Boolean output  = Output.valueOf(window).isBelow(pidTerm);

        LOGGER.debug("windowSize={}, pidTerm={}, output={}", windowSize, pidTerm, output);

        fireEvent(window, pidTerm, output);
        return output;
    }
}
