package com.drpicox.game.combinations;

import com.drpicox.game.command.AfterCommandComponent;
import com.drpicox.game.command.Command;
import com.drpicox.game.command.CommandRequest;
import com.drpicox.game.command.CommandResponse;

@AfterCommandComponent
public class LookPlayerLevelAfterCommand implements Command {

    private CombinationsController combinationsController;

    public LookPlayerLevelAfterCommand(CombinationsController combinationsController) {
        this.combinationsController = combinationsController;
    }

    @Override
    public void run(CommandRequest request, CommandResponse response) {
        var level = combinationsController.getPlayerLevel(request.getUsername()).getLevel();
        response.put("combinationPlayerLevel", level);
    }
}
