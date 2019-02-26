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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public class HeaterMock implements Switch
{
    ScheduledExecutorService element = Executors.newScheduledThreadPool(2);

    AtomicInteger value = new AtomicInteger();

    AtomicBoolean heat = new AtomicBoolean();

    // private final ScheduledFuture<?> on;

    public HeaterMock()
    {
        element.scheduleAtFixedRate(() -> {
            if (heat.get())
                value.getAndIncrement();
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    public Temperature currentTemperature()
    {
        // System.out.println(on);
        final double temperature = value.get() / 100.0;
        return Temperature.celsius(temperature);
    }

    @Override
    public void switchOn()
    {
        System.out.println("ON");
        heat.set(true);
    }

    @Override
    public void switchOff()
    {
        System.out.println("OFF");
        heat.set(false);
    }
}
