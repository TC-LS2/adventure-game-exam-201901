package com.drpicox.game.combinations;

import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CombinationsController {

    private CombinationRepository combinationRepository;

    public CombinationsController(CombinationRepository combinationRepository) {
        this.combinationRepository = combinationRepository;
    }

    public Combination getCombination(List<String> ingredients) {
        return this.combinationRepository.findAll()
                .stream().filter(combination -> combination.match(ingredients))
                .findAny().orElse(null);
    }
}
