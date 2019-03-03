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

import kernel.EqualityContract;
import temperature.Temperature;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
class TemperatureUpdatedTest implements EqualityContract<TemperatureUpdated>
{
    Temperature temperature = Temperature.celsius(55.3);

    @Override
    public Class<TemperatureUpdated> getTypeClass()
    {
        return TemperatureUpdated.class;
    }

    @Test
    void testAccesssToTemperatureValue()
    {
        assertThat(TemperatureUpdated.with(temperature).getTemperature())
                .isEqualTo(Temperature.celsius(55.3));
    }

    @Test
    void toStringContainsEssentials()
    {
        assertThat(TemperatureUpdated.with(temperature).toString()).contains("timestamp=",
                                                                             "temperature=");
    }

}
