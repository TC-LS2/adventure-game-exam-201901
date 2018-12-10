package com.drpicox.game.command.runners;

import com.drpicox.game.command.Command;
import com.drpicox.game.command.CommandComponent;
import com.drpicox.game.command.CommandRequest;
import com.drpicox.game.command.CommandResponse;

@CommandComponent("look")
public class LookCommandRunner implements Command {

    @Override
    public void run(CommandRequest request, CommandResponse response) {
        // Nothing to do here
        // It just defines that "look" is a valid command
        // Real look is done with many @AfterCommandComponent
    }
}
