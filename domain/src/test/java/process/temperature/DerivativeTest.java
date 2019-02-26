package process.temperature;

import process.kernel.EqualityContract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

class DerivativeTest implements EqualityContract<Derivative>
{
    @Override
    public Class<Derivative> getType()
    {
        return Derivative.class;
    }

    @Test
    void zero()
    {
        assertThat(Derivative.zero()).isEqualTo(Derivative.valueOf(0));
    }

    @Test
    void dTermZeroDuration()
    {
        assertThat(Derivative.zero()
                .magnitudeFor(Magnitude.zero(), Magnitude.zero(), Duration.ZERO))
                        .isEqualTo(Magnitude.zero());
    }

    @Test
    void dTermZeroError()
    {
        assertThat(Derivative.zero()
                .magnitudeFor(Magnitude.zero(), Magnitude.zero(), Duration.ofSeconds(2)))
                        .isEqualTo(Magnitude.zero());
    }

    @Test
    void dTermPreviousErrorIsLess()
    {
        assertThat(Derivative.valueOf(1)
                .magnitudeFor(Magnitude.valueOf(10), Magnitude.zero(), Duration.ofSeconds(1)))
                        .isEqualTo(Magnitude.valueOf(10));
    }

    @Test
    void dTermPreviousErrorIsBigger()
    {
        assertThat(Derivative.valueOf(1)
                .magnitudeFor(Magnitude.zero(), Magnitude.valueOf(10), Duration.ofSeconds(1)))
                        .isEqualTo(Magnitude.valueOf(-10));
    }

    @Test
    @DisplayName("string representation of value is returned")
    void toStringHasValueOnly()
    {
        assertThat(Derivative.valueOf(22.3).toString()).isEqualTo("22.3");
    }
}
