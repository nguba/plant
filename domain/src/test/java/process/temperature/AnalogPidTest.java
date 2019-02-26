package process.temperature;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

class AnalogPidTest
{
    private final AnalogPid pid = new AnalogPid();

    @BeforeEach
    void setUp()
    {
        pid.setDerivative(Derivative.zero());
        pid.setIntegral(Integral.zero());
        pid.setProportional(Proportional.zero());
    }

    @Test
    void pidOnProporitonalTermOnly()
    {
        pid.setProportional(Proportional.valueOf(1));

        assertThat(pid.update(Temperature.celsius(50.0), Temperature.celsius(50.0)))
                .isEqualTo(Output.zero());
    }

    @Test
    void pidPTermOnly()
    {
        pid.setProportional(Proportional.valueOf(1));

        assertThat(pid.update(Temperature.celsius(20.0), Temperature.celsius(10.0)))
                .isEqualTo(Output.valueOf(10.0));
    }

    @Test
    void integralOverTwoPeriods() throws Exception
    {
        pid.setIntegral(Integral.valueOf(10));

        assertThat(pid.update(Temperature.celsius(20.0), Temperature.celsius((10.0))))
                .isEqualTo(Output.zero());

        Thread.sleep(2000);

        assertThat(pid.update(Temperature.celsius(20.0), Temperature.celsius((10.0))))
                .isEqualTo(Output.valueOf(200.0));
    }

    @Test
    void timeChangeFromNull()
    {
        final Duration duration = pid.timeChange(null, Instant.now());
        assertThat(duration).isEqualTo(Duration.ZERO);
    }

    @Test
    void timeChangeForOneSecond()
    {
        final Duration duration = pid.timeChange(Instant.ofEpochSecond(1),
                                                 Instant.ofEpochSecond(2));
        assertThat(duration).isEqualTo(Duration.ofSeconds(1));
    }

    @Test
    void pidUpdatesLastTime() throws Exception
    {
        final Instant start = Instant.now();
        pid.update(Temperature.celsius(0), Temperature.celsius(0));

        assertThat(pid.getLastTime()).isAfterOrEqualTo(start);
    }

    // @Test
    void tryItOut() throws InterruptedException
    {
        // final HeaterMock heater = new HeaterMock();
        //
        // // pid.setP(5000);
        //
        // final Temperature setpoint = Temperature.celsius(3.00);
        //
        // final DigitalPid digital = new DigitalPid(pid, 1500);
        // digital.setI(1000);
        // digital.setP(1.5);
        // digital.setD(0);
        //
        // while (true) {
        // if (digital.update(setpoint, heater.currentTemperature()))
        // heater.switchOn();
        // else heater.switchOff();
        //
        // Thread.sleep(1000);
        // System.out.println("temp=" + heater.currentTemperature());
        // }
    }
}
