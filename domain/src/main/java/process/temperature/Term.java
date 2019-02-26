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
public class Term
{
    public static Term valueOf(final double value)
    {
        return new Term(value);
    }

    public static Term zero()
    {
        return Term.valueOf(0);
    }

    protected final double value;

    protected Term(final double value)
    {
        this.value = value;
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
        final Term other = (Term) obj;
        if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        final int prime  = 31;
        int       result = 1;
        long      temp;
        temp = Double.doubleToLongBits(value);
        result = prime * result + (int) (temp ^ temp >>> 32);
        return result;
    }

    @Override
    public String toString()
    {
        return String.valueOf(value);
    }

}
