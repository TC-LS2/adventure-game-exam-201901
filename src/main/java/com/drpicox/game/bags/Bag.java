package com.drpicox.game.bags;

import com.drpicox.game.items.Item;
import com.drpicox.game.players.Player;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Bag {

    @Id
    private String username;

    @OneToOne @PrimaryKeyJoinColumn
    private Player owner;

    @ManyToMany
    private List<Item> items = new ArrayList<>();

    public Bag(Player owner) {
        this.username = owner.getUsername();
        this.owner = owner;
    }

    public void takeItem(Item item) {
        this.items.add(item);
    }

    public Item dropItem(String itemName) {
        var item = getItem(itemName);
        if (item != null) this.items.remove(item);
        return item;
    }

    public Item getItem(String itemName) {
        return this.items.stream().filter(item -> item.hasName(itemName)).findAny().orElse(null);
    }

    @JsonValue
    public Map getJsonObject() {
        Map result = new LinkedHashMap();
        result.put("items", items);
        return result;
    }

    Bag() {
    }

}
