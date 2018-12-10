package com.drpicox.game.bags;

import com.drpicox.game.command.*;
import com.drpicox.game.players.PlayerController;
import com.drpicox.game.rooms.RoomController;

@CommandComponent("get")
public class GetCommandRunner implements Command {

    private RoomController roomController;
    private BagController bagController;
    private PlayerController playerController;

    public GetCommandRunner(RoomController roomController, BagController bagController, PlayerController playerController) {
        this.roomController = roomController;
        this.bagController = bagController;
        this.playerController = playerController;
    }

    @Override
    public void run(CommandRequest request, CommandResponse response) {
        var player = playerController.getPlayer(request.getUsername());
        var room = player.getRoom();
        var item = room.getItem();
        if (item == null)
            throw new IllegalCommandException("no-item", "There is no item in this room.");

        var droppedItem = playerController.takeItem(player, item);
        if (droppedItem != null) bagController.takeItem(player, droppedItem);
        roomController.removeItem(room, item);
    }

}
