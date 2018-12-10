package com.drpicox.game.items;

import javax.persistence.Entity;

@Entity
public class Food extends Item {

    private int lifePoints;

    public Food(String name, int lifePoints) {
        super(name);
        this.lifePoints = lifePoints;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    Food() {
    }
}
