package com.drpicox.game.combinations;

import com.drpicox.game.utils.Parser;
import com.drpicox.game.world.World;
import com.drpicox.game.world.WorldParser;
import com.drpicox.game.world.WorldParserComponent;

@WorldParserComponent
public class CombinationsWorldParser implements WorldParser {

    private CombinationRepository combinationRepository;

    public CombinationsWorldParser(CombinationRepository combinationRepository) {
        this.combinationRepository = combinationRepository;
    }

    @Override
    public void parse(World world) {
        var combinations = world.get("combinations");
        var parser = new Parser(combinations);

        while (parser.hasNext()) {
            var result = parser.readWord();
            var ingredients = parser.skipChar(':').skipSpaces().readLine();

            var combination = new Combination(ingredients, result);
            combinationRepository.save(combination);
        }
    }
}
