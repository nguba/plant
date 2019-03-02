package temperature.pid;

import temperature.Temperature;
import temperature.pid.AnalogPid;
import temperature.pid.Derivative;
import temperature.pid.DigitalPid;
import temperature.pid.Integral;
import temperature.pid.Proportional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DigitalPidTest
{
    DigitalPid pid = new DigitalPid(new AnalogPid(), 1000);

    @Test
    void falseWhenNoHeatingNeeded() throws Exception
    {
        assertThat(pid.update(Temperature.celsius(10), Temperature.celsius(10.01))).isFalse();
    }

    @BeforeEach
    void setUp() throws Exception
    {
        Thread.sleep(1000);
        pid.setIntegral(Integral.valueOf(1020));
        pid.setProportional(Proportional.zero());
        pid.setDerivative(Derivative.zero());
    }

    @Test
    void trueWhenHeatingIsNeeded() throws Exception
    {
        assertThat(pid.update(Temperature.celsius(10), Temperature.celsius(0))).isTrue();
    }

}
