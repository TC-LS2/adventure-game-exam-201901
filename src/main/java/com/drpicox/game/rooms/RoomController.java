package com.drpicox.game.rooms;

import com.drpicox.game.items.Item;
import com.drpicox.game.utils.TimerTaskRunner;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Controller
public class RoomController {

    private static final int REFRESH_MILLISECONDS = 30000;

    private RoomParser roomParser;
    private RoomRepository roomRepository;
    private TimerTaskRunner timerTaskRunner;
    private Set<Room> scheduledRefreshes = ConcurrentHashMap.newKeySet();

    public RoomController(RoomParser roomParser, RoomRepository roomRepository, TimerTaskRunner timerTaskRunner) {
        this.roomParser = roomParser;
        this.roomRepository = roomRepository;
        this.timerTaskRunner = timerTaskRunner;
    }

    public List<AdjacentRoom> getAdjacentRooms(Room origin) {
        return origin.getOpenedDirections()
                .stream()
                .map(direction -> {
                    var destination = getDestinationRoom(origin, direction);
                    var adjacentRoom = new AdjacentRoom(origin, direction, destination);
                    return adjacentRoom;
                }).collect(Collectors.toList());
    }

    public List<AdjacentRoomItem<Item>> getAdjacentRoomsItems(Room origin) {
        return getAdjacentRooms(origin)
                .stream()
                .map(adjacentRoom -> new AdjacentRoomItem<Item>(
                        adjacentRoom,
                        adjacentRoom.getRoom().getItem()))
                .filter(adjacentRoomItem -> adjacentRoomItem.getItem() != null)
                .collect(Collectors.toList());
    }

    public <ITEM extends Item> List<AdjacentRoomItem<ITEM>> getAdjacentRoomsItemsOf(Room origin, Class<ITEM> type) {
        return getAdjacentRoomsItems(origin)
                .stream()
                .filter(adjacentRoomItem -> type.isInstance(adjacentRoomItem.getItem()))
                .map(itemAdjacentRoomItem -> (AdjacentRoomItem<ITEM>) itemAdjacentRoomItem)
                .collect(Collectors.toList());
    }

    public List<AdjacentRoomMonster> getAdjacentRoomsMonsters(Room origin) {
        return getAdjacentRooms(origin)
                .stream()
                .map(adjacentRoom -> new AdjacentRoomMonster(
                        adjacentRoom,
                        adjacentRoom.getRoom().getMonster()))
                .filter(adjacentRoomMonster -> adjacentRoomMonster.getMonster() != null)
                .collect(Collectors.toList());
    }

    public Room getDestinationRoom(Room room, Direction direction) {
        var target = room.getCoordinates().move(direction);
        return roomRepository.findById(target).orElse(null);
    }

    public Room getInitialRoom() {
        var origin = new RoomCoordinates(0, 0);
        var room = roomRepository.findById(origin).orElse(null);
        return room;
    }

    public void killMonster(Room room) {
        scheduleRoomRefresh(room);
        room.killMonster();
        roomRepository.save(room);
    }

    public void open(Room room, Direction direction) {
        scheduleRoomRefresh(room);
        room.open(direction);
        roomRepository.save(room);
    }

    public void receiveItem(Room room, Item item) {
        scheduleRoomRefresh(room);
        room.receiveItem(item);
        roomRepository.save(room);
    }

    public void removeItem(Room room, Item item) {
        scheduleRoomRefresh(room);
        room.removeItem(item);
        roomRepository.save(room);
    }

    public void scheduleRoomRefresh(Room room) {
        if (scheduledRefreshes.add(room)) {
            timerTaskRunner.run(() -> refreshRoom(room), REFRESH_MILLISECONDS);
        }
    }

    public void refreshRoom(Room room) {
        scheduledRefreshes.remove(room);
        room.refresh();
        roomRepository.save(room);
    }

    public void read(String rooms) {
        roomParser.read(rooms);
    }
}
