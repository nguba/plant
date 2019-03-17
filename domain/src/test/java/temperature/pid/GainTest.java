package temperature.pid;

import test.EqualityContract;

class GainTest implements EqualityContract<Gain>
{
    @Override
    public Class<Gain> getTypeClass()
    {
        return Gain.class;
    }
}
