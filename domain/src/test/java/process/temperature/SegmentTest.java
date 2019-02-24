package process.temperature;

import process.kernel.EntityEqualityContract;
import process.temperature.Temperature;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.UUID;

class SegmentTest implements EntityEqualityContract<UUID, Segment>
{
    final Segment segment = new Segment(UUID.randomUUID(),
                                        "first segment",
                                        Duration.ofSeconds(2),
                                        Temperature.celsius(25.0));

    @Test
    @DisplayName("segment is not active when temperature is below setpoint")
    void setpointNotReached()
    {
        assertThat(segment.isActive(Temperature.celsius(22.0))).isFalse();
    }

    @Test
    @DisplayName("segment active once setpoint reached")
    void setpointReached()
    {
        assertThat(segment.isActive(Temperature.celsius(25.0))).isTrue();
    }

    @Test
    void toStringResillience()
    {
        new Segment(null, null, null, null).toString();
    }

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
        System.out.println(segment);
        assertThat(segment.getIdentity()).isEqualTo(identity);
    }
}
