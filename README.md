# Industrial Sensor Monitoring and Alarm System

## Overview

Industrial Sensor Monitoring and Alarm System is a Java console application developed as part of a technical assessment. It simulates monitoring of industrial equipment using sensor readings, operating limits, and automatic alarm generation.

The application is inspired by monitoring concepts used in PLC and SCADA environments. It does **not** connect to a physical PLC or real sensors; all sensor readings are simulated.

## Main Features

- Simulates industrial sensors for temperature, pressure, vibration, and level.
- Defines minimum and maximum acceptable operating values for each sensor.
- Processes sensor readings with timestamps.
- Detects readings outside the configured operating range and generates alarms.
- Distinguishes between `WARNING` and `CRITICAL` alarm severity levels.
- Provides descriptive alarm messages and recommended maintenance actions.
- Maintains an alarm history during execution.
- Saves generated alarms to a JSON file (`data/alarms.json`).
- Includes JUnit tests for monitoring and alarm behavior.

## Technologies

- Java 17 or newer
- Maven
- JUnit 5
- Jackson Databind
- Object-Oriented Programming (OOP)

## Project Structure

The following structure shows the files relevant to the standalone monitoring application after separating it from the train ticketing project:

```text
industrial-sensor-monitoring-system/
├── pom.xml
├── README.md
├── data/
│   └── alarms.json
└── src/
    ├── main/
    │   └── java/
    │       └── com/example/industrialmonitoring/
    │           ├── IndustrialMonitoringMain.java
    │           ├── model/
    │           │   ├── Alarm.java
    │           │   ├── AlarmSeverity.java
    │           │   ├── Sensor.java
    │           │   ├── SensorReading.java
    │           │   └── SensorType.java
    │           └── service/
    │               ├── AlarmFileService.java
    │               └── SensorMonitoringService.java
    └── test/
        └── java/
            └── com/example/industrialmonitoring/
                └── IndustrialMonitoringServiceTest.java
```

## Supported Sensor Types

| Type | Example Sensor | Measurement |
|---|---|---|
| `TEMPERATURE` | `TEMP-101` – Boiler temperature sensor | °C |
| `PRESSURE` | `PRESS-201` – Hydraulic pressure transducer | bar |
| `VIBRATION` | `VIB-301` – Motor vibration sensor | mm/s |
| `LEVEL` | `LEVEL-401` – Tank level sensor | % |

## Monitoring and Alarm Logic

Each sensor is assigned a valid operating range. The monitoring service checks each simulated measurement against its configured limits:

```text
IF measured value is below the minimum allowed value
   OR measured value is above the maximum allowed value
THEN generate an alarm
ELSE mark the reading as normal
```

Generated alarms include the affected sensor, its measured value, a severity (`WARNING` or `CRITICAL`), a descriptive message, a timestamp, and a recommended action.

### Example Console Output

```text
===== INDUSTRIAL SENSOR MONITORING SYSTEM =====
PLC-inspired monitoring logic for industrial sensors and alarms.

Sensor: TEMP-101 - Boiler temperature sensor
Type: TEMPERATURE
Value: 72.5 °C
Normal range: 20.0 - 80.0 °C
Status: NORMAL

Sensor: PRESS-201 - Hydraulic pressure transducer
Type: PRESSURE
Value: 11.2 bar
Normal range: 2.0 - 10.0 bar
Status: ALARM
Severity: WARNING
Alarm: Hydraulic pressure transducer is above the maximum allowed value.
Recommended action: Inspect pressure regulator, safety valve, and hydraulic circuit.

Sensor: VIB-301 - Motor vibration sensor
Type: VIBRATION
Value: 6.8 mm/s
Normal range: 0.0 - 5.0 mm/s
Status: ALARM
Severity: CRITICAL
Alarm: Motor vibration sensor is above the maximum allowed value.
Recommended action: Schedule motor bearing inspection and check mechanical alignment.
```

The console can also display the alarm history generated during execution.

## Alarm Persistence

Generated alarms are exported by `AlarmFileService` using Jackson Databind to:

```text
data/alarms.json
```

Example record:

```json
[
  {
    "alarmId": "AL-97D9E627",
    "sensorId": "PRESS-201",
    "sensorName": "Hydraulic pressure transducer",
    "sensorType": "PRESSURE",
    "actualValue": 11.2,
    "unit": "bar",
    "minimumAllowedValue": 2.0,
    "maximumAllowedValue": 10.0,
    "message": "Hydraulic pressure transducer is above the maximum allowed value.",
    "timestamp": "2026-05-10T18:46:59.507058700",
    "severity": "WARNING",
    "recommendedAction": "Inspect pressure regulator, safety valve, and hydraulic circuit."
  }
]
```

The JSON output allows alarm records to be inspected after the application finishes. Saved alarms are not automatically loaded into memory when the application starts.

## How to Run

### Prerequisites

- JDK 17 or newer
- IntelliJ IDEA or another Java IDE
- Maven (for command-line builds and tests)

### Run in IntelliJ IDEA

1. Clone or download the standalone repository and open it as a Maven project.
2. Allow Maven to resolve the dependencies.
3. Open:

   ```text
   src/main/java/com/example/industrialmonitoring/IndustrialMonitoringMain.java
   ```

4. Run `IndustrialMonitoringMain` using the IDE's Run action.

### Run the Tests

From the project root, execute:

```bash
mvn clean test
```

Or run `IndustrialMonitoringServiceTest` directly in IntelliJ IDEA.

## Testing

The original technical assessment documentation describes four industrial monitoring unit-test scenarios:

- A reading within the valid range does not generate an alarm.
- A reading above the maximum allowed value generates an alarm.
- A reading below the minimum allowed value generates an alarm.
- Alarm history stores generated alarms.

## Design

The application separates data models from monitoring logic:

- **Model layer:** `Sensor`, `SensorType`, `SensorReading`, `Alarm`, and `AlarmSeverity` represent the simulated industrial monitoring domain.
- **Service layer:** `SensorMonitoringService` performs range checks, alarm generation, severity classification, maintenance recommendations, and history management.
- **Persistence service:** `AlarmFileService` writes generated alarm records to JSON.

This organization demonstrates Java OOP concepts and separation of responsibilities.

## Current Limitations

- Sensor values are simulated rather than collected from physical devices.
- The application does not connect to a real PLC or SCADA platform.
- Alarm history is exported to JSON but is not automatically restored at startup.
- The application uses a console interface rather than a graphical dashboard.

## Possible Future Improvements

- Integrate with physical sensors or PLC data sources.
- Add configurable alarm thresholds and sensor definitions.
- Reload historical alarms from the JSON file on startup.
- Add a database for long-term sensor and alarm storage.
- Implement real-time visualization and a monitoring dashboard.
- Expand automated tests and introduce additional alarm-handling scenarios.

## Project Context

This project was originally developed alongside a separate **Train Ticketing Application** as part of the same Java technical assessment. It is documented here as an independent application to highlight its industrial automation concepts and Java implementation.

## Author

Agnes-Maria Tanko
