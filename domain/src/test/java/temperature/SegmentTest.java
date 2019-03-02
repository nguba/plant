package temperature;

import kernel.EntityEqualityContract;
import temperature.Segment;
import temperature.Temperature;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

class SegmentTest implements EntityEqualityContract<UUID, Segment>
{
    final Segment segment = new Segment(UUID.randomUUID(),
                                        "first segment",
                                        Duration.ofSeconds(2),
                                        Temperature.celsius(25.0));

    @Override
    public Class<Segment> getType()
    {
        return Segment.class;
    }

    @Test
    void identity()
    {
        final UUID    identity = UUID.randomUUID();
        final Segment segment  = new Segment(identity,
                                             "a segment",
                                             Duration.ofSeconds(2),
                                             Temperature.celsius(25.0));
        assertThat(segment.getIdentity()).isEqualTo(identity);
    }

    @Test
    @DisplayName("is not complete when temperature is below setpoint")
    void setpointNotReached()
    {
        assertThat(segment.isComplete(Temperature.celsius(22.0))).isFalse();
    }

    @Test
    @DisplayName("has completed once setpoint reached")
    void setpointReached()
    {
        assertThat(segment.isComplete(Temperature.celsius(25.0))).isTrue();
    }

    @Test
    @DisplayName("start returns the instant")
    void startSegment()
    {
        assertThat(segment.start()).isBeforeOrEqualTo(Instant.now());
    }

    @Test
    @DisplayName("toString() won't crash on null values")
    void toStringResillience()
    {
        new Segment(null, null, null, null).toString();
    }
}
