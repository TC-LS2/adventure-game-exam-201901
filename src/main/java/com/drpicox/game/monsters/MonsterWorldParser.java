package com.drpicox.game.monsters;

import com.drpicox.game.world.World;
import com.drpicox.game.world.WorldParser;
import com.drpicox.game.world.WorldParserComponent;
import org.springframework.core.annotation.Order;

@WorldParserComponent
@Order(2)
public class MonsterWorldParser implements WorldParser {

    private MonsterController monsterController;

    public MonsterWorldParser(MonsterController monsterController) {
        this.monsterController = monsterController;
    }

    @Override
    public void parse(World world) {
        monsterController.read(world.get("monsters"));
    }
}
