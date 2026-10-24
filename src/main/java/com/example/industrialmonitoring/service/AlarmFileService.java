package com.example.industrialmonitoring.service;

import com.example.industrialmonitoring.model.Alarm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AlarmFileService {
    private static final String ALARMS_FILE_PATH = "data/alarms.json";

    private final ObjectMapper objectMapper;

    public AlarmFileService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void saveAlarm(Alarm alarm) {
        try {
            File file = new File(ALARMS_FILE_PATH);
            createDataDirectoryIfMissing(file);

            List<Map<String, Object>> alarms = readExistingAlarms(file);
            alarms.add(convertAlarmToMap(alarm));

            objectMapper.writeValue(file, alarms);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to save alarm to JSON file: " + exception.getMessage(), exception);
        }
    }

    private void createDataDirectoryIfMissing(File file) {
        File parentDirectory = file.getParentFile();

        if (parentDirectory != null && !parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }
    }

    private List<Map<String, Object>> readExistingAlarms(File file) throws IOException {
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        return objectMapper.readValue(
                file,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class)
        );
    }

    private Map<String, Object> convertAlarmToMap(Alarm alarm) {
        Map<String, Object> alarmMap = new LinkedHashMap<>();

        alarmMap.put("alarmId", alarm.getAlarmId());
        alarmMap.put("sensorId", alarm.getSensor().getSensorId());
        alarmMap.put("sensorName", alarm.getSensor().getName());
        alarmMap.put("sensorType", alarm.getSensor().getType().toString());
        alarmMap.put("actualValue", alarm.getActualValue());
        alarmMap.put("unit", alarm.getSensor().getUnit());
        alarmMap.put("minimumAllowedValue", alarm.getSensor().getMinimumAllowedValue());
        alarmMap.put("maximumAllowedValue", alarm.getSensor().getMaximumAllowedValue());
        alarmMap.put("message", alarm.getMessage());
        alarmMap.put("timestamp", alarm.getTimestamp().toString());
        alarmMap.put("severity", alarm.getSeverity().toString());
        alarmMap.put("recommendedAction", alarm.getRecommendedAction());

        return alarmMap;
    }
}