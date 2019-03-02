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

package temperature.controller;

import temperature.Temperature;
import temperature.controller.Sensor;
import temperature.controller.Switch;
import temperature.controller.event.DomainEvent;
import temperature.controller.event.HeaterSwitchedOff;
import temperature.controller.event.HeaterSwitchedOn;
import temperature.controller.event.MessageBus;
import temperature.controller.event.TemperatureUpdated;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public class HeaterMock implements Switch, Sensor
{
    ScheduledExecutorService element = Executors.newScheduledThreadPool(2);

    AtomicBoolean heat = new AtomicBoolean();

    AtomicInteger value = new AtomicInteger();

    private final MessageBus bus;

    public HeaterMock(final MessageBus bus)
    {
        this.bus = bus;
        element.scheduleAtFixedRate(() -> {
            if (heat.get())
                value.getAndIncrement();
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    @Override
    public Temperature currentTemperature()
    {
        final Temperature temperature = Temperature.celsius(value.get() / 100.0);
        raise(TemperatureUpdated.with(temperature));
        return temperature;
    }

    private void raise(final DomainEvent event)
    {
        bus.publish(event);
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
