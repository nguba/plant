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

package temperature.pwm;

import kernel.DomainEvent;
import kernel.EntityEqualityContract;
import kernel.EventPublisher;
import temperature.Temperature;
import temperature.pid.Derivative;
import temperature.pid.Integral;
import temperature.pid.Pid;
import temperature.pid.Proportional;
import temperature.pwm.Pwm;
import temperature.pwm.PwmChanged;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 *
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
class PwmTest implements EventPublisher, EntityEqualityContract<UUID, Pwm>
{
    DomainEvent event;
    final Pid   pid = Pid.withIdentityOf(UUID.randomUUID());
    Pwm         pwm;

    @Test
    void falseWhenNoHeatingIsNeeded() throws Exception
    {
        Thread.sleep(1000);
        pid.setIntegral(Integral.valueOf(100.01));
        assertThat(pwm.update(Temperature.celsius(1), Temperature.celsius(10))).isFalse();
    }

    @Test
    void falseWhenNoHeatingNeeded() throws Exception
    {
        assertThat(pwm.update(Temperature.celsius(10), Temperature.celsius(10.01))).isFalse();
    }

    @Test
    void falseWhenWindowSizeEqualsPidTerm() throws Exception
    {
        Thread.sleep(1000);
        pid.setIntegral(Integral.valueOf(100));
        assertThat(pwm.update(Temperature.celsius(10), Temperature.celsius(0))).isFalse();
    }

    @Test
    void fireEventOnPidTermChange()
    {
        pwm.setMessageBus(this);
        pwm.update(Temperature.celsius(10), Temperature.celsius(0));
        assertThat(event).isInstanceOf(PwmChanged.class);
    }

    @Override
    public Class<Pwm> getTypeClass()
    {
        return Pwm.class;
    }

    @Override
    public <E extends DomainEvent> void publish(final E event)
    {
        this.event = event;
    }

    @BeforeEach
    void setUp() throws Exception
    {

        pid.setIntegral(Integral.valueOf(1001));
        pid.setProportional(Proportional.zero());
        pid.setDerivative(Derivative.zero());

        pwm = Pwm.with(UUID.randomUUID(), pid, 1000);
    }

    @Test
    void trueWhenHeatingIsNeeded() throws Exception
    {
        Thread.sleep(1000);
        pid.setIntegral(Integral.valueOf(101));
        assertThat(pwm.update(Temperature.celsius(10), Temperature.celsius(0))).isTrue();
    }
}
