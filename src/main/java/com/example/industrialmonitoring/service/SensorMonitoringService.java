package com.example.industrialmonitoring.service;

import com.example.industrialmonitoring.model.Alarm;
import com.example.industrialmonitoring.model.AlarmSeverity;
import com.example.industrialmonitoring.model.Sensor;
import com.example.industrialmonitoring.model.SensorReading;
import com.example.industrialmonitoring.model.SensorType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SensorMonitoringService {
    private final List<SensorReading> readings = new ArrayList<>();
    private final List<Alarm> alarms = new ArrayList<>();
    private final AlarmFileService alarmFileService = new AlarmFileService();

    public SensorReading addReading(Sensor sensor, double value) {
        SensorReading reading = new SensorReading(sensor, value);
        readings.add(reading);

        if (!reading.isNormal()) {
            Alarm alarm = createAlarm(reading);
            alarms.add(alarm);
            alarmFileService.saveAlarm(alarm);
        }

        return reading;
    }

    public List<SensorReading> getReadings() {
        return readings;
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }

    public void displayReadingStatus(SensorReading reading) {
        Sensor sensor = reading.getSensor();

        System.out.println("\nSensor: " + sensor.getSensorId() + " - " + sensor.getName());
        System.out.println("Type: " + sensor.getType());
        System.out.println("Value: " + reading.getValue() + " " + sensor.getUnit());
        System.out.println("Normal range: " + sensor.getMinimumAllowedValue()
                + " - " + sensor.getMaximumAllowedValue() + " " + sensor.getUnit());

        if (reading.isNormal()) {
            System.out.println("Status: NORMAL");
        } else {
            Alarm alarm = createAlarmPreview(reading);
            System.out.println("Status: ALARM");
            System.out.println("Severity: " + alarm.getSeverity());
            System.out.println("Alarm: " + alarm.getMessage());
            System.out.println("Recommended action: " + alarm.getRecommendedAction());
        }
    }

    public void displayAlarmHistory() {
        System.out.println("\n===== ALARM HISTORY =====");

        if (alarms.isEmpty()) {
            System.out.println("No alarms detected.");
            return;
        }

        alarms.forEach(System.out::println);
    }

    private Alarm createAlarm(SensorReading reading) {
        String alarmId = "AL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        return new Alarm(
                alarmId,
                reading.getSensor(),
                reading.getValue(),
                calculateSeverity(reading),
                buildAlarmMessage(reading),
                buildRecommendedAction(reading)
        );
    }

    private Alarm createAlarmPreview(SensorReading reading) {
        return new Alarm(
                "PREVIEW",
                reading.getSensor(),
                reading.getValue(),
                calculateSeverity(reading),
                buildAlarmMessage(reading),
                buildRecommendedAction(reading)
        );
    }

    private String buildAlarmMessage(SensorReading reading) {
        Sensor sensor = reading.getSensor();
        double value = reading.getValue();

        if (value < sensor.getMinimumAllowedValue()) {
            return sensor.getName() + " is below the minimum allowed value.";
        }

        return sensor.getName() + " is above the maximum allowed value.";
    }

    private AlarmSeverity calculateSeverity(SensorReading reading) {
        Sensor sensor = reading.getSensor();
        double value = reading.getValue();
        double range = sensor.getMaximumAllowedValue() - sensor.getMinimumAllowedValue();
        double warningMargin = range * 0.20;

        if (value < sensor.getMinimumAllowedValue()) {
            double difference = sensor.getMinimumAllowedValue() - value;
            return difference > warningMargin ? AlarmSeverity.CRITICAL : AlarmSeverity.WARNING;
        }

        double difference = value - sensor.getMaximumAllowedValue();
        return difference > warningMargin ? AlarmSeverity.CRITICAL : AlarmSeverity.WARNING;
    }

    private String buildRecommendedAction(SensorReading reading) {
        Sensor sensor = reading.getSensor();
        double value = reading.getValue();

        if (sensor.getType() == SensorType.TEMPERATURE) {
            return value > sensor.getMaximumAllowedValue()
                    ? "Check cooling system, ventilation, and temperature control loop."
                    : "Check heating system and verify temperature sensor calibration.";
        }

        if (sensor.getType() == SensorType.PRESSURE) {
            return value > sensor.getMaximumAllowedValue()
                    ? "Inspect pressure regulator, safety valve, and hydraulic circuit."
                    : "Check pump operation, leaks, and pressure supply line.";
        }

        if (sensor.getType() == SensorType.VIBRATION) {
            return "Schedule motor bearing inspection and check mechanical alignment.";
        }

        if (sensor.getType() == SensorType.LEVEL) {
            return value > sensor.getMaximumAllowedValue()
                    ? "Check overflow protection and tank level control system."
                    : "Check supply pump, inlet valve, and refill tank if necessary.";
        }

        return "Perform general maintenance inspection.";
    }
}