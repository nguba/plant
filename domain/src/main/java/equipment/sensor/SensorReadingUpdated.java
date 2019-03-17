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

package equipment.sensor;

import kernel.DomainEvent;
import temperature.Temperature;

import java.util.Objects;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class SensorReadingUpdated extends DomainEvent
{
    public static SensorReadingUpdated with(final Temperature temperature)
    {
        return new SensorReadingUpdated(temperature);
    }

    private final Temperature temperature;

    private SensorReadingUpdated(final Temperature temperature)
    {
        this.temperature = temperature;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SensorReadingUpdated other = (SensorReadingUpdated) obj;
        return Objects.equals(temperature, other.temperature);
    }

    public Temperature getTemperature()
    {
        return temperature;
    }

    @Override
    public int hashCode()
    {
        final int prime  = 31;
        int       result = super.hashCode();
        result = (prime * result) + Objects.hash(temperature);
        return result;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("SensorReadingUpdated [temperature=").append(temperature)
                .append(", timestamp=").append(timestamp).append("]");
        return builder.toString();
    }

}
