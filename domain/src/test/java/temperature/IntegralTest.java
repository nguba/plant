package temperature;

import kernel.EqualityContract;
import temperature.Error;
import temperature.Integral;
import temperature.Term;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

class IntegralTest implements EqualityContract<Integral>
{
    private static final Integral INTEGRAL = Integral.valueOf(1);

    @Override
    public Class<Integral> getType()
    {
        return Integral.class;
    }

    @Test
    void integralForNoError()
    {
        assertThat(INTEGRAL.termFor(Error.valueOf(0), Duration.ofSeconds(5)))
                .isEqualTo(Term.zero());
    }

    @Test
    void integralForNoTimeChange()
    {
        assertThat(INTEGRAL.termFor(Error.valueOf(1000), Duration.ZERO)).isEqualTo(Term.zero());
    }

    @Test
    void integralForOneSecond()
    {
        assertThat(INTEGRAL.termFor(Error.valueOf(1), Duration.ofSeconds(1)))
                .isEqualTo(Term.valueOf(1));
    }

    @Test
    void integralForOneSecondAndErrorOf10()
    {
        assertThat(INTEGRAL.termFor(Error.valueOf(10), Duration.ofSeconds(1)))
                .isEqualTo(Term.valueOf(10.0));
    }

    @Test
    void integralOverTimePeriod()
    {
        assertThat(INTEGRAL.termFor(Error.valueOf(10), Duration.ofSeconds(2)))
                .isEqualTo(Term.valueOf(20.0));
    }

    @Test
    @DisplayName("string representation of value is returned")
    void toStringHasValueOnly()
    {
        assertThat(Integral.valueOf(22.3).toString()).isEqualTo("22.3");
    }

    @Test
    void zero()
    {
        assertThat(Integral.zero()).isEqualTo(Integral.valueOf(0));
    }

}
