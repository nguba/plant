package temperature;

import kernel.EqualityContract;
import temperature.Output;
import temperature.Term;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OutputTest implements EqualityContract<Output>
{

    private static final Output PID_TERM = Output.valueOf(Term.zero(), Term.zero(), Term.zero());

    @Override
    public Class<Output> getType()
    {
        return Output.class;
    }

    @Test
    void isBelowThreshold()
    {
        assertThat(PID_TERM.isBelow(Output.valueOf(1))).isTrue();
    }

    @Test
    void isEqualThreshold()
    {
        assertThat(PID_TERM.isBelow(Output.zero())).isFalse();
    }

    @Test
    void isNotBelowThreshold()
    {
        assertThat(PID_TERM.isBelow(Output.valueOf(-1))).isFalse();
    }

}
