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
public final class Output extends Term
{
    private Output(final double value)
    {
        super(value);
    }

    public static Output valueOf(final Term pTerm, final Term iTerm, final Term dTerm)
    {
        return new Output(pTerm.value + iTerm.value + dTerm.value);
    }

    public static Output valueOf(final double value)
    {
        return new Output(value);
    }

    public Boolean isBelow(final Output other)
    {
        System.out.println(value + " < " + other.value);
        return Boolean.valueOf(value < other.value);
    }

    public static Output zero()
    {
        return new Output(0);
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
        final Output other = (Output) obj;
        if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
            return false;
        return true;
    }
}
