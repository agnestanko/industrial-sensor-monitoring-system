package com.example.industrialmonitoring.model;

import java.time.LocalDateTime;

public class SensorReading {
    private Sensor sensor;
    private double value;
    private LocalDateTime timestamp;

    public SensorReading(Sensor sensor, double value) {
        this.sensor = sensor;
        this.value = value;
        this.timestamp = LocalDateTime.now();
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isNormal() {
        return sensor.isValueInNormalRange(value);
    }

    @Override
    public String toString() {
        return "Sensor: " + sensor.getSensorId()
                + ", Value: " + value + " " + sensor.getUnit()
                + ", Timestamp: " + timestamp;
    }
}