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
                .name("Empty room")
                .description("It is an empty room.")
                .item("chiken", "food", 1)
                .item("onion", "food", 1)
                .item("stick", "weapon", 1)
                .item("yakitori", "food", 4)
                .item("negima", "food", 5)
                .line("combinations", "yakitori: chicken,stick")
                .line("combinations", "negima: chicken,onion,stick")
                ;
    }

    @Test
    public void world_text_snapshot() {
        assertThat(buildWorld().build(), is("" +
                "== rooms:\n" +
                "0 0:Empty room:-1 -1 -1 -1:negima\n" +
                "It is an empty room.\n" +
                "::::\n" +
                "== items:\n" +
                "chiken: food 1\n" +
                "onion: food 1\n" +
                "stick: weapon 1\n" +
                "yakitori: food 4\n" +
                "negima: food 5\n" +
                "== combinations:\n" +
                "yakitori: chicken,stick\n" +
                "negima: chicken,onion,stick\n"));
    }

    @Test
    public void world_map_snapshot() {
        assertThat(buildWorld().map(), is("" +
                "+----------+      \n" +
                "|EMPTY ROOM|      \n" +
                "|it is a...|      \n" +
                "|  (negima)|      \n" +
                "+----------+      \n"));
    }

    @Test
    public void solveme() throws Exception {
        helper.putWorld(buildWorld());

        // SOLVEME
    }
}
