package temperature.pid;

import kernel.EqualityContract;

class GainTest implements EqualityContract<Gain>
{
    @Override
    public Class<Gain> getTypeClass()
    {
        return Gain.class;
    }
}
