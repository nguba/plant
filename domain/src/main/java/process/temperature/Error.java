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
public final class Error extends Gain
{
    public static Error from(final Temperature sP, final Temperature pV)
    {
        return Error.valueOf(sP.difference(pV));
    }

    public static Error valueOf(final double value)
    {
        return new Error(value);
    }

    public static Error zero()
    {
        return Error.valueOf(0);
    }

    private Error(final double value)
    {
        super(value);
    }
}
