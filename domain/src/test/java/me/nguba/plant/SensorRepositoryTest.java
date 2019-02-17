package me.nguba.plant;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SensorRepositoryTest
{
    private final SensorRepository repo = new SensorRepository();

    final Sensor sensor = new Sensor() {

        @Override
        public Temperature getTemperature()
        {
            return null;
        }

        @Override
        public SensorId getId()
        {
            return null;
        }
    };

    final SensorId id = new SensorId() {
    };

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
