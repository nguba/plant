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

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class Proportional extends Gain
{
    private Proportional(final double value)
    {
        super(value);
    }

    public static Proportional zero()
    {
        return Proportional.valueOf(0);
    }

    public static Proportional valueOf(final double value)
    {
        return new Proportional(value);
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    /**
     * In Proportional Only mode, the controller simply multiplies the Error by the Proportional
     * Gain (Kp) to get the controller output.
     *
     * @param error
     *            difference between sP and pV
     * @return the magnitude of the proportional term
     */
    public Term termFor(final Error error)
    {
        return Term.valueOf(error.value * value);
    }

}
