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
    private final AnalogPid delegate;
    private long            startTime = System.currentTimeMillis();
    private final long      windowSize;

    public DigitalPid(final AnalogPid delegate, final long windowSize)
    {
        this.delegate = delegate;
        this.windowSize = windowSize;
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

    @Override
    public void setProportional(final Proportional proportional)
    {
        delegate.setProportional(proportional);
    }

    @Override
    public Boolean update(final Temperature sP, final Temperature pV)
    {
        final long window = System.currentTimeMillis() - startTime;
        System.out.println(window);
        if (window > windowSize)
            startTime += windowSize;

        final Output pidTerm = delegate.update(sP, pV);
        return Output.valueOf(window).isBelow(pidTerm);
    }

}
