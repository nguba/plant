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
package process.temperature;

import process.kernel.EntityEqualityContract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.UUID;

/**
 *
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
class ProfileTest implements EntityEqualityContract<String, Profile>
{
    @Test
    void toStringResillience()
    {
        System.out.println(Profile.withSegments(null).toString());
    }

    @Test
    void identity()
    {
        assertThat(Profile.withSegments("Bavarian Helles").getIdentity())
                .isEqualTo("Bavarian Helles");
    }

    @Test
    @DisplayName("initialised with segments in order")
    void segmentOrder()
    {
        final Segment segmentOne = new Segment(UUID.randomUUID(),
                                               "Segment 1",
                                               Duration.ofSeconds(3),
                                               Temperature.celsius(25.3));
        final Segment segmentTwo = new Segment(UUID.randomUUID(),
                                               "Segment 2",
                                               Duration.ofSeconds(3),
                                               Temperature.celsius(28.0));

        final Profile profile = Profile.withSegments("A profile", segmentOne, segmentTwo);

        assertThat(profile).containsExactly(segmentOne, segmentTwo);
    }

    @Test
    @DisplayName("starting the profile sets the state to running")
    void startProfile()
    {
        final Profile profile = Profile.withSegments("A profile");

        assertThat(profile.isRunning()).isFalse();

        profile.start();

        assertThat(profile.isRunning()).isTrue();
    }

    @Test
    @DisplayName("has no segments when none given")
    void currentSegmentIsInitialisedToEmpty()
    {
        final Profile profile = Profile.withSegments("A profile");

        assertThat(profile).isEmpty();
    }

    @Override
    public Class<Profile> getType()
    {
        return Profile.class;
    }

    @Test
    @DisplayName("execute segments")
    void executeSegments()
    {
        final Segment segmentOne = new Segment(UUID.randomUUID(),
                                               "Segment 1",
                                               Duration.ofSeconds(3),
                                               Temperature.celsius(25.3));
        final Segment segmentTwo = new Segment(UUID.randomUUID(),
                                               "Segment 2",
                                               Duration.ofSeconds(3),
                                               Temperature.celsius(28.0));

        final Profile profile = Profile.withSegments("A profile", segmentOne, segmentTwo);

        for (final Segment segment : profile)
            System.out.println(segment);
    }
}
