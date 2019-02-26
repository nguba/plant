package process.temperature;

import process.kernel.EqualityContract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

class MagnitudeTest implements EqualityContract<Magnitude>
{

    private static final Temperature SETPOINT = Temperature.celsius(50.0);

    @Test
    void zero()
    {
        assertThat(Magnitude.zero().toString()).isEqualTo("0.0");
    }

    @Override
    public Class<Magnitude> getType()
    {
        return Magnitude.class;
    }

    @Test
    @DisplayName("error is zero when processValue and setPoint are equal")
    void calculateErrorZero()
    {
        assertThat(Magnitude.error(SETPOINT, Temperature.celsius(50.0)))
                .isEqualTo(Magnitude.zero());
    }

    @Test
    @DisplayName("error is 10.0 when processValue is 10.0 below setPoint ")
    void calculateErrorPositive()
    {
        assertThat(Magnitude.error(SETPOINT, Temperature.celsius(40.0)))
                .isEqualTo(Magnitude.valueOf(10.0));
    }

    @Test
    @DisplayName("error is 10.0 when processValue is 10.0 above setPoint ")
    void calculateErrorNegative()
    {
        assertThat(Magnitude.error(SETPOINT, Temperature.celsius(60.0)))
                .isEqualTo(Magnitude.valueOf(-10.0));
    }

    @Test
    @DisplayName("proporional term is zero when there is no error")
    void proportionalTerm()
    {
        assertThat(Magnitude.pTerm(Magnitude.valueOf(0), Magnitude.valueOf(1)))
                .isEqualTo(Magnitude.zero());
    }

    @Test
    @DisplayName("proporional term is 10 for error of 10 and gain 1")
    void proportionalTermPositive()
    {
        assertThat(Magnitude.pTerm(Magnitude.valueOf(10), Magnitude.valueOf(1)))
                .isEqualTo(Magnitude.valueOf(10.0));
    }

    @Test
    @DisplayName("proporional term is -10 for error of 10 and gain 1")
    void proportionalTermNegative()
    {
        assertThat(Magnitude.pTerm(Magnitude.valueOf(-10), Magnitude.valueOf(1)))
                .isEqualTo(Magnitude.valueOf(-10.0));
    }

    @Test
    void integralForNoTimeChange()
    {
        assertThat(Magnitude.iTerm(Magnitude.valueOf(1000), Magnitude.valueOf(1), Duration.ZERO))
                .isEqualTo(Magnitude.zero());
    }

    @Test
    void integralForNoError()
    {
        assertThat(Magnitude
                .iTerm(Magnitude.valueOf(0), Magnitude.valueOf(1), Duration.ofSeconds(5)))
                        .isEqualTo(Magnitude.zero());
    }

    @Test
    void integralForOneSecond()
    {
        assertThat(Magnitude
                .iTerm(Magnitude.valueOf(1), Magnitude.valueOf(1), Duration.ofSeconds(1)))
                        .isEqualTo(Magnitude.valueOf(1));
    }

    @Test
    void integralForOneSecondAndErrorOf10()
    {
        assertThat(Magnitude
                .iTerm(Magnitude.valueOf(10), Magnitude.valueOf(1), Duration.ofSeconds(1)))
                        .isEqualTo(Magnitude.valueOf(10.0));
    }

    @Test
    void integralForOneSecondAndErrorOf10AndGain10()
    {
        assertThat(Magnitude
                .iTerm(Magnitude.valueOf(10), Magnitude.valueOf(10), Duration.ofSeconds(1)))
                        .isEqualTo(Magnitude.valueOf(100.0));
    }

    @Test
    void integralOverTimePeriod()
    {
        assertThat(Magnitude
                .iTerm(Magnitude.valueOf(10), Magnitude.valueOf(10), Duration.ofSeconds(2)))
                        .isEqualTo(Magnitude.valueOf(200.0));
    }

    @Test
    void dTermZeroDuration()
    {
        assertThat(Magnitude
                .dTerm(Magnitude.zero(), Magnitude.zero(), Magnitude.zero(), Duration.ZERO))
                        .isEqualTo(Magnitude.zero());
    }

    @Test
    void dTermZeroError()
    {
        assertThat(Magnitude
                .dTerm(Magnitude.zero(), Magnitude.zero(), Magnitude.zero(), Duration.ofSeconds(2)))
                        .isEqualTo(Magnitude.zero());
    }

    @Test
    void dTermPreviousErrorIsLess()
    {
        assertThat(Magnitude.dTerm(Magnitude.valueOf(10),
                                   Magnitude.zero(),
                                   Magnitude.valueOf(1),
                                   Duration.ofSeconds(1))).isEqualTo(Magnitude.valueOf(10));
    }

    @Test
    void dTermPreviousErrorIsBigger()
    {
        assertThat(Magnitude.dTerm(Magnitude.zero(),
                                   Magnitude.valueOf(10),
                                   Magnitude.valueOf(1),
                                   Duration.ofSeconds(1))).isEqualTo(Magnitude.valueOf(-10));
    }
}
