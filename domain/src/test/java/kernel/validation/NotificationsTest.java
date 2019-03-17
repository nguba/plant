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

package kernel.validation;

import test.EqualityContract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
class NotificationsTest implements EqualityContract<Notifications>
{
    Notifications notifications = Notifications.empty();

    @Test
    void canIterateThroughFailures()
    {
        notifications.add(Failure.from("Failure #1"));
        assertThat(notifications).contains(Failure.from("Failure #1"));
    }

    @Test
    void createWithEmptyFailures()
    {
        assertThat(notifications).isEmpty();
    }

    @Override
    public Class<Notifications> getTypeClass()
    {
        return Notifications.class;
    }

    @Test
    void hasErrorsIsFalseWhenNoFailures()
    {
        assertThat(notifications.hasErrors()).isFalse();
    }

    @Test
    void hasErrorsIsTrueWhenFailures()
    {
        notifications.add(Failure.from("Failure #1"));
        assertThat(notifications.hasErrors()).isTrue();
    }

    @Test
    void returnsFalseWhenNotContainsFailure()
    {
        assertThat(notifications.has(Failure.from("Failure #1"))).isFalse();
    }

    @Test
    void returnsTrueWhenContainsFailure()
    {
        notifications.add(Failure.from("Failure #1"));
        assertThat(notifications.has(Failure.from("Failure #1"))).isTrue();
    }

    @Test
    void toStringReturnsContatenatedFailures()
    {
        notifications.add(Failure.from("Failure #1"));
        notifications.add(Failure.from("Failure #2"));
        notifications.add(Failure.from("Failure #3"));

        assertThat(notifications.toString()).isEqualTo("Failure #1, Failure #2, Failure #3");
    }
}
