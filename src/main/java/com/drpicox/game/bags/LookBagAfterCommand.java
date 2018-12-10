package com.drpicox.game.bags;

import com.drpicox.game.command.AfterCommandComponent;
import com.drpicox.game.command.Command;
import com.drpicox.game.command.CommandRequest;
import com.drpicox.game.command.CommandResponse;
import com.drpicox.game.players.PlayerController;

@AfterCommandComponent
public class LookBagAfterCommand implements Command {

    private PlayerController playerController;
    private BagController bagController;

    public LookBagAfterCommand(PlayerController playerController, BagController bagController) {
        this.playerController = playerController;
        this.bagController = bagController;
    }

    @Override
    public void run(CommandRequest request, CommandResponse response) {
        var player = playerController.getPlayer(request.getUsername());
        var bag = bagController.getBag(player);
        response.put("bag", bag);
    }
}
