package com.drpicox.game.command.runners;

import com.drpicox.game.command.AfterCommandComponent;
import com.drpicox.game.command.Command;
import com.drpicox.game.command.CommandRequest;
import com.drpicox.game.command.CommandResponse;
import com.drpicox.game.players.PlayerController;

@AfterCommandComponent
public class LookPlayerAfterCommand implements Command {

    private PlayerController playerController;

    public LookPlayerAfterCommand(PlayerController playerController) {
        this.playerController = playerController;
    }

    @Override
    public void run(CommandRequest request, CommandResponse response) {
        var player = playerController.getPlayer(request.getUsername());
        response.put("player", player);
    }
}
