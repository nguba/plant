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
package process.temperature;

import java.time.Duration;

/**
 *
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class Derivative extends Magnitude
{
    private Derivative(final double value)
    {
        super(value);
    }

    public static Derivative valueOf(final double value)
    {
        return new Derivative(value);
    }

    public static Derivative zero()
    {
        return Derivative.valueOf(0);
    }

    /**
     * This gives a rough estimate of the velocity (delta position/sample time), which predicts
     * where the position will be in a while.
     *
     * @param error
     *            difference between sP and pV
     * @param lastError
     *            difference between sP and pV calculated previously
     * @param duration
     *            between the last and current sample time
     * @return the derivative term
     */
    public Term termFor(final Error error, final Error lastError, final Duration duration)
    {
        if (duration.getSeconds() == 0)
            return Term.zero();

        final double dError = error.value - lastError.value;
        return Term.valueOf((dError / duration.getSeconds()) * value);
    }

}
