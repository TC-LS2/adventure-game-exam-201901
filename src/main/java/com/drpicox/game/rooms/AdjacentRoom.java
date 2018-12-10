package com.drpicox.game.rooms;

public class AdjacentRoom {

    private Room from;
    private Direction direction;
    private Room room;

    public AdjacentRoom(AdjacentRoom adjacentRoom) {
        this(adjacentRoom.from, adjacentRoom.direction, adjacentRoom.room);
    }

    public AdjacentRoom(Room from, Direction direction, Room room) {
        this.from = from;
        this.direction = direction;
        this.room = room;
    }

    public Room getFrom() {
        return from;
    }

    public Direction getDirection() {
        return direction;
    }

    public Room getRoom() {
        return room;
    }
}
