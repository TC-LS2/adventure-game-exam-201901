package com.drpicox.game.world;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorldParserCollector implements WorldParser {

    private List<WorldParser> worldParserList;

    public WorldParserCollector(
            @Qualifier("com.drpicox.game.world.worldParserList")
                    List<WorldParser> worldParserList
    ) {
        this.worldParserList = worldParserList;
    }

    @Override
    public void parse(World world) {
        worldParserList.forEach(parser -> parser.parse(world));
    }
}
