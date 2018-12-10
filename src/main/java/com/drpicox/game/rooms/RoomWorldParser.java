package com.drpicox.game.rooms;

import com.drpicox.game.world.World;
import com.drpicox.game.world.WorldParser;
import com.drpicox.game.world.WorldParserComponent;
import org.springframework.core.annotation.Order;

@WorldParserComponent
@Order(3)
public class RoomWorldParser implements WorldParser {

    private RoomController roomController;

    public RoomWorldParser(RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void parse(World world) {
        roomController.read(world.get("rooms"));
    }
}
