package com.drpicox.game.world;

import java.util.HashMap;
import java.util.regex.Pattern;

public class TextWorldFactory {

    public World create(String... lines) {
        var world = new HashMap<String, String>();

        var kind = "rooms";
        for (var line : lines) {
            var regex = Pattern.compile(" ?=+[= #~-]+([a-z]+).*:.*");
            var match = regex.matcher(line);
            if (match.matches()) {
                kind = match.group(1);
            } else {
                var value = world.get(kind);
                if (value == null) value = "";
                world.put(kind, value + "\n" + line);
            }
        }

        return new World(world);
    }

}
