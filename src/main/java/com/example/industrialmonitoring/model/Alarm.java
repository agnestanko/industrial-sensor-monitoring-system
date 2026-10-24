package com.example.industrialmonitoring.model;

import java.time.LocalDateTime;

public class Alarm {
    private String alarmId;
    private Sensor sensor;
    private double actualValue;
    private AlarmSeverity severity;
    private String message;
    private String recommendedAction;
    private LocalDateTime timestamp;

    public Alarm(String alarmId, Sensor sensor, double actualValue, AlarmSeverity severity, String message, String recommendedAction) {
        this.alarmId = alarmId;
        this.sensor = sensor;
        this.actualValue = actualValue;
        this.severity = severity;
        this.message = message;
        this.recommendedAction = recommendedAction;
        this.timestamp = LocalDateTime.now();
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public double getActualValue() {
        return actualValue;
    }

    public void setActualValue(double actualValue) {
        this.actualValue = actualValue;
    }

    public AlarmSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(AlarmSeverity severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecommendedAction() {
        return recommendedAction;
    }

    public void setRecommendedAction(String recommendedAction) {
        this.recommendedAction = recommendedAction;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ALARM ID: " + alarmId
                + ", Severity: " + severity
                + ", Sensor: " + sensor.getSensorId()
                + " - " + sensor.getName()
                + ", Actual value: " + actualValue + " " + sensor.getUnit()
                + ", Message: " + message
                + ", Recommended action: " + recommendedAction
                + ", Time: " + timestamp;
    }
}