package temperature;

import temperature.event.DomainEvent;
import temperature.event.GuavaMessageBus;
import temperature.event.MessageBus;

import com.google.common.eventbus.Subscribe;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HeaterMockTest
{
    private static final MessageBus BUS = new GuavaMessageBus();

    final HeaterMock heater = new HeaterMock(BUS);

    @Test
    void increaseTempeature() throws Exception
    {
        final Temperature previous = heater.currentTemperature();

        heater.switchOn();

        Thread.sleep(1000);

        assertThat(heater.currentTemperature().isAbove(previous));
    }

    @BeforeEach
    void setUp() throws Exception
    {
        BUS.subscribe(heater);
        BUS.subscribe(this);
    }

    @AfterEach
    void tearDown() throws Exception
    {
        BUS.unsubscribe(heater);
        BUS.unsubscribe(this);
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

    @Subscribe
    void onEvent(final DomainEvent event)
    {
        System.out.println(event);
    }
}
