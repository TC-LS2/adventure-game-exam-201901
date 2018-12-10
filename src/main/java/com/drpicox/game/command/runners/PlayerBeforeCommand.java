package com.drpicox.game.command.runners;

import com.drpicox.game.command.BeforeCommandComponent;
import com.drpicox.game.command.Command;
import com.drpicox.game.command.CommandRequest;
import com.drpicox.game.command.CommandResponse;
import com.drpicox.game.players.Player;
import com.drpicox.game.players.PlayerController;
import com.drpicox.game.rooms.Room;
import com.drpicox.game.rooms.RoomController;
import com.drpicox.game.world.DefaultWorldFactory;
import com.drpicox.game.world.WorldRestController;

@BeforeCommandComponent
public class PlayerBeforeCommand implements Command {

    private PlayerController playerController;
    private RoomController roomController;
    private WorldRestController worldRestController;

    public PlayerBeforeCommand(PlayerController playerController, RoomController roomController, WorldRestController worldRestController) {
        this.playerController = playerController;
        this.roomController = roomController;
        this.worldRestController = worldRestController;
    }

    @Override
    public void run(CommandRequest request, CommandResponse response) {
        var username = request.getUsername();
        Player player = playerController.getPlayer(username);

        if (player == null) {
            var initialRoom = getInitialRoom();
            playerController.createPlayer(username, initialRoom);
        }
    }

    private Room getInitialRoom() {
        var initialRoom = roomController.getInitialRoom();
        if (initialRoom == null) {
            var world = new DefaultWorldFactory().create();
            worldRestController.replace(world);
            initialRoom = roomController.getInitialRoom();
        }
        return initialRoom;
    }

}
