package me.nguba.plant;

public interface Sensor
{
    SensorId getId();

    Temperature getTemperature();
}
