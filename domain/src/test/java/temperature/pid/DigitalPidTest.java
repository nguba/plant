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

package temperature.pid;

import kernel.DomainEvent;
import kernel.EntityEqualityContract;
import kernel.MessageBus;
import temperature.Temperature;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 *
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
class DigitalPidTest implements MessageBus, EntityEqualityContract<UUID, DigitalPid>
{
    DomainEvent event;
    DigitalPid  pid = DigitalPid
            .with(UUID.randomUUID(), AnalogPid.withIdentityOf(UUID.randomUUID()), 1000);

    @Test
    void falseWhenNoHeatingNeeded() throws Exception
    {
        assertThat(pid.update(Temperature.celsius(10), Temperature.celsius(10.01))).isFalse();
    }

    @Test
    void fireEventOnPidTermChange()
    {
        pid.setMessageBus(this);
        pid.update(Temperature.celsius(10), Temperature.celsius(0));
        assertThat(event).isInstanceOf(DigitalPidChanged.class);
    }

    @Override
    public Class<DigitalPid> getTypeClass()
    {
        return DigitalPid.class;
    }

    @Override
    public <E extends DomainEvent> void publish(final E event)
    {
        this.event = event;
        System.out.println(event);
    }

    @BeforeEach
    void setUp() throws Exception
    {
        Thread.sleep(1000);
        pid.setIntegral(Integral.valueOf(1020));
        pid.setProportional(Proportional.zero());
        pid.setDerivative(Derivative.zero());
    }

    @Override
    public void subscribe(final Object recipient)
    {
    }

    @Test
    void trueWhenHeatingIsNeeded() throws Exception
    {
        assertThat(pid.update(Temperature.celsius(10), Temperature.celsius(0))).isTrue();
    }

    @Override
    public void unsubscribe(final Object recipient)
    {
    }
}
