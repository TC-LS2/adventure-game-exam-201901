package com.drpicox.game.combinations;

import com.drpicox.game.utils.Parser;
import com.drpicox.game.world.World;
import com.drpicox.game.world.WorldParser;
import com.drpicox.game.world.WorldParserComponent;

@WorldParserComponent
public class CombinationsWorldParser implements WorldParser {

    @Override
    public void parse(World world) {
        var combinations = world.get("combinations");
        var parser = new Parser(combinations);

        while (parser.hasNext()) {
            var name = parser.readWord();
            var rest = parser.readLine();

            System.out.println(name + rest);
        }
    }
}
