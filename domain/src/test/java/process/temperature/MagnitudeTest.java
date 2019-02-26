package process.temperature;

import process.kernel.EqualityContract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
