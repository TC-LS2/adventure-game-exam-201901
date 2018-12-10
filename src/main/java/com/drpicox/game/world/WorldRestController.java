package com.drpicox.game.world;

import com.drpicox.game.utils.SuccessResponse;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/world")
public class WorldRestController {

    private WorldParserCollector worldParserCollector;

    public WorldRestController(WorldParserCollector worldParserCollector) {
        this.worldParserCollector = worldParserCollector;
    }

    @PutMapping
    public SuccessResponse replace(@RequestBody World world) {
        worldParserCollector.parse(world);
        return new SuccessResponse("success");
    }
}
