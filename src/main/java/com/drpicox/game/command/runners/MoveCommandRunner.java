package com.drpicox.game.command.runners;

import com.drpicox.game.command.*;
import com.drpicox.game.players.Player;
import com.drpicox.game.players.PlayerController;
import com.drpicox.game.rooms.Direction;
import com.drpicox.game.rooms.Exit;
import com.drpicox.game.rooms.Room;
import com.drpicox.game.rooms.RoomController;

@CommandComponent("move")
public class MoveCommandRunner implements Command {

    private RoomController roomController;
    private PlayerController playerController;

    public MoveCommandRunner(RoomController roomController, PlayerController playerController) {
        this.roomController = roomController;
        this.playerController = playerController;
    }

    private Direction getDirection(CommandRequest request) {
        var directionName = request.getArgument(0);
        var direction = Direction.valueOf(directionName.toUpperCase());
        return direction;
    }

    @Override
    public void run(CommandRequest request, CommandResponse response) {
        var direction = getDirection(request);
        var player = playerController.getPlayer(request.getUsername());
        var room = player.getRoom();
        var exit = room.getExit(direction);

        if (exit == null)
            throw new IllegalCommandException("no-exit", "Exit " + direction.name() + " does not exists.");

        if (!exit.isOpen()) {
            openExit(player, room, exit);
        }
        move(player, direction, room);

    }

    private void openExit(Player player, Room room, Exit exit) {
        var code = exit.getCode();
        var key = player.getKey();

        if (key == null || !key.opens(code))
            throw new IllegalCommandException("exit-closed", "Exit " + exit.getDirection().name() + " is closed and requires a key.");

        roomController.open(room, exit.getDirection());
        playerController.leaveItem(player, key);
    }

    private void move(Player player, Direction direction, Room room) {
        var destinationRoom = roomController.getDestinationRoom(room, direction);
        playerController.moveTo(player, destinationRoom);
    }

}
