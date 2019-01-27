package com.drpicox.game.rooms;

import com.drpicox.game.items.Item;
import com.drpicox.game.monsters.Monster;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
public class Room {

    @EmbeddedId
    private RoomCoordinates coordinates;

    private String name;
    @ElementCollection
    private List<Exit> exits;
    @ManyToOne
    private Item item;
    @ManyToOne
    private Monster monster;
    @ManyToOne
    private Item initialItem;
    @ManyToOne
    private Monster initialMonster;
    @Lob
    private String description;

    public Room(RoomCoordinates coordinates, String name, List<Exit> exits, Item item, Monster monster, String description) {
        this.coordinates = coordinates;
        this.name = name;
        this.exits = exits;
        this.item = item;
        this.monster = monster;
        this.initialItem = item;
        this.initialMonster = monster;
        this.description = description;
    }


    public RoomCoordinates getCoordinates() {
        return coordinates;
    }

    public Exit getExit(Direction direction) {
        return exits.stream().filter(exit -> exit.isDirection(direction)).findAny().orElse(null);
    }

    public List<Direction> getOpenedDirections() {
        return exits.stream()
                .filter(exit -> exit.isOpen())
                .map(exit -> exit.getDirection())
                .collect(Collectors.toList());
    }

    public Item getItem() {
        return item;
    }

    public Monster getMonster() {
        return monster;
    }

    public void removeItem(Item item) {
        if (this.item == item) this.item = null;
    }

    public void killMonster() {
        this.item = monster.dropTreasure();
        this.monster = null;
    }

    public void open(Direction direction) {
        getExit(direction).open();
    }

    public void receiveItem(Item droppedItem) {
        this.item = droppedItem;
    }

    public void refresh() {
        this.item = this.initialItem;
        this.monster = this.initialMonster;

        for (Exit exit : exits) {
            exit.refresh();
        }
    }

    @JsonValue
    public Map getJsonObject() {
        Map result = new LinkedHashMap();
        result.put("name", name);
        result.put("description", description);
        result.put("exits", exits);
        result.put("item", item);
        result.put("monster", monster);
        return result;
    }

    Room() {
    }
}
