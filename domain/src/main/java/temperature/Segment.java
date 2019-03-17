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

package temperature;

import kernel.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class Segment implements Entity<UUID>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Segment.class);

    private final Duration    duration;
    private final UUID        identity;
    private final String      label;
    private final Temperature setPoint;
    private Instant           expiresOn;

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
    public boolean equals(final Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Segment other = (Segment) obj;
        return Objects.equals(identity, other.identity);
    }

    @Override
    public UUID getIdentity()
    {
        return identity;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(identity);
    }

    public boolean isComplete(final Temperature processValue)
    {
        final Instant now = Instant.now();

        if (processValue.isBelow(setPoint) && (expiresOn == null))
            return false;

        if (expiresOn == null)
            expiresOn = now.plus(duration);

        LOGGER.debug("{}: duration={}, now={}, expiresOn={}",
                     new Object[] { duration, label, now, expiresOn });

        return now.isAfter(expiresOn);
    }

    public Instant start()
    {
        expiresOn = Instant.now();
        return expiresOn;
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
}
