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
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class Pid implements Entity<UUID>
{
    protected static Duration timeChange(final Instant previous, final Instant current)
    {
        if (previous == null)
            return Duration.ZERO;
        return Duration.between(previous, current);
    }

    public static Pid withIdentityOf(final UUID id)
    {
        return new Pid(id);
    }

    private Derivative derivative = Derivative.zero();
    private final UUID identity;
    private Integral   integral   = Integral.zero();
    private Error      lastError  = Error.zero();

    private Instant lastTime = Instant.now();

    private Proportional proportional = Proportional.zero();

    private Pid(final UUID identity)
    {
        this.identity = identity;
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
        final Pid other = (Pid) obj;
        return Objects.equals(identity, other.identity);
    }

    @Override
    public UUID getIdentity()
    {
        return identity;
    }

    public Instant getLastTime()
    {
        return lastTime;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(identity);
    }

    public void setDerivative(final Derivative derivative)
    {
        this.derivative = derivative;
    }

    public void setIntegral(final Integral integral)
    {
        this.integral = integral;
    }

    public void setProportional(final Proportional proportional)
    {
        this.proportional = proportional;
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
}
