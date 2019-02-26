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
public final class DigitalPid implements Pid<Boolean>
{
    private final Pid<Output> delegate;

    long               startTime;
    private final long windowSize;

    public DigitalPid(final Pid<Output> delegate, final long windowSize)
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

        final Output output = delegate.update(sP, pV);
        return output.isAbove(now - startTime);

    }

    @Override
    public void setProportional(final Proportional pGain)
    {
        delegate.setProportional(pGain);
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

}
