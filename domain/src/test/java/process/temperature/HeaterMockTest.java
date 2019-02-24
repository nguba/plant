package process.temperature;

import process.temperature.Temperature;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HeaterMockTest
{

    @BeforeEach
    void setUp() throws Exception
    {
    }

    @Test
    void increaseTempeature() throws Exception
    {
        final Temperature previous = heater.currentTemperature();

        heater.switchOn();

        Thread.sleep(1000);

        assertThat(heater.currentTemperature().isAbove(previous));
    }

    final HeaterMock heater = new HeaterMock();

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
