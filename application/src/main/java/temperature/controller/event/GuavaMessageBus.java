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

package temperature.controller.event;

import com.google.common.eventbus.EventBus;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public class GuavaMessageBus implements MessageBus
{
    private final EventBus bus;

    public GuavaMessageBus()
    {
        bus = new EventBus("Temperature controller events");
    }

    @Override
    public <E extends DomainEvent> void publish(final E event)
    {
        bus.post(event);
    }

    @Override
    public void subscribe(final Object recipient)
    {
        bus.register(recipient);
    }

    @Override
    public void unsubscribe(final Object recipient)
    {
        bus.unregister(recipient);
    }
}
