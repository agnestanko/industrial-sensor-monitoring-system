package com.example.industrialmonitoring;

import com.example.industrialmonitoring.model.Sensor;
import com.example.industrialmonitoring.model.SensorReading;
import com.example.industrialmonitoring.model.SensorType;
import com.example.industrialmonitoring.service.SensorMonitoringService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class IndustrialMonitoringServiceTest {

    @Test
    void normalSensorReadingShouldNotCreateAlarm() {
        SensorMonitoringService monitoringService = new SensorMonitoringService();
        Sensor temperatureSensor = createTemperatureSensor();

        SensorReading reading = monitoringService.addReading(temperatureSensor, 60.0);

        assertTrue(reading.isNormal());
        assertEquals(0, monitoringService.getAlarms().size());
    }

    @Test
    void highSensorReadingShouldCreateAlarm() {
        SensorMonitoringService monitoringService = new SensorMonitoringService();
        Sensor pressureSensor = createPressureSensor();

        SensorReading reading = monitoringService.addReading(pressureSensor, 12.0);

        assertFalse(reading.isNormal());
        assertEquals(1, monitoringService.getAlarms().size());
        assertTrue(monitoringService.getAlarms().get(0).getMessage().contains("above"));
    }

    @Test
    void lowSensorReadingShouldCreateAlarm() {
        SensorMonitoringService monitoringService = new SensorMonitoringService();
        Sensor levelSensor = createLevelSensor();

        SensorReading reading = monitoringService.addReading(levelSensor, 5.0);

        assertFalse(reading.isNormal());
        assertEquals(1, monitoringService.getAlarms().size());
        assertTrue(monitoringService.getAlarms().get(0).getMessage().contains("below"));
    }

    @Test
    void alarmHistoryShouldStoreAllGeneratedAlarms() {
        SensorMonitoringService monitoringService = new SensorMonitoringService();

        monitoringService.addReading(createPressureSensor(), 12.0);
        monitoringService.addReading(createVibrationSensor(), 6.8);
        monitoringService.addReading(createTemperatureSensor(), 60.0);

        assertEquals(3, monitoringService.getReadings().size());
        assertEquals(2, monitoringService.getAlarms().size());
    }

    private Sensor createTemperatureSensor() {
        return new Sensor(
                "TEMP-101",
                "Boiler temperature sensor",
                SensorType.TEMPERATURE,
                "°C",
                20.0,
                80.0
        );
    }

    private Sensor createPressureSensor() {
        return new Sensor(
                "PRESS-201",
                "Hydraulic pressure transducer",
                SensorType.PRESSURE,
                "bar",
                2.0,
                10.0
        );
    }

    private Sensor createVibrationSensor() {
        return new Sensor(
                "VIB-301",
                "Motor vibration sensor",
                SensorType.VIBRATION,
                "mm/s",
                0.0,
                5.0
        );
    }

    private Sensor createLevelSensor() {
        return new Sensor(
                "LEVEL-401",
                "Tank level sensor",
                SensorType.LEVEL,
                "%",
                10.0,
                90.0
        );
    }
}