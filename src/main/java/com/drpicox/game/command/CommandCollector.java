package com.drpicox.game.command;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandCollector implements Command {

    private List<Command> beforeCommandList;
    private Map<String, Command> commandMap = new HashMap<>();
    private List<Command> afterCommandList;

    public CommandCollector(
            @Qualifier("com.drpicox.game.command.beforeCommandList")
                    List<Command> beforeCommandList,
            @Qualifier("com.drpicox.game.command.commandList")
                    List<Command> commandList,
            @Qualifier("com.drpicox.game.command.afterCommandList")
                    List<Command> afterCommandList
    ) {
        this.beforeCommandList = beforeCommandList;
        commandList.forEach(command -> {
            var commandName = command.getClass().getAnnotation(CommandComponent.class).value();
            commandMap.put(commandName, command);
        });
        this.afterCommandList = afterCommandList;
    }

    @Override
    public void run(CommandRequest request, CommandResponse response) {
        var command = getCommand(request);

        runBeforeCommand(request, response);
        command.run(request, response);
        runAfterCommand(request, response);
    }

    private Command getCommand(CommandRequest request) {
        var commandName = request.getCommand();
        var command = commandMap.get(commandName);

        if (command == null) {
            throw new IllegalCommandException("unknown-command", "Command '" + commandName + "' unkown");
        }

        return command;
    }

    private void runBeforeCommand(CommandRequest request, CommandResponse response) {
        beforeCommandList.forEach(command -> command.run(request, response));
    }

    private void runAfterCommand(CommandRequest request, CommandResponse response) {
        afterCommandList.forEach(command -> command.run(request, response));
    }


}
