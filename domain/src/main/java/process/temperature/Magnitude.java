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
public class Magnitude
{
    protected final double value;

    private static final Magnitude zero = Magnitude.valueOf(0);

    protected Magnitude(final double value)
    {
        this.value = value;
    }

    public static Magnitude error(final Temperature sP, final Temperature pV)
    {
        return Magnitude.valueOf(sP.difference(pV));
    }

    public static Magnitude valueOf(final double value)
    {
        return new Magnitude(value);
    }

    public static Magnitude zero()
    {
        return zero;
    }

    /**
     * In Proportional Only mode, the controller simply multiplies the Error by the Proportional
     * Gain (Kp) to get the controller output.
     *
     * @param error
     *            difference between sP and pV
     * @param pGain
     *            the proportional gain
     * @return the proportional term
     */
    public static Magnitude pTerm(final Magnitude error, final Magnitude pGain)
    {
        return Magnitude.valueOf(error.value * pGain.value);
    }

    /**
     * Adds long-term precision to a control loop
     *
     * @param error
     *            difference between sP and pV
     * @param iGain
     *            the integral gain
     * @param duration
     *            between the last and current sample time
     * @return the integral term
     */
    public static Magnitude iTerm(final Magnitude error,
                                  final Magnitude iGain,
                                  final Duration duration)
    {
        return Magnitude.valueOf(error.value * duration.getSeconds() * iGain.value);
    }

    /**
     * This gives a rough estimate of the velocity (delta position/sample time), which predicts
     * where the position will be in a while.
     *
     * @param error
     *            difference between sP and pV
     * @param lastError
     *            difference between sP and pV calculated previously
     * @param dGain
     *            the derivative gain
     * @param duration
     *            between the last and current sample time
     * @return the derivative term
     */
    public static Magnitude dTerm(final Magnitude error,
                                  final Magnitude lastError,
                                  final Magnitude dGain,
                                  final Duration duration)
    {
        if (duration.getSeconds() == 0)
            return Magnitude.zero();

        final double dError = error.value - lastError.value;
        return Magnitude.valueOf((dError / duration.getSeconds()) * dGain.value);
    }

    @Override
    public int hashCode()
    {
        final int prime  = 31;
        int       result = 1;
        long      temp;
        temp = Double.doubleToLongBits(value);
        result = (prime * result) + (int) (temp ^ (temp >>> 32));
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
        final Magnitude other = (Magnitude) obj;
        if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return String.valueOf(value);
    }

    public static Magnitude of(final Magnitude pTerm, final Magnitude iTerm, final Magnitude dTerm)
    {
        return Magnitude.valueOf(pTerm.value + iTerm.value + dTerm.value);
    }

}
