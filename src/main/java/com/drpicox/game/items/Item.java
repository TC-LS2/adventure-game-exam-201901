package com.drpicox.game.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {

    @Id
    private String name;

    @JsonCreator
    public Item(
            @JsonProperty("name") String name) {

        this.name = name;
    }

    public boolean hasName(String name) {
        return this.name.equals(name);
    }


    @JsonValue
    public Map getJsonObject() {
        var result = new LinkedHashMap();
        result.put("name", name);
        result.put("type", this.getClass().getName().toLowerCase().substring(23));
        return result;
    }

    Item() {
    }

}
