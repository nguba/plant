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
package kernel.validation;

import kernel.ValueObject;

import org.assertj.core.util.Streams;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class Notifications implements Iterable<Failure>, ValueObject
{
    public static Notifications empty()
    {
        return new Notifications();
    }

    private final Set<Failure> errors = new LinkedHashSet<>();

    public void add(final Failure reason)
    {
        errors.add(reason);
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
        final Notifications other = (Notifications) obj;
        if (errors == null) {
            if (other.errors != null)
                return false;
        } else if (!errors.equals(other.errors))
            return false;
        return true;
    }

    public boolean has(final Failure reason)
    {
        return errors.contains(reason);
    }

    public boolean hasErrors()
    {
        return !errors.isEmpty();
    }

    @Override
    public int hashCode()
    {
        final int prime  = 31;
        int       result = 1;
        result = (prime * result) + (errors == null ? 0 : errors.hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        return Streams.stream(errors).map(Failure::toString).collect(Collectors.joining(", "));
    }

    @Override
    public Iterator<Failure> iterator()
    {
        return errors.iterator();
    }
}
