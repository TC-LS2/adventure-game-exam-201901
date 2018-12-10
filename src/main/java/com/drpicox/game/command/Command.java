package com.drpicox.game.command;

public interface Command {
    void run(CommandRequest request, CommandResponse response);
}
