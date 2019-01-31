package com.drpicox.game.combinations;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PlayerLevel {

    @Id
    private String username;


    private int level;

    public PlayerLevel(String username, int level) {
        this.username = username;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void upgradeLevelTo(int max) {
        this.level = Math.max(max, level);
    }

    PlayerLevel() {}
}
