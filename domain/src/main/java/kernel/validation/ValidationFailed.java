/*
    Copyright (C) 2018  Nicolai P. Guba

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

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class ValidationFailed extends Exception
{
    private static final long serialVersionUID = -6165488948892216564L;

    public static ValidationFailed with(final Notifications notifications)
    {
        return new ValidationFailed(notifications.toString());
    }

    private ValidationFailed(final String message)
    {
        super(message);
    }
}
