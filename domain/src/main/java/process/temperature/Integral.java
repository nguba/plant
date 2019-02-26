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

package process.temperature;

import java.time.Duration;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class Integral extends Gain
{
    public static Integral valueOf(final double value)
    {
        return new Integral(value);
    }

    public static Integral zero()
    {
        return Integral.valueOf(0);
    }

    private Integral(final double value)
    {
        super(value);
    }

    /**
     * Adds long-term precision to a control loop
     *
     * @param error
     *            difference between sP and pV
     * @param duration
     *            between the last and current sample time
     * @return the integral termMa
     */
    public Term termFor(final Error error, final Duration duration)
    {
        return Term.valueOf(error.value * duration.getSeconds() * value);
    }

}
