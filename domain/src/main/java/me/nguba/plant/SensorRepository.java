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

package me.nguba.plant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class SensorRepository
{
    private final Map<SensorId, Sensor> sensors = new ConcurrentHashMap<>();

    public void create(final Sensor sensor)
    {
        sensors.put(sensor.getId(), sensor);
    }

    public Sensor read(final SensorId id)
    {
        return sensors.get(id);
    }

    public void delete(final SensorId id)
    {
        sensors.remove(id);
    }

}
