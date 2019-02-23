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
package me.nguba.plant;

import java.time.Duration;
import java.time.Instant;

/**
 *
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public class PidStrategy
{
    private double  pGain;
    private double  iGain;
    private Instant lastTime;
    private double  lastError;
    private double  dGain;

    protected double error(final double sP, final double pV)
    {
        return sP - pV;
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
    protected double pTerm(final double error, final double pGain)
    {
        return error * pGain;
    }

    public void setP(final double proportionalGain)
    {
        this.pGain = proportionalGain;
    }

    public double update(final double sP, final double pV)
    {
        final double error = error(sP, pV);

        final Instant  now        = Instant.now();
        final Duration timeChange = timeChange(lastTime, now);
        final double   dTerm      = dTerm(error, lastError, dGain, timeChange);
        final double   pTerm      = pTerm(error, pGain);
        final double   iTerm      = iTerm(error, iGain, timeChange);

        lastTime = now;
        lastError = error;

        return pTerm + iTerm + dTerm;
    }

    public Instant getLastTime()
    {
        return lastTime;
    }

    protected Duration timeChange(final Instant previous, final Instant current)
    {
        if (previous == null)
            return Duration.ZERO;
        return Duration.between(previous, current);
    }

    public void setI(final double iGain)
    {
        this.iGain = iGain;
    }

    public void setD(final double dGain)
    {
        this.dGain = dGain;
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
    protected double iTerm(final double error, final double iGain, final Duration duration)
    {
        return error * duration.getSeconds() * iGain;
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
    protected double dTerm(final double error,
                           final double lastError,
                           final double dGain,
                           final Duration duration)
    {
        if (duration.isZero())
            return 0.0;

        final double dError = error - lastError;
        return (dError / duration.getSeconds()) * dGain;
    }

}
