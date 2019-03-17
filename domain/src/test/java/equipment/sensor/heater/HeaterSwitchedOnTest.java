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

package equipment.sensor.heater;

import kernel.EqualityContract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
class HeaterSwitchedOnTest implements EqualityContract<HeaterSwitchedOn>
{
    HeaterSwitchedOn event = HeaterSwitchedOn.with("Mash Tun 1");

    @Test
    void accessToLabel()
    {
        assertThat(event.getLabel()).isEqualTo("Mash Tun 1");
    }

    @Override
    public Class<HeaterSwitchedOn> getTypeClass()
    {
        return HeaterSwitchedOn.class;
    }

    @Test
    void toStringContains()
    {
        assertThat(event.toString()).contains("timestamp=", "label=");
    }

}
