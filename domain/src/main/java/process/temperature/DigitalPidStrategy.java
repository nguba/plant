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
public final class DigitalPidStrategy implements Pid<Boolean>
{
    private final Pid<Double> delegate;

    long               startTime;
    private final long windowSize;

    public DigitalPidStrategy(final Pid<Double> delegate, final long windowSize)
    {
        this.delegate = delegate;
        this.windowSize = windowSize;
    }

    @Override
    public Boolean update(final Temperature sP, final Temperature pV)
    {
        if (startTime == 0)
            startTime = System.currentTimeMillis();

        final long now = System.currentTimeMillis();

        if ((now - startTime) > windowSize)
            startTime += windowSize;

        final Double  pidTerm          = delegate.update(sP, pV);
        final boolean isAboveThreshold = pidTerm.doubleValue() > (now - startTime);
        return Boolean.valueOf(isAboveThreshold);
    }

    @Override
    public void setD(final double dGain)
    {
        delegate.setD(dGain);
    }

    @Override
    public void setI(final double iGain)
    {
        delegate.setI(iGain);
    }

    @Override
    public void setP(final double pGain)
    {
        delegate.setP(pGain);
    }

}
