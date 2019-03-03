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

package temperature.pid;

import kernel.DomainEvent;
import kernel.Entity;
import kernel.MessageBus;
import temperature.Temperature;

import java.util.UUID;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class DigitalPid implements Pid<Boolean>, Entity<UUID>
{
    public static DigitalPid with(final UUID identity,
                                  final AnalogPid analogPid,
                                  final int windowSize)
    {
        return new DigitalPid(identity, analogPid, windowSize);
    }

    private final AnalogPid delegate;
    private final UUID      identity;
    private MessageBus      messageBus;
    private long            startTime = System.currentTimeMillis();

    private final long windowSize;

    private DigitalPid(final UUID identity, final AnalogPid delegate, final long windowSize)
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
        final DigitalPid other = (DigitalPid) obj;
        if (identity == null) {
            if (other.identity != null)
                return false;
        } else if (!identity.equals(other.identity))
            return false;
        return true;
    }

    private void fireEvent(final long window, final Output pidTerm, final Boolean below)
    {
        if (below.booleanValue())
            raise(DigitalPidChangedToOn.with(pidTerm, window));
        else raise(DigitalPidChangedToOff.with(pidTerm, window));
    }

    @Override
    public UUID getIdentity()
    {
        return identity;
    }

    @Override
    public int hashCode()
    {
        final int prime  = 31;
        int       result = 1;
        result = prime * result + (identity == null ? 0 : identity.hashCode());
        return result;
    }

    private void raise(final DomainEvent event)
    {
        if (messageBus != null)
            messageBus.publish(event);
    }

    @Override
    public void setDerivative(final Derivative derivative)
    {
        delegate.setDerivative(derivative);
    }

    @Override
    public void setIntegral(final Integral integral)
    {
        delegate.setIntegral(integral);
    }

    public void setMessageBus(final MessageBus messageBus)
    {
        this.messageBus = messageBus;
    }

    @Override
    public void setProportional(final Proportional proportional)
    {
        delegate.setProportional(proportional);
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

    @Override
    public Boolean update(final Temperature sP, final Temperature pV)
    {
        final long window = System.currentTimeMillis() - startTime;
        if (window > windowSize)
            startTime += windowSize;

        final Output  pidTerm = delegate.update(sP, pV);
        final Boolean output  = Output.valueOf(window).isBelow(pidTerm);
        fireEvent(window, pidTerm, output);
        return output;
    }
}
