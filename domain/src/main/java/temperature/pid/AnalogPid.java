/*
    Copyright (C) 2018  Nicolai P. Guba

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
import temperature.Temperature;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 *
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class AnalogPid implements Pid<Output>, Entity<UUID>
{
    private Derivative   derivative   = Derivative.zero();
    private Integral     integral     = Integral.zero();
    private Error        lastError    = Error.zero();
    private Instant      lastTime     = Instant.now();
    private Proportional proportional = Proportional.zero();
    private final UUID   identity;

    private AnalogPid(final UUID identity)
    {
        this.identity = identity;
    }

    public static AnalogPid withIdentityOf(final UUID id)
    {
        return new AnalogPid(id);
    }

    public Instant getLastTime()
    {
        return lastTime;
    }

    @Override
    public void setDerivative(final Derivative derivative)
    {
        this.derivative = derivative;
    }

    @Override
    public void setIntegral(final Integral integral)
    {
        this.integral = integral;
    }

    @Override
    public void setProportional(final Proportional proportional)
    {
        this.proportional = proportional;
    }

    protected Duration timeChange(final Instant previous, final Instant current)
    {
        if (previous == null)
            return Duration.ZERO;
        return Duration.between(previous, current);
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("AnalogPid [id=").append(identity).append(", derivative=").append(derivative)
                .append(", integral=").append(integral).append(", lastError=").append(lastError)
                .append(", lastTime=").append(lastTime).append(", proportional=")
                .append(proportional).append("]");
        return builder.toString();
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
        final AnalogPid other = (AnalogPid) obj;
        if (identity == null) {
            if (other.identity != null)
                return false;
        } else if (!identity.equals(other.identity))
            return false;
        return true;
    }

    @Override
    public Output update(final Temperature sP, final Temperature pV)
    {
        final Error    error      = Error.from(sP, pV);
        final Instant  now        = Instant.now();
        final Duration timeChange = timeChange(lastTime, now);
        final Term     dTerm      = derivative.termFor(error, lastError, timeChange);
        final Term     pTerm      = proportional.termFor(error);
        final Term     iTerm      = integral.termFor(error, timeChange);

        lastTime = now;
        lastError = error;

        return Output.valueOf(pTerm, iTerm, dTerm);
    }

    @Override
    public UUID getIdentity()
    {
        return identity;
    }
}
