/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.drpicox.game;

import com.drpicox.game.rooms.Direction;
import com.drpicox.game.tools.MustacheGameStringifier;
import com.drpicox.game.tools.WorldBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class KeepYourThingsTests {

    @Autowired
    private TestHelper helper;

    @Before
    @After
    public void cleanup() throws Exception {
        helper.cleanup();

        var mustacheStringifier = new MustacheGameStringifier();
        decorateMustache(mustacheStringifier);
        helper.setGameResultStringifier(mustacheStringifier);
    }

    public static void decorateMustache(MustacheGameStringifier mustache) {
        WhoIsThereTests.decorateMustache(mustache);

        mustache.setBody("" +
                "{{#room}}{{>room}}{{/room}}" +
                "{{#player}}{{>player}}{{/player}}" +
                "{{#roomPlayers}}{{>roomPlayers}}{{/roomPlayers}}" +
                "{{#bag}}{{#items}}{{>bag}}{{/items}}{{/bag}}");

        mustache.setTemplate("bag", "" +
                "{{#-first}}You have: {{/-first}}" +
                "{{^-first}}, {{/-first}}" +
                "{{>bagItem}}" +
                "{{#-last}}.\n{{/-last}}");

        mustache.setTemplate("bagItem", "{{name}}({{type}})");

    }

    public static WorldBuilder buildWorld() {
        return new WorldBuilder()
                .name("Hall of Warriors")
                .description("Great hall displaying the best artifacts from the greater warriors.")
                .exit(Direction.EAST, -1)
                .north()
                .name("Swords corridor")
                .description("The best swords of the world are displayed in this corridor")
                .item("dagger", "weapon", 10)
                .north()
                .name("Swords end")
                .description("The best swords of the world are displayed in this corridor")
                .item("excalibur", "weapon", 20)
                .east()
                .name("Shields corridor")
                .description("The best shields of the world are displayed in this corridor")
                .item("woodshield", "shield", 2)
                .east()
                .name("Shields end")
                .description("The best shields of the world are displayed in this corridor")
                .item("ancile", "shield", 10)
                .south()
                .name("Sunny room")
                .description("Just a pleasant room with huge windows to enjoy the sun")
                .item("key", "key", 123)
                .south()
                .name("Misterious room")
                .description("Where this room goes?")
                .item("rustykey", "key", 234)
                .exit(Direction.WEST, 123)
                .west()
                .name("Secret room")
                .description("There is the secret scroll.")
                .exit(Direction.WEST, -1)
                ;
    }

    @Test
    public void world_text_snapshot() {
        assertThat(buildWorld().build(), is("" +
                "== rooms:\n" +
                "0 0:Hall of Warriors:0 -1 -1 -1:nothing\n" +
                "Great hall displaying the best artifacts from the greater warriors.\n" +
                "::::\n" +
                "1 0:Swords corridor:0 0 -1 -1:dagger\n" +
                "The best swords of the world are displayed in this corridor\n" +
                "::::\n" +
                "2 0:Swords end:-1 0 0 -1:excalibur\n" +
                "The best swords of the world are displayed in this corridor\n" +
                "::::\n" +
                "2 1:Shields corridor:-1 -1 0 0:woodshield\n" +
                "The best shields of the world are displayed in this corridor\n" +
                "::::\n" +
                "2 2:Shields end:-1 0 -1 0:ancile\n" +
                "The best shields of the world are displayed in this corridor\n" +
                "::::\n" +
                "1 2:Sunny room:0 0 -1 -1:key\n" +
                "Just a pleasant room with huge windows to enjoy the sun\n" +
                "::::\n" +
                "0 2:Misterious room:0 -1 -1 123:rustykey\n" +
                "Where this room goes?\n" +
                "::::\n" +
                "0 1:Secret room:-1 -1 0 -1:nothing\n" +
                "There is the secret scroll.\n" +
                "::::\n" +
                "== items:\n" +
                "dagger: weapon 10\n" +
                "excalibur: weapon 20\n" +
                "woodshield: shield 2\n" +
                "ancile: shield 10\n" +
                "key: key 123\n" +
                "rustykey: key 234\n"));
    }

    @Test
    public void world_map_snapshot() {
        assertThat(buildWorld().map(), is("" +
                "+----------------+      +----------------+      +---------------+      \n" +
                "|   SWORDS END   |      |SHIELDS CORRIDOR|      |  SHIELDS END  |      \n" +
                "|the best swor...o------othe best shie...o------othe best shi...|      \n" +
                "|     (excalibur)|      |    (woodshield)|      |       (ancile)|      \n" +
                "+-------o--------+      +----------------+      +------o--------+      \n" +
                "        |                                              |               \n" +
                "        |                                              |               \n" +
                "+-------o--------+                              +------o--------+      \n" +
                "| SWORDS CORRIDOR|                              |   SUNNY ROOM  |      \n" +
                "|the best swor...|                              |just a pleas...|      \n" +
                "|        (dagger)|                              |          (key)|      \n" +
                "+-------o--------+                              +------o--------+      \n" +
                "        |                                              |               \n" +
                "        |                                              |               \n" +
                "+-------o--------+      +----------------+      +------o--------+      \n" +
                "|HALL OF WARRIORS|      |   SECRET ROOM  |      |MISTERIOUS ROOM|      \n" +
                "|great hall di...x      xthere is the ...o------▒where this r...|      \n" +
                "|                |      |                |      |     (rustykey)|      \n" +
                "+----------------+      +----------------+      +---------------+      \n"));
    }

    @Test
    public void carry_more_than_one_weapon() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "look");
        helper.assertResult("" +
                "Hall of Warriors\n" +
                "Great hall displaying the best artifacts from the\n" +
                "greater warriors.\n" +
                "Exits: north.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.");

        helper.runCommand("kirito", "move", "north");
        helper.assertResult("" +
                "Swords corridor\n" +
                "The best swords of the world are displayed in\n" +
                "this corridor\n" +
                "There is the dagger weapon.\n" +
                "Exits: north, south.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.");

        helper.runCommand("kirito", "get");
        helper.assertResult("" +
                "Swords corridor\n" +
                "The best swords of the world are displayed in\n" +
                "this corridor\n" +
                "Exits: north, south.\n" +
                "Player has the dagger weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.");

        helper.runCommand("kirito", "move", "north");
        helper.assertResult("" +
                "Swords end\n" +
                "The best swords of the world are displayed in\n" +
                "this corridor\n" +
                "There is the excalibur weapon.\n" +
                "Exits: south, east.\n" +
                "Player has the dagger weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.");

        helper.runCommand("kirito", "get");
        helper.assertResult("" +
                "Swords end\n" +
                "The best swords of the world are displayed in\n" +
                "this corridor\n" +
                "Exits: south, east.\n" +
                "Player has the excalibur weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: dagger(weapon).");
    }

    @Test
    public void equip_the_other_sword() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "equip", "dagger");
        helper.assertResult("" +
                "Swords end\n" +
                "The best swords of the world are displayed in\n" +
                "this corridor\n" +
                "Exits: south, east.\n" +
                "Player has the dagger weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: excalibur(weapon).");
    }

    @Test
    public void you_cannot_equip_what_you_dont_have() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "equip", "dagger");
        helper.runCommand("kirito", "equip", "anduril")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("no-item")));
    }

    @Test
    public void carry_more_than_one_shield() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "equip", "dagger");
        helper.runCommand("kirito", "move", "east");
        helper.assertResult("" +
                "Shields corridor\n" +
                "The best shields of the world are displayed in\n" +
                "this corridor\n" +
                "There is the woodshield shield.\n" +
                "Exits: east, west.\n" +
                "Player has the dagger weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: excalibur(weapon).");
        helper.runCommand("kirito", "get");
        helper.assertResult("" +
                "Shields corridor\n" +
                "The best shields of the world are displayed in\n" +
                "this corridor\n" +
                "Exits: east, west.\n" +
                "Player has the woodshield shield.\n" +
                "Player has the dagger weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: excalibur(weapon).");
        helper.runCommand("kirito", "move", "east");
        helper.assertResult("" +
                "Shields end\n" +
                "The best shields of the world are displayed in\n" +
                "this corridor\n" +
                "There is the ancile shield.\n" +
                "Exits: south, west.\n" +
                "Player has the woodshield shield.\n" +
                "Player has the dagger weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: excalibur(weapon).");
        helper.runCommand("kirito", "get");
        helper.assertResult("" +
                "Shields end\n" +
                "The best shields of the world are displayed in\n" +
                "this corridor\n" +
                "Exits: south, west.\n" +
                "Player has the ancile shield.\n" +
                "Player has the dagger weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: excalibur(weapon), woodshield(shield).");
    }

    @Test
    public void equip_the_other_shield() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "equip", "dagger");
        helper.runCommand("kirito", "move", "east");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "east");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "equip", "woodshield");
        helper.assertResult("" +
                "Shields end\n" +
                "The best shields of the world are displayed in\n" +
                "this corridor\n" +
                "Exits: south, west.\n" +
                "Player has the woodshield shield.\n" +
                "Player has the dagger weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: excalibur(weapon), ancile(shield).");
    }

    @Test
    public void carry_more_than_one_key() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "equip", "dagger");
        helper.runCommand("kirito", "move", "east");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "east");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "equip", "woodshield");
        helper.runCommand("kirito", "move", "south");
        helper.assertResult("" +
                "Sunny room\n" +
                "Just a pleasant room with huge windows to enjoy\n" +
                "the sun\n" +
                "There is the key key.\n" +
                "Exits: north, south.\n" +
                "Player has the woodshield shield.\n" +
                "Player has the dagger weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: excalibur(weapon), ancile(shield).");
        helper.runCommand("kirito", "get");
        helper.assertResult("" +
                "Sunny room\n" +
                "Just a pleasant room with huge windows to enjoy\n" +
                "the sun\n" +
                "Exits: north, south.\n" +
                "Player has the key key.\n" +
                "Player has the woodshield shield.\n" +
                "Player has the dagger weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: excalibur(weapon), ancile(shield).");
        helper.runCommand("kirito", "move", "south");
        helper.assertResult("" +
                "Misterious room\n" +
                "Where this room goes?\n" +
                "There is the rustykey key.\n" +
                "Exits: north, west (closed).\n" +
                "Player has the key key.\n" +
                "Player has the woodshield shield.\n" +
                "Player has the dagger weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: excalibur(weapon), ancile(shield).");
        helper.runCommand("kirito", "get");
        helper.assertResult("" +
                "Misterious room\n" +
                "Where this room goes?\n" +
                "Exits: north, west (closed).\n" +
                "Player has the rustykey key.\n" +
                "Player has the woodshield shield.\n" +
                "Player has the dagger weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: excalibur(weapon), ancile(shield),\n" +
                "key(key).");
    }

    @Test
    public void but_only_one_opens_the_door() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "equip", "dagger");
        helper.runCommand("kirito", "move", "east");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "east");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "equip", "woodshield");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "south");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "west")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("exit-closed")));
        helper.runCommand("kirito", "equip", "key");
        helper.assertResult("" +
                "Misterious room\n" +
                "Where this room goes?\n" +
                "Exits: north, west (closed).\n" +
                "Player has the key key.\n" +
                "Player has the woodshield shield.\n" +
                "Player has the dagger weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: excalibur(weapon), ancile(shield),\n" +
                "rustykey(key).");
        helper.runCommand("kirito", "move", "west");
        helper.assertResult("" +
                "Secret room\n" +
                "There is the secret scroll.\n" +
                "Exits: east.\n" +
                "Player has the woodshield shield.\n" +
                "Player has the dagger weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: excalibur(weapon), ancile(shield),\n" +
                "rustykey(key).");
    }
}
