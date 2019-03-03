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

import kernel.Entity;
import kernel.MessageBus;
import temperature.Temperature;
import temperature.pid.event.DigitalPidChanged;

import java.util.UUID;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class DigitalPid implements Pid<Boolean>, Entity<UUID>
{
    private final AnalogPid delegate;
    private long            startTime = System.currentTimeMillis();
    private final long      windowSize;
    private MessageBus      messageBus;
    private final UUID      identity;

    private DigitalPid(final UUID identity, final AnalogPid delegate, final long windowSize)
    {
        this.identity = identity;
        this.delegate = delegate;
        this.windowSize = windowSize;
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

    @Override
    public void setProportional(final Proportional proportional)
    {
        delegate.setProportional(proportional);
    }

    @Override
    public Boolean update(final Temperature sP, final Temperature pV)
    {
        final long window = System.currentTimeMillis() - startTime;
        if (window > windowSize)
            startTime += windowSize;

        final Output pidTerm = delegate.update(sP, pV);
        if (messageBus != null)
            messageBus.publish(DigitalPidChanged.with(pidTerm, window));
        return Output.valueOf(window).isBelow(pidTerm);
    }

    public void setMessageBus(final MessageBus messageBus)
    {
        this.messageBus = messageBus;
    }

    @Override
    public UUID getIdentity()
    {
        return identity;
    }

    public static DigitalPid with(final UUID identity,
                                  final AnalogPid analogPid,
                                  final int windowSize)
    {
        return new DigitalPid(identity, analogPid, windowSize);
    }

    @Override
    public int hashCode()
    {
        final int prime  = 31;
        int       result = 1;
        result = (prime * result) + ((identity == null) ? 0 : identity.hashCode());
        return result;
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
}
