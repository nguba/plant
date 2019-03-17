package equipment.sensor.heater;

import kernel.DomainEvent;
import kernel.EventPublisher;
import temperature.Temperature;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HeaterMockTest implements EventPublisher
{
    final HeaterMock heater = new HeaterMock(this);

    @Test
    void increaseTempeature() throws Exception
    {
        final Temperature previous = heater.currentTemperature();

        heater.switchOn();

        Thread.sleep(1000);

        assertThat(heater.currentTemperature().isAbove(previous));
    }

    @Override
    public <E extends DomainEvent> void publish(final E event)
    {
        System.out.println(event);
    }

    @Test
    void switchOff() throws Exception
    {
        heater.switchOn();
        Thread.sleep(1000);

        final Temperature previous = heater.currentTemperature();

        heater.switchOff();

        Thread.sleep(1000);

        assertThat(heater.currentTemperature().equals(previous));
    }
}
