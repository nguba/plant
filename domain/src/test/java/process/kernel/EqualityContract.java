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

package process.kernel;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public interface EqualityContract<T>
{
    @Test
    @DisplayName("Equality contract is implemented")
    default void equalityContract()
    {
        EqualsVerifier.forClass(getType()).usingGetClass().verify();
    }

    Class<T> getType();
}
