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

package equipment.heater;

import equipment.heater.Heater;
import equipment.heater.HeaterSwitchedOff;
import equipment.heater.HeaterSwitchedOn;
import equipment.sensor.Sensor;
import equipment.sensor.SensorReadingUpdated;
import kernel.DomainEvent;
import kernel.EventPublisher;
import temperature.Temperature;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public class HeaterMock implements Heater, Sensor
{
    ScheduledExecutorService element = Executors.newScheduledThreadPool(2);

    AtomicBoolean heat = new AtomicBoolean();

    private final EventPublisher publisher;

    AtomicInteger value = new AtomicInteger();

    public HeaterMock(final EventPublisher publisher)
    {
        this.publisher = publisher;
        element.scheduleAtFixedRate(() -> {
            if (heat.get())
                value.getAndIncrement();
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    @Override
    public Temperature currentTemperature()
    {
        final Temperature temperature = Temperature.celsius(value.get() / 100.0);
        raise(SensorReadingUpdated.with(temperature));
        return temperature;
    }

    private void raise(final DomainEvent event)
    {
        publisher.publish(event);
    }

    @Override
    public void switchOff()
    {
        heat.set(false);
        raise(HeaterSwitchedOff.with("Mock Heater"));
    }

    @Override
    public void switchOn()
    {
        heat.set(true);
        raise(HeaterSwitchedOn.with("Mock Heater"));
    }
}
