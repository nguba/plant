package me.nguba.plant;

public interface Sensor extends TemperatureReader
{
    SensorId getId();
}
