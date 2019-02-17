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
package me.nguba.plant.ddd;

/**
 * An object defined primarily by its identiy and <em>not</em> by their attributes.
 * <p>
 * Entities have special modelling and design considerations. They have lifecycles that can
 * radically change their form or content, but a thread of continuity must be maintained. They
 * represent a thread of identity that runs through time and often across distinct representations.
 * </p>
 * <p>
 * Sometimes such an object must be matched with another object even though their attributes differ.
 * An object must be distinguished from other objects even though they may have the same attributes.
 * </p>
 * <p>
 * Their identities must be defined so that they can be effectively tracked. Their class
 * definitions, responsiblities, atrributes and associations should revolve around who they are,
 * rather than the particular attributes they carry.
 * </p>
 *
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 * @param <I>
 * @see Service
 * @see ValueObject
 * @see Aggregate
 */
public interface Entity<I>
{
    I getIdentity();
}
