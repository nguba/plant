package process.temperature;

import process.kernel.EqualityContract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OutputTest implements EqualityContract<Output>
{

    private static final Output PID_TERM = Output
            .valueOf(Magnitude.zero(), Magnitude.zero(), Magnitude.zero());

    @Test
    void isAboveLowerThreshold()
    {
        assertThat(PID_TERM.isAbove(-1)).isTrue();
    }

    @Test
    void isAboveEqualThreshold()
    {
        assertThat(PID_TERM.isAbove(0)).isFalse();
    }

    @Test
    void isAboveHigherThreshold()
    {
        assertThat(PID_TERM.isAbove(1)).isFalse();
    }

    @Override
    public Class<Output> getType()
    {
        return Output.class;
    }

}
