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

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WhoIsThereTests {

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
        RoomRefreshTests.decorateMustache(mustache);

        mustache.setBody("" +
                "{{#room}}{{>room}}{{/room}}" +
                "{{#player}}{{>player}}{{/player}}" +
                "{{#roomPlayers}}{{>roomPlayers}}{{/roomPlayers}}");

        mustache.setTemplate("roomPlayers", "" +
                "{{#-first}}There is: {{/-first}}" +
                "{{^-first}}, {{/-first}}" +
                "{{>roomPlayer}}" +
                "{{#-last}}.\n{{/-last}}");

        mustache.setTemplate("roomPlayer", "{{username}}");
    }

    public static WorldBuilder buildWorld() {
        return new WorldBuilder()
                .name("Meeting point")
                .description("This is a large room. Probably the largest room in the world. Everyone, sooner or later, comes here. Sometimes is calm and quiet. Sometimes is crowd and full of people.")
                ;
    }

    @Test
    public void world_text_snapshot() {
        assertThat(buildWorld().build(), is("" +
                "== rooms:\n" +
                "0 0:Meeting point:-1 -1 -1 -1:nothing\n" +
                "This is a large room. Probably the largest room in the world. Everyone, sooner or later, comes here. Sometimes is calm and quiet. Sometimes is crowd and full of people.\n" +
                "::::\n"));
    }

    @Test
    public void world_map_snapshot() {
        assertThat(buildWorld().map(), is("" +
                "+-------------+      \n" +
                "|MEETING POINT|      \n" +
                "|this is a ...|      \n" +
                "|             |      \n" +
                "+-------------+      \n"));
    }

    @Test
    public void everything_is_calm_when_you_are_alone() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("kirito", "look");
        helper.assertResult("" +
                "Meeting point\n" +
                "This is a large room. Probably the largest room\n" +
                "in the world. Everyone, sooner or later, comes\n" +
                "here. Sometimes is calm and quiet. Sometimes is\n" +
                "crowd and full of people.\n" +
                "Player has 16 life points.\n" +
                "There is: kirito.");
    }


    @Test
    public void but_it_can_get_full_of_friends() throws Exception {
        helper.putWorld(buildWorld());

        helper.runCommand("asuna", "look");
        helper.runCommand("leafa", "look");
        helper.runCommand("sinon", "look");
        helper.runCommand("kirito", "look");
        helper.assertResult("" +
                "Meeting point\n" +
                "This is a large room. Probably the largest room\n" +
                "in the world. Everyone, sooner or later, comes\n" +
                "here. Sometimes is calm and quiet. Sometimes is\n" +
                "crowd and full of people.\n" +
                "Player has 16 life points.\n" +
                "There is: asuna, leafa, sinon, kirito.");
    }
}
