package process.temperature;

import process.kernel.EqualityContract;

class MagnitudeTest implements EqualityContract<Magnitude>
{
    @Override
    public Class<Magnitude> getType()
    {
        return Magnitude.class;
    }
}
