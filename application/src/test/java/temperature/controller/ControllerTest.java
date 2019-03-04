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

import kernel.validation.ValidationFailed;
import temperature.Profile;
import temperature.Segment;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
class ControllerTest
{
    private final Controller controller = new Controller();

    @Test
    @DisplayName("should report error when no segment is present")
    void executeProfileNoSegment() throws Exception
    {
        final Segment[]  segments = null;
        final HeaterMock heater   = new HeaterMock(null);
        assertThatExceptionOfType(ValidationFailed.class)
                .isThrownBy(() -> controller.executeProfile(heater,
                                                            heater,
                                                            Profile.withSegments("Wheat Beer",
                                                                                 segments)))
                .withMessage("Profile is empty.  You need to define at least one segment for the profile to execute.");
    }

}
