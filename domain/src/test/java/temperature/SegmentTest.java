package temperature;

import kernel.EntityEqualityContract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

class SegmentTest implements EntityEqualityContract<UUID, Segment>
{
    static final UUID identity = UUID.randomUUID();

    final Segment segment = new Segment(identity,
                                        "first segment",
                                        Duration.ofMillis(1),
                                        Temperature.celsius(25.0));

    @Override
    public Class<Segment> getTypeClass()
    {
        return Segment.class;
    }

    @Test
    void identity()
    {
        assertThat(segment.getIdentity()).isEqualTo(identity);
    }

    @Test
    @DisplayName("is not complete when temperature is below setpoint")
    void setpointNotReached()
    {
        assertThat(segment.isComplete(Temperature.celsius(22.0))).isFalse();
    }

    @Test
    @DisplayName("has completed once setpoint and duration reached")
    void setpointReached() throws Exception
    {
        assertThat(segment.isComplete(Temperature.celsius(25.0))).isFalse();
        Thread.sleep(1);
        assertThat(segment.isComplete(Temperature.celsius(25.0))).isTrue();
    }

    @Test
    @DisplayName("has completed once setpoint is higher and duration reached")
    void startTimerForAboveSetpoint() throws Exception
    {
        assertThat(segment.isComplete(Temperature.celsius(26.0))).isFalse();
        Thread.sleep(1);
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
