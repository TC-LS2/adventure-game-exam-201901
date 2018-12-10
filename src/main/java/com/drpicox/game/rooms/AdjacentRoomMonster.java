package com.drpicox.game.rooms;

import com.drpicox.game.monsters.Monster;

public class AdjacentRoomMonster extends AdjacentRoom {

    private Monster monster;

    public AdjacentRoomMonster(AdjacentRoom adjacentRoom, Monster monster) {
        super(adjacentRoom);
        this.monster = monster;
    }

    public Monster getMonster() {
        return monster;
    }
}
