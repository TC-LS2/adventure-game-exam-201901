package com.drpicox.game.command.runners;

import com.drpicox.game.command.*;
import com.drpicox.game.players.PlayerController;
import com.drpicox.game.rooms.RoomController;
import org.springframework.beans.factory.annotation.Autowired;

@CommandComponent("attack")
public class AttackCommandRunner implements Command {

    @Autowired
    RoomController roomController;
    @Autowired
    PlayerController playerController;

    @Override
    public void run(CommandRequest request, CommandResponse response) {
        var player = playerController.getPlayer(request.getUsername());
        var room = player.getRoom();
        var monster = room.getMonster();

        if (monster == null)
            throw new IllegalCommandException("no-monster", "There is no monster in this room.");

        var code = "attack";
        var playerAttack = player.getAttack();
        var playerDefense = player.getDefense();

        var monsterAttack = monster.getAttack();
        var monsterDefense = monster.getDefense();

        var monsterPoints = playerAttack - monsterDefense;
        var playerPoints = monsterAttack - playerDefense;

        if (monsterPoints >= 0) {
            roomController.killMonster(room);
            response.append("code", "monster-killed");
        }

        if (playerPoints > player.getLifePoints()) {
            playerController.killPlayer(player, roomController.getInitialRoom());
            throw new IllegalCommandException("player-killed", "Monster killed you.");
        }
        if (playerPoints > 0) {
            playerController.takeDamage(player, playerPoints);
            response.append("code", "player-damaged");
        }
    }

}
