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

import kernel.DomainEvent;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class HeaterSwitchedOn extends DomainEvent
{
    public static HeaterSwitchedOn with(final String label)
    {
        return new HeaterSwitchedOn(label);
    }

    private final String label;

    private HeaterSwitchedOn(final String label)
    {
        this.label = label;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        final HeaterSwitchedOn other = (HeaterSwitchedOn) obj;
        if (label == null) {
            if (other.label != null)
                return false;
        } else if (!label.equals(other.label))
            return false;
        return true;
    }

    public String getLabel()
    {
        return label;
    }

    @Override
    public int hashCode()
    {
        final int prime  = 31;
        int       result = super.hashCode();
        result = prime * result + (label == null ? 0 : label.hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("HeaterSwitchedOn [label=").append(label).append(", timestamp=")
                .append(timestamp).append("]");
        return builder.toString();
    }
}
