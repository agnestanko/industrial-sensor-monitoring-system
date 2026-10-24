package com.example.industrialmonitoring.model;

public class Sensor {
    private String sensorId;
    private String name;
    private SensorType type;
    private String unit;
    private double minimumAllowedValue;
    private double maximumAllowedValue;

    public Sensor(String sensorId, String name, SensorType type, String unit,
                  double minimumAllowedValue, double maximumAllowedValue) {
        this.sensorId = sensorId;
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.minimumAllowedValue = minimumAllowedValue;
        this.maximumAllowedValue = maximumAllowedValue;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getMinimumAllowedValue() {
        return minimumAllowedValue;
    }

    public void setMinimumAllowedValue(double minimumAllowedValue) {
        this.minimumAllowedValue = minimumAllowedValue;
    }

    public double getMaximumAllowedValue() {
        return maximumAllowedValue;
    }

    public void setMaximumAllowedValue(double maximumAllowedValue) {
        this.maximumAllowedValue = maximumAllowedValue;
    }

    public boolean isValueInNormalRange(double value) {
        return value >= minimumAllowedValue && value <= maximumAllowedValue;
    }

    @Override
    public String toString() {
        return sensorId + " - " + name
                + " [" + type + "]"
                + ", normal range: " + minimumAllowedValue
                + " - " + maximumAllowedValue + " " + unit;
    }
}