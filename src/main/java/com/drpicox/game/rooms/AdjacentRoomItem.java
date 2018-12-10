package com.drpicox.game.rooms;

import com.drpicox.game.items.Item;

public class AdjacentRoomItem<ITEM extends Item> extends AdjacentRoom {

    private ITEM item;

    public AdjacentRoomItem(AdjacentRoom adjacentRoom, ITEM item) {
        super(adjacentRoom);
        this.item = item;
    }

    public ITEM getItem() {
        return item;
    }
}
