package process.temperature;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

class AnalogPidStrategyTest
{
    private final AnalogPidStrategy pid = new AnalogPidStrategy();

    @Test
    @DisplayName("error is zero when processValue and setPoint are equal")
    void calculateErrorZero()
    {
        assertThat(pid.error(Temperature.celsius(50.0), Temperature.celsius(50.0))).isZero();
    }

    @Test
    @DisplayName("error is 10.0 when processValue is 10.0 below setPoint ")
    void calculateErrorPositive()
    {
        assertThat(pid.error(Temperature.celsius(50.0), Temperature.celsius(40.0))).isEqualTo(10.0);
    }

    @Test
    @DisplayName("error is 10.0 when processValue is 10.0 above setPoint ")
    void calculateErrorNegative()
    {
        assertThat(pid.error(Temperature.celsius(50.0), Temperature.celsius(60.0)))
                .isEqualTo(-10.0);
    }

    @Test
    @DisplayName("proporional term is zero when there is no error")
    void proportionalTerm()
    {
        assertThat(pid.pTerm(0, 1)).isZero();
    }

    @Test
    @DisplayName("proporional term is 10 for error of 10 and gain 1")
    void proportionalTerm10()
    {
        assertThat(pid.pTerm(10, 1)).isEqualTo(10.0);
    }

    @Test
    void pidOnProporitonalTermOnly()
    {
        pid.setP(1);

        assertThat(pid.update(Temperature.celsius(50.0), Temperature.celsius(50.0))).isZero();
    }

    @Test
    void pidPTermOnly()
    {
        pid.setP(1);

        assertThat(pid.update(Temperature.celsius(20.0), Temperature.celsius(10.0)))
                .isEqualTo(10.0);
    }

    @Test
    void integralForNoTimeChange()
    {
        assertThat(pid.iTerm(1000, 1, Duration.ZERO)).isZero();
    }

    @Test
    void integralForNoError()
    {
        assertThat(pid.iTerm(0, 1, Duration.ofSeconds(5))).isZero();
    }

    @Test
    void integralForOneSecond()
    {
        assertThat(pid.iTerm(1, 1, Duration.ofSeconds(1))).isEqualTo(1.0);
    }

    @Test
    void integralForOneSecondAndErrorOf10()
    {
        assertThat(pid.iTerm(10, 1, Duration.ofSeconds(1))).isEqualTo(10.0);
    }

    @Test
    void integralForOneSecondAndErrorOf10AndGain10()
    {
        assertThat(pid.iTerm(10, 10, Duration.ofSeconds(1))).isEqualTo(100.0);
    }

    @Test
    void integralOverTimePeriod()
    {
        assertThat(pid.iTerm(10, 10, Duration.ofSeconds(2))).isEqualTo(200.0);
    }

    @Test
    void integralOverTwoPeriods() throws Exception
    {
        pid.setI(10);

        assertThat(pid.update(Temperature.celsius(20.0), Temperature.celsius((10.0))))
                .isEqualTo(0.0);

        Thread.sleep(2000);

        assertThat(pid.update(Temperature.celsius(20.0), Temperature.celsius((10.0))))
                .isEqualTo(200.0);
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
        assertThat(pid.getLastTime()).isNull();

        final Instant start = Instant.now();
        pid.update(Temperature.celsius(0), Temperature.celsius(0));

        assertThat(pid.getLastTime()).isAfterOrEqualTo(start);
    }

    @Test
    void dTermZeroDuration()
    {
        assertThat(pid.dTerm(0, 0, 0, Duration.ZERO)).isZero();
    }

    @Test
    void dTermZeroError()
    {
        assertThat(pid.dTerm(0, 0, 0, Duration.ofSeconds(2))).isZero();
    }

    @Test
    void dTermPreviousErrorIsLess()
    {
        assertThat(pid.dTerm(10.0, 0.0, 1, Duration.ofSeconds(1))).isEqualTo(10.0);
    }

    @Test
    void dTermPreviousErrorIsBigger()
    {
        assertThat(pid.dTerm(0.0, 10.0, 1, Duration.ofSeconds(1))).isEqualTo(-10.0);
    }

    // @Test
    void tryItOut() throws InterruptedException
    {
        final HeaterMock heater = new HeaterMock();

        // pid.setP(5000);

        final Temperature setpoint = Temperature.celsius(3.00);

        final DigitalPidStrategy digital = new DigitalPidStrategy(pid, 1500);
        digital.setI(1000);
        digital.setP(1.5);
        digital.setD(0);

        while (true) {
            if (digital.update(setpoint, heater.currentTemperature()))
                heater.switchOn();
            else heater.switchOff();

            Thread.sleep(1000);
            System.out.println("temp=" + heater.currentTemperature());
        }
    }
}
