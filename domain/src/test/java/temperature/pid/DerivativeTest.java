package temperature.pid;

import kernel.EqualityContract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

class DerivativeTest implements EqualityContract<Derivative>
{
    @Test
    void dTermPreviousErrorIsBigger()
    {
        assertThat(Derivative.valueOf(1)
                .termFor(Error.zero(), Error.valueOf(10), Duration.ofSeconds(1)))
                        .isEqualTo(Term.valueOf(-10));
    }

    @Test
    void dTermPreviousErrorIsLess()
    {
        assertThat(Derivative.valueOf(1)
                .termFor(Error.valueOf(10), Error.zero(), Duration.ofSeconds(1)))
                        .isEqualTo(Term.valueOf(10));
    }

    @Test
    void dTermZeroDuration()
    {
        assertThat(Derivative.zero().termFor(Error.zero(), Error.zero(), Duration.ZERO))
                .isEqualTo(Term.zero());
    }

    @Test
    void dTermZeroError()
    {
        assertThat(Derivative.zero().termFor(Error.zero(), Error.zero(), Duration.ofSeconds(2)))
                .isEqualTo(Term.zero());
    }

    @Override
    public Class<Derivative> getTypeClass()
    {
        return Derivative.class;
    }

    @Test
    @DisplayName("string representation of value is returned")
    void toStringHasValueOnly()
    {
        assertThat(Derivative.valueOf(22.3).toString()).isEqualTo("22.3");
    }

    @Test
    void zero()
    {
        assertThat(Derivative.zero()).isEqualTo(Derivative.valueOf(0));
    }
}
