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
public class CombineAndCreateTests {

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
        KeepYourThingsTests.decorateMustache(mustache);
    }

    public static WorldBuilder buildWorld() {
        return new WorldBuilder()
                .item("yakitori", "food", 4)

                .name("Kitchen")
                .description("A kitchen with some food.")
                .item("chiken", "food", 1)

                .east()
                .name("Garden")
                .description("Nice garden with nice plants")
                .item("stick", "weapon", 1)

                .north()
                .name("River")
                .description("Nice river with nice fishes")
                .item("rock", "weapon", 1)

                .west()
                .name("Closet")
                .description("Large food store")
                .item("onion", "food", 1)

                .line("combinations", "yakitori: chicken,stick")
                ;
    }

    @Test
    public void world_text_snapshot() {
        assertThat(buildWorld().build(), is("" +
                "== rooms:\n" +
                "0 0:Kitchen:0 -1 0 -1:chiken\n" +
                "A kitchen with some food.\n" +
                "::::\n" +
                "0 1:Garden:0 -1 -1 0:stick\n" +
                "Nice garden with nice plants\n" +
                "::::\n" +
                "1 1:River:-1 0 -1 0:rock\n" +
                "Nice river with nice fishes\n" +
                "::::\n" +
                "1 0:Closet:-1 0 0 -1:onion\n" +
                "Large food store\n" +
                "::::\n" +
                "== items:\n" +
                "yakitori: food 4\n" +
                "chiken: food 1\n" +
                "stick: weapon 1\n" +
                "rock: weapon 1\n" +
                "onion: food 1\n" +
                "== combinations:\n" +
                "yakitori: chicken,stick\n"));
    }

    @Test
    public void world_map_snapshot() {
        assertThat(buildWorld().map(), is("" +
                "+-------+      +------+      \n" +
                "| CLOSET|      | RIVER|      \n" +
                "|larg...o------onic...|      \n" +
                "|(onion)|      |(rock)|      \n" +
                "+--o----+      +--o---+      \n" +
                "   |              |          \n" +
                "   |              |          \n" +
                "+--o----+      +--o---+      \n" +
                "|KITCHEN|      |GARDEN|      \n" +
                "|a ki...o------onic...|      \n" +
                "|(chi...|      |(st...|      \n" +
                "+-------+      +------+      \n"));
    }

    @Test
    public void combine_items_into_new_items() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "look");
        helper.assertResult("Kitchen\n" +
                "A kitchen with some food.\n" +
                "There is the chiken food.\n" +
                "Exits: north, east.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.");
        helper.runCommand("kirito", "get");
        helper.assertResult("Kitchen\n" +
                "A kitchen with some food.\n" +
                "Exits: north, east.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: chiken(food).");

        helper.runCommand("kirito", "move", "east");
        helper.assertResult("Garden\n" +
                "Nice garden with nice plants\n" +
                "There is the stick weapon.\n" +
                "Exits: north, west.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: chiken(food).");
        helper.runCommand("kirito", "get");
        helper.assertResult("Garden\n" +
                "Nice garden with nice plants\n" +
                "Exits: north, west.\n" +
                "Player has the stick weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: chiken(food).");

        helper.runCommand("kirito", "move", "north");
        helper.assertResult("River\n" +
                "Nice river with nice fishes\n" +
                "There is the rock weapon.\n" +
                "Exits: south, west.\n" +
                "Player has the stick weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: chiken(food).");
        helper.runCommand("kirito", "get");
        helper.assertResult("River\n" +
                "Nice river with nice fishes\n" +
                "Exits: south, west.\n" +
                "Player has the rock weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: chiken(food), stick(weapon).");

        helper.runCommand("kirito", "combine", "chicken", "stick");
        helper.assertResult("River\n" +
                "Nice river with nice fishes\n" +
                "Exits: south, west.\n" +
                "Player has the rock weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: yakitori(food).");
    }

    @Test
    public void not_everything_combines() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "east");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "combine", "chicken", "stick");

        helper.runCommand("kirito", "move", "west");
        helper.assertResult("Closet\n" +
                "Large food store\n" +
                "There is the onion food.\n" +
                "Exits: south, west.\n" +
                "Player has the stick weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: yakitori(food).");
        helper.runCommand("kirito", "get");
        helper.assertResult("Closet\n" +
                "Large food store\n" +
                "Exits: south, west.\n" +
                "Player has the rock weapon.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.\n" +
                "You have: yakitori(food), onion(food).");

        helper.runCommand("kirito", "combine", "yakitori", "onion")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("no-combine")));
    }

    @Test
    public void you_must_have_the_ingredients() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "east");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "move", "north");
        helper.runCommand("kirito", "get");
        helper.runCommand("kirito", "combine", "chicken", "stick");

        helper.runCommand("kirito", "move", "west");
        helper.runCommand("kirito", "get");

        helper.runCommand("kirito", "combine", "chicken", "stick")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("no-item")));
    }
}
