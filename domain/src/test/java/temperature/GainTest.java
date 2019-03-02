package temperature;

import kernel.EqualityContract;
import temperature.Gain;

class GainTest implements EqualityContract<Gain>
{
    @Override
    public Class<Gain> getTypeClass()
    {
        return Gain.class;
    }
}
