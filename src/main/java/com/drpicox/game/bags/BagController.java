package com.drpicox.game.bags;

import com.drpicox.game.items.Item;
import com.drpicox.game.players.Player;
import org.springframework.stereotype.Controller;

@Controller
public class BagController {

    private BagRepository bagRepository;

    public BagController(BagRepository bagRepository) {
        this.bagRepository = bagRepository;
    }

    public void takeItem(Player owner, Item item) {
        var bag = getBag(owner);
        bag.takeItem(item);
        bagRepository.save(bag);
    }

    public Item dropItem(Player owner, String itemName) {
        var bag = getBag(owner);
        var item = bag.dropItem(itemName);
        bagRepository.save(bag);
        return item;
    }

    public Bag getBag(Player owner) {
        var bag = bagRepository.findById(owner.getUsername()).orElse(null);
        if (bag == null) {
            bag = new Bag(owner);
            bagRepository.save(bag);
        }
        return bag;
    }
}
