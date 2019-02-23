package me.nguba.plant.ddd;

import me.nguba.plant.EqualityContract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class DomainEventTest implements EqualityContract<DomainEvent>
{
    @Override
    public Class<DomainEvent> getType()
    {
        return DomainEvent.class;
    }

    @Test
    @DisplayName("toString includes timestamp")
    void toStringContainsTimestamp()
    {
        final DomainEvent event = new DomainEvent() {
        };

        assertThat(event.toString()).contains("timestamp=");
    }

    @Test
    @DisplayName("timestamp is accessed")
    void timestampAccess()
    {
        final LocalDateTime expected = LocalDateTime.now();
        final DomainEvent   event    = new DomainEvent(expected) {
                                     };

        assertThat(event.getTimestamp()).isEqualTo(expected);
    }
}
