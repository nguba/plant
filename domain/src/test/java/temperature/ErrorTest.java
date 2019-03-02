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

package temperature;

import kernel.EqualityContract;
import temperature.Error;
import temperature.Temperature;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
class ErrorTest implements EqualityContract<Error>
{
    private static final Temperature SETPOINT = Temperature.celsius(50.0);

    @Test
    @DisplayName("error is 10.0 when processValue is 10.0 above setPoint ")
    void calculateErrorNegative()
    {
        assertThat(Error.from(SETPOINT, Temperature.celsius(60.0))).isEqualTo(Error.valueOf(-10.0));
    }

    @Test
    @DisplayName("error is 10.0 when processValue is 10.0 below setPoint ")
    void calculateErrorPositive()
    {
        assertThat(Error.from(SETPOINT, Temperature.celsius(40.0))).isEqualTo(Error.valueOf(10.0));
    }

    @Test
    @DisplayName("error is zero when processValue and setPoint are equal")
    void calculateErrorZero()
    {
        assertThat(Error.from(SETPOINT, Temperature.celsius(50.0))).isEqualTo(Error.zero());
    }

    @Override
    public Class<Error> getType()
    {
        return Error.class;
    }

    @Test
    @DisplayName("string representation of value is returned")
    void toStringHasValueOnly()
    {
        assertThat(Error.valueOf(22.3).toString()).isEqualTo("22.3");
    }

    @Test
    void zero()
    {
        assertThat(Error.zero()).isEqualTo(Error.valueOf(0));
    }
}
