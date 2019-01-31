package com.drpicox.game.combinations;

import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CombinationsController {

    private CombinationRepository combinationRepository;
    private PlayerLevelRepository playerLevelRepository;

    public CombinationsController(CombinationRepository combinationRepository, PlayerLevelRepository playerLevelRepository) {
        this.combinationRepository = combinationRepository;
        this.playerLevelRepository = playerLevelRepository;
    }

    public Combination getCombination(List<String> ingredients) {
        return this.combinationRepository.findAll()
                .stream().filter(combination -> combination.match(ingredients))
                .findAny().orElse(null);
    }

    public PlayerLevel getPlayerLevel(String username) {
        var playerLevel = playerLevelRepository.findById(username).orElse(null);
        if (playerLevel == null) {
            playerLevel = new PlayerLevel(username, 1);
            playerLevelRepository.save(playerLevel);
        }
        return playerLevel;
    }

    public void upgradePlayerLevelTo(String username, int levelUpgrade) {
        var level = getPlayerLevel(username);
        level.upgradeLevelTo(levelUpgrade);
        playerLevelRepository.save(level);
    }
}
