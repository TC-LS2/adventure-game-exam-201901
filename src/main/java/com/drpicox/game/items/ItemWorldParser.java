package com.drpicox.game.items;

import com.drpicox.game.world.World;
import com.drpicox.game.world.WorldParser;
import com.drpicox.game.world.WorldParserComponent;
import org.springframework.core.annotation.Order;

@WorldParserComponent
@Order(1)
public class ItemWorldParser implements WorldParser {

    private ItemController itemController;

    public ItemWorldParser(ItemController itemController) {
        this.itemController = itemController;
    }

    @Override
    public void parse(World world) {
        itemController.read(world.get("items"));
    }
}
