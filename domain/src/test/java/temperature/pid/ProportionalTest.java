package temperature.pid;

import kernel.EqualityContract;
import temperature.pid.Error;
import temperature.pid.Proportional;
import temperature.pid.Term;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProportionalTest implements EqualityContract<Proportional>
{
    private static final Proportional PROPORTIONAL = Proportional.valueOf(1);

    @Override
    public Class<Proportional> getTypeClass()
    {
        return Proportional.class;
    }

    @Test
    @DisplayName("proporional term is zero when there is no error")
    void proportionalTerm()
    {
        assertThat(PROPORTIONAL.termFor(Error.valueOf(0))).isEqualTo(Term.zero());
    }

    @Test
    @DisplayName("proporional term is -10 for error of 10 and gain 1")
    void proportionalTermNegative()
    {
        assertThat(PROPORTIONAL.termFor(Error.valueOf(-10))).isEqualTo(Term.valueOf(-10.0));
    }

    @Test
    @DisplayName("proporional term is 10 for error of 10 and gain 1")
    void proportionalTermPositive()
    {
        assertThat(PROPORTIONAL.termFor(Error.valueOf(10))).isEqualTo(Term.valueOf(10.0));
    }

    @Test
    @DisplayName("string representation of value is returned")
    void toStringHasValueOnly()
    {
        assertThat(Proportional.valueOf(22.3).toString()).isEqualTo("22.3");
    }

    @Test
    void zero()
    {
        assertThat(Proportional.zero()).isEqualTo(Proportional.valueOf(0));
    }
}
