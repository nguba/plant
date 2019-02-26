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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class Profile implements Entity<String>, Iterable<Segment>
{
    public static Profile withSegments(final String name, final Segment... segments)
    {
        final List<Segment> schedule = new ArrayList<>(segments.length);
        for (final Segment segment : segments)
            schedule.add(segment);
        return new Profile(name, schedule);
    }

    private final String identity;

    private boolean running;

    private final List<Segment> segments;

    private Profile(final String identity, final List<Segment> segments)
    {
        this.identity = identity;
        this.segments = segments;
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
        final Profile other = (Profile) obj;
        if (identity == null) {
            if (other.identity != null)
                return false;
        } else if (!identity.equals(other.identity))
            return false;
        return true;
    }

    @Override
    public String getIdentity()
    {
        return identity;
    }

    @Override
    public int hashCode()
    {
        final int prime  = 31;
        int       result = 1;
        result = prime * result + (identity == null ? 0 : identity.hashCode());
        return result;
    }

    public boolean isRunning()
    {
        return running;
    }

    @Override
    public Iterator<Segment> iterator()
    {
        return segments.iterator();
    }

    public void start()
    {
        running = true;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("Profile [identity=").append(identity).append(", segments=").append(segments)
                .append("]");
        return builder.toString();
    }

}
