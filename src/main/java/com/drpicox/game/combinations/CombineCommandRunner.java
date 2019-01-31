package com.drpicox.game.combinations;

import com.drpicox.game.bags.BagController;
import com.drpicox.game.command.*;
import com.drpicox.game.items.ItemController;
import com.drpicox.game.players.PlayerController;

@CommandComponent("combine")
public class CombineCommandRunner implements Command {

    private PlayerController playerController;
    private ItemController itemController;
    private CombinationsController combinationsController;
    private BagController bagController;

    public CombineCommandRunner(PlayerController playerController, ItemController itemController, CombinationsController combinationsController, BagController bagController) {
        this.playerController = playerController;
        this.itemController = itemController;
        this.combinationsController = combinationsController;
        this.bagController = bagController;
    }

    @Override
    public void run(CommandRequest request, CommandResponse response) {
        var ingredients = request.getArguments();
        var player = playerController.getPlayer(request.getUsername());
        var bag = bagController.getBag(player);
        var level = combinationsController.getPlayerLevel(request.getUsername()).getLevel();

        for (var ingredient: ingredients) {
            var item = bag.getItem(ingredient);
            if (item == null)
                throw new IllegalCommandException("no-item", "This item is not in the bag.");
        }

        var combination = combinationsController.getCombination(ingredients);
        if (combination == null) {
            throw new IllegalCommandException("no-combination", "There is no combination.");
        }
        if (combination.getLevelRequired() > level) {
            throw new IllegalCommandException("no-level", "Your level is too low.");
        }

        for (var ingredient: ingredients) {
            bagController.dropItem(player, ingredient);
        }

        var item = itemController.get(combination.getResult());
        bagController.takeItem(player, item);

        combinationsController.upgradePlayerLevelTo(request.getUsername(), combination.getLevelUpgrade());
    }

}
