package com.drpicox.game.world;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;

public class World {

    private Map<String, String> definitions;

    @JsonCreator
    public World(Map<String, String> definitions) {
        this.definitions = definitions;
    }

    public String get(String type) {
        return definitions.get(type);
    }

    @JsonValue
    public Map getJsonObject() {
        return definitions;
    }

}
