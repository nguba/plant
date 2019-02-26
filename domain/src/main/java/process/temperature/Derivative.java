package process.temperature;

import java.time.Duration;

public final class Derivative extends Magnitude
{
    private Derivative(final double value)
    {
        super(value);
    }

    public static Derivative valueOf(final double value)
    {
        return new Derivative(value);
    }

    public static Derivative zero()
    {
        return Derivative.valueOf(0);
    }

    /**
     * This gives a rough estimate of the velocity (delta position/sample time), which predicts
     * where the position will be in a while.
     *
     * @param error
     *            difference between sP and pV
     * @param lastError
     *            difference between sP and pV calculated previously
     * @param dGain
     *            the derivative gain
     * @param duration
     *            between the last and current sample time
     * @return the derivative term
     */
    public Magnitude magnitudeFor(final Magnitude error,
                                  final Magnitude lastError,
                                  final Duration duration)
    {
        if (duration.getSeconds() == 0)
            return Magnitude.zero();

        final double dError = error.value - lastError.value;
        return Magnitude.valueOf((dError / duration.getSeconds()) * value);
    }

}
