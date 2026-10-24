package com.example.industrialmonitoring;

import com.example.industrialmonitoring.model.Sensor;
import com.example.industrialmonitoring.model.SensorReading;
import com.example.industrialmonitoring.model.SensorType;
import com.example.industrialmonitoring.service.SensorMonitoringService;

public class IndustrialMonitoringMain {
    public static void main(String[] args) {
        SensorMonitoringService monitoringService = new SensorMonitoringService();

        Sensor temperatureSensor = new Sensor(
                "TEMP-101",
                "Boiler temperature sensor",
                SensorType.TEMPERATURE,
                "°C",
                20.0,
                80.0
        );

        Sensor pressureSensor = new Sensor(
                "PRESS-201",
                "Hydraulic pressure transducer",
                SensorType.PRESSURE,
                "bar",
                2.0,
                10.0
        );

        Sensor vibrationSensor = new Sensor(
                "VIB-301",
                "Motor vibration sensor",
                SensorType.VIBRATION,
                "mm/s",
                0.0,
                5.0
        );

        Sensor levelSensor = new Sensor(
                "LEVEL-401",
                "Tank level sensor",
                SensorType.LEVEL,
                "%",
                10.0,
                90.0
        );

        System.out.println("===== INDUSTRIAL SENSOR MONITORING SYSTEM =====");
        System.out.println("PLC-inspired monitoring logic for industrial sensors and alarms.");

        SensorReading readingOne = monitoringService.addReading(temperatureSensor, 72.5);
        SensorReading readingTwo = monitoringService.addReading(pressureSensor, 11.2);
        SensorReading readingThree = monitoringService.addReading(vibrationSensor, 6.8);
        SensorReading readingFour = monitoringService.addReading(levelSensor, 55.0);
        SensorReading readingFive = monitoringService.addReading(levelSensor, 5.0);

        monitoringService.displayReadingStatus(readingOne);
        monitoringService.displayReadingStatus(readingTwo);
        monitoringService.displayReadingStatus(readingThree);
        monitoringService.displayReadingStatus(readingFour);
        monitoringService.displayReadingStatus(readingFive);

        monitoringService.displayAlarmHistory();
    }
}