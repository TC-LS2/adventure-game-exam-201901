package com.drpicox.game.bags;

import com.drpicox.game.command.*;
import com.drpicox.game.players.PlayerController;

@CommandComponent("equip")
public class EquipCommandRunner implements Command {

    private PlayerController playerController;
    private BagController bagController;

    public EquipCommandRunner(PlayerController playerController, BagController bagController) {
        this.playerController = playerController;
        this.bagController = bagController;
    }

    @Override
    public void run(CommandRequest request, CommandResponse response) {
        var itemName = request.getArgument(0);
        var player = playerController.getPlayer(request.getUsername());
        var item = bagController.dropItem(player, itemName);
        if (item == null)
            throw new IllegalCommandException("no-item", "This item is not in the bag.");

        var droppedItem = playerController.takeItem(player, item);
        bagController.takeItem(player, droppedItem);
    }

}
