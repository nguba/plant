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
import java.time.Instant;

/**
 *
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public class AnalogPid implements Pid<Output>
{
    private Gain      pGain     = Gain.zero();
    private Gain      iGain     = Gain.zero();
    private Instant   lastTime  = Instant.now();
    private Magnitude lastError = Magnitude.zero();
    private Gain      dGain     = Gain.zero();

    @Override
    public void setP(final Gain pGain)
    {
        this.pGain = pGain;
    }

    @Override
    public Output update(final Temperature sP, final Temperature pV)
    {
        final Magnitude error      = Magnitude.error(sP, pV);
        final Instant   now        = Instant.now();
        final Duration  timeChange = timeChange(lastTime, now);
        final Magnitude dTerm      = Magnitude.dTerm(error, lastError, dGain, timeChange);
        final Magnitude pTerm      = Magnitude.pTerm(error, pGain);
        final Magnitude iTerm      = Magnitude.iTerm(error, iGain, timeChange);

        lastTime = now;
        lastError = error;

        return Output.valueOf(pTerm, iTerm, dTerm);
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

    @Override
    public void setI(final Gain iGain)
    {
        this.iGain = iGain;
    }

    @Override
    public void setD(final Gain dGain)
    {
        this.dGain = dGain;
    }
}
