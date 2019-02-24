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

import process.kernel.Entity;
import process.temperature.Temperature;

import java.time.Duration;
import java.util.UUID;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class Segment implements Entity<UUID>
{
    private final String      label;
    private final Duration    duration;
    private final Temperature setPoint;
    private final UUID        identity;

    public Segment(final UUID identity,
                   final String label,
                   final Duration duration,
                   final Temperature setPoint)
    {
        this.identity = identity;
        this.label = label;
        this.duration = duration;
        this.setPoint = setPoint;
    }

    @Override
    public int hashCode()
    {
        final int prime  = 31;
        int       result = 1;
        result = (prime * result) + ((identity == null) ? 0 : identity.hashCode());
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
        final Segment other = (Segment) obj;
        if (identity == null) {
            if (other.identity != null)
                return false;
        } else if (!identity.equals(other.identity))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("Segment [label=").append(label).append(", duration=").append(duration)
                .append(", setPoint=").append(setPoint).append(", identity=").append(identity)
                .append("]");
        return builder.toString();
    }

    public boolean isActive(final Temperature processValue)
    {
        return setPoint.isBelowOrAt(processValue);
    }

    @Override
    public UUID getIdentity()
    {
        return identity;
    }
}
