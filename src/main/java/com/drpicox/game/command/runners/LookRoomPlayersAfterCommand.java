package com.drpicox.game.command.runners;

import com.drpicox.game.command.AfterCommandComponent;
import com.drpicox.game.command.Command;
import com.drpicox.game.command.CommandRequest;
import com.drpicox.game.command.CommandResponse;
import com.drpicox.game.players.PlayerController;

@AfterCommandComponent
public class LookRoomPlayersAfterCommand implements Command {

    private PlayerController playerController;

    public LookRoomPlayersAfterCommand(PlayerController playerController) {
        this.playerController = playerController;
    }

    @Override
    public void run(CommandRequest request, CommandResponse response) {
        var player = playerController.getPlayer(request.getUsername());
        var room = player.getRoom();
        var roomPlayers = playerController.getPlayersAt(room);
        response.put("roomPlayers", roomPlayers);
    }
}
