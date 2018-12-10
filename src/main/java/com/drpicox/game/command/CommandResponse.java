package com.drpicox.game.command;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommandResponse {

    private Map<String, Object> result = new LinkedHashMap<>();

    public void put(String key, Object value) {
        result.put(key, value);
    }

    public void append(String key, String value) {
        var newValue = result.get("key");
        if (newValue != null) {
            newValue = value;
        } else {
            newValue += "," + value;
        }
        result.put(key, newValue);
    }

    @JsonValue
    public Map getJsonObject() {
        return result;
    }
}
