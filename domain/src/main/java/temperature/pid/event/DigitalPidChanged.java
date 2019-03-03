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

package temperature.pid.event;

import kernel.DomainEvent;
import temperature.pid.Output;

/**
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public class DigitalPidChanged extends DomainEvent
{
    private final Output output;
    private final long   window;

    private DigitalPidChanged(final Output output, final long window)
    {
        this.output = output;
        this.window = window;
    }

    public static DigitalPidChanged with(final Output output, final long window)
    {
        return new DigitalPidChanged(output, window);
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("DigitalPidChanged [output=").append(output).append(", window=")
                .append(window).append(", timestamp=").append(timestamp).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime  = 31;
        int       result = super.hashCode();
        result = (prime * result) + ((output == null) ? 0 : output.hashCode());
        return result;
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
        final DigitalPidChanged other = (DigitalPidChanged) obj;
        if (output == null) {
            if (other.output != null)
                return false;
        } else if (!output.equals(other.output))
            return false;
        return true;
    }
}
