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
package process.kernel;

import java.time.LocalDateTime;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public abstract class DomainEvent
{
    protected final LocalDateTime timestamp;

    protected DomainEvent()
    {
        this(LocalDateTime.now());
    }

    protected DomainEvent(final LocalDateTime timestamp)
    {
        this.timestamp = timestamp;
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
        final DomainEvent other = (DomainEvent) obj;
        if (timestamp == null) {
            if (other.timestamp != null)
                return false;
        } else if (!timestamp.equals(other.timestamp))
            return false;
        return true;
    }

    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }

    @Override
    public int hashCode()
    {
        final int prime  = 31;
        int       result = 1;
        result = prime * result + (timestamp == null ? 0 : timestamp.hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("DomainEvent [timestamp=").append(timestamp).append("]");
        return builder.toString();
    }
}
