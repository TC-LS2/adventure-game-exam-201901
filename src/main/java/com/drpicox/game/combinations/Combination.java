package com.drpicox.game.combinations;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Combination {

    @Id
    private String ingredients;

    private String result;

    public Combination(String ingredients, String result) {
        this.ingredients = ingredients;
        this.result = result;
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

    Combination() {}

}
