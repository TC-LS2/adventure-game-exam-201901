package com.drpicox.game.combinations;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Combination {

    @Id
    private String ingredients;

    private String result;

    private int levelRequired;

    private int levelUpgrade;

    public Combination(String ingredients, String result, int levelRequired, int levelUpgrade) {
        this.ingredients = ingredients;
        this.result = result;
        this.levelRequired = levelRequired;
        this.levelUpgrade = levelUpgrade;
    }

    public String getResult() {
        return result;
    }

    public boolean match(List<String> actual) {
        var array = ingredients.split(",");

        if (array.length != actual.size()) {
            return false;
        }

        for (var ingredient: array) {
            if (!actual.contains(ingredient)) {
                return false;
            }
        }

        return true;
    }

    public int getLevelRequired() {
        return levelRequired;
    }

    public int getLevelUpgrade() {
        return levelUpgrade;
    }

    Combination() {}

}
