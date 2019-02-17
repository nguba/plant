package me.nguba.plant;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SensorRepositoryTest
{
    private final SensorRepository repo = new SensorRepository();

    @BeforeEach
    void setUp() throws Exception
    {
    }

    @Test
    void createAndRead()
    {
        final SensorId id = new SensorId() {
        };

        final Sensor sensor = () -> id;

        repo.create(sensor);

        assertThat(repo.read(id)).isEqualTo(sensor);
    }

    @Test
    void delete()
    {
        final SensorId id = new SensorId() {
        };

        final Sensor sensor = () -> id;

        repo.create(sensor);

        assertThat(repo.read(id)).isEqualTo(sensor);

        repo.delete(id);

        assertThat(repo.read(id)).isNull();
    }
}
