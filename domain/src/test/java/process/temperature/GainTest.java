package process.temperature;

import process.kernel.EqualityContract;

class GainTest implements EqualityContract<Gain>
{
    @Override
    public Class<Gain> getType()
    {
        return Gain.class;
    }
}
