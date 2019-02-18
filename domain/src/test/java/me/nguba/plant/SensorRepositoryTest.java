package me.nguba.plant;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SensorRepositoryTest
{
    private final SensorRepository repo = new SensorRepository();

    Sensor sensor;

    final SensorId id = new SensorId() {
    };

    @BeforeEach
    void setUp()
    {
        sensor = new Sensor() {

            @Override
            public Temperature getTemperature()
            {
                return null;
            }

            @Override
            public SensorId getId()
            {
                return id;
            }
        };
    }

    @Test
    void createAndRead()
    {
        repo.create(sensor);

        assertThat(repo.read(id)).isEqualTo(sensor);
    }

    @Test
    void delete()
    {
        repo.create(sensor);

        assertThat(repo.read(id)).isEqualTo(sensor);

        repo.delete(id);

        assertThat(repo.read(id)).isNull();
    }
}
