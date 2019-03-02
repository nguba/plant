package temperature.pid;

import kernel.EqualityContract;
import temperature.pid.Gain;

class GainTest implements EqualityContract<Gain>
{
    @Override
    public Class<Gain> getTypeClass()
    {
        return Gain.class;
    }
}
