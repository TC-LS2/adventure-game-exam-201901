package com.drpicox.game;

import com.drpicox.game.bags.BagRepository;
import com.drpicox.game.combinations.CombinationRepository;
import com.drpicox.game.combinations.PlayerLevelRepository;
import com.drpicox.game.command.CommandRequest;
import com.drpicox.game.items.ItemRepository;
import com.drpicox.game.mocks.TimerTaskRunnerMock;
import com.drpicox.game.monsters.MonsterRepository;
import com.drpicox.game.players.PlayerRepository;
import com.drpicox.game.rooms.RoomRepository;
import com.drpicox.game.tools.GameResultStringifier;
import com.drpicox.game.tools.WorldBuilder;
import com.drpicox.game.world.TextWorldFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class TestHelper {

    /* change it to true temporaly to generate snapshots */
    private boolean suspendAssert = false;

    /* change it in @Before of tests that require a different snapshot shape (add a set) */
    private GameResultStringifier gameResultStringifier;


    public final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private final ObjectMapper mapper = new ObjectMapper();
    private ResultActions lastResultActions = null;
    private List<String> commandsStrings = new ArrayList<>();

    private MockMvc mockMvc;
    private ItemRepository itemRepository;
    private MonsterRepository monsterRepository;
    private RoomRepository roomRepository;
    private BagRepository bagRepository;
    private PlayerRepository playerRepository;
    private CombinationRepository combinationRepository;
    private PlayerLevelRepository playerLevelRepository;
    private TimerTaskRunnerMock timerTaskRunnerMock;

    public TestHelper(MockMvc mockMvc, ItemRepository itemRepository, MonsterRepository monsterRepository, RoomRepository roomRepository, PlayerRepository playerRepository, BagRepository bagRepository, TimerTaskRunnerMock timerTaskRunnerMock, CombinationRepository combinationRepository, PlayerLevelRepository playerLevelRepository) {
        this.mockMvc = mockMvc;
        this.itemRepository = itemRepository;
        this.monsterRepository = monsterRepository;
        this.roomRepository = roomRepository;
        this.playerRepository = playerRepository;
        this.bagRepository = bagRepository;
        this.timerTaskRunnerMock = timerTaskRunnerMock;
        this.combinationRepository = combinationRepository;
        this.playerLevelRepository = playerLevelRepository;
    }

    public void cleanup() {
        if (suspendAssert)
            System.out.println("! alerts suspended !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        else
            System.out.println("> █\n= cleanup =====================================");

        playerLevelRepository.deleteAll();
        combinationRepository.deleteAll();
        bagRepository.deleteAll();
        playerRepository.deleteAll();
        roomRepository.deleteAll();
        monsterRepository.deleteAll();
        itemRepository.deleteAll();
        commandsStrings.clear();
        timerTaskRunnerMock.clear();

        gameResultStringifier = new GameResultStringifier();
    }

    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object fromJson(String json) {
        return fromJson(json, Object.class);
    }

    public Object eraseType(Object object) {
        return fromJson(toJson(object));
    }

    public ResultActions postCommands(String username, String... commands) throws Exception {
        ResultActions result = null;
        for (var command : commands) {
            switch (command) {
                case "north":
                case "south":
                case "east":
                case "west":
                    command = "move?direction=" + command;
            }

            result = mockMvc.perform(post("/api/v1/players/" + username + "/commands/" + command));
        }
        return result;
    }


    public void assertResult(String expectedResult) throws Exception {
        var commandsString = getCommandsString();
        var resultJson = lastResultActions
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var resultGame = fromJson(resultJson, Map.class);
        var resultString = gameToString(resultGame);

        if (!resultString.equals(expectedResult))
            System.out.println("vvvv xxxxxxxxxxxxxxxxx");
        System.out.println(resultString);
        if (!resultString.equals(expectedResult))
            System.out.println("^^^^ xxxxxxxxxxxxxxxxx");

        if (!suspendAssert)
            assertThat(commandsString + resultString, is(commandsString + expectedResult));
    }

    private String getCommandsString() {
        return String.join("\n", commandsStrings.stream().toArray(String[]::new)) + "\n";
    }

    private String gameToString(Map game) {
        return gameResultStringifier.toString(game);
    }

    public void setGameResultStringifier(GameResultStringifier gameResultStringifier) {
        this.gameResultStringifier = gameResultStringifier;
    }

    public ResultActions runCommand(String username, String command, String... arguments) throws Exception {
        var commandString = "> " + command + " " + String.join(" ", arguments);
        commandsStrings.add(commandString);
        System.out.println(commandString);

        var request = new CommandRequest(username, command, Arrays.asList(arguments));

        lastResultActions = mockMvc.perform(post("/api/v1/commands")
                .contentType(contentType)
                .content(toJson(request)));

        if (lastResultActions.andReturn().getResponse().getStatus() >= 300) {
            var resultJson = lastResultActions.andReturn().getResponse().getContentAsString();
            var resultGame = fromJson(resultJson, Map.class);
            System.out.print("Ops, ");
            System.out.println(resultGame.get("message").toString().toLowerCase());
        }


        return lastResultActions;
    }

    public ResultActions putWorld(WorldBuilder builder) throws Exception {
        var text = builder.build();

        return putWorld(text.split("\n"));
    }

    public ResultActions putWorld(String... lines) throws Exception {
        var world = new TextWorldFactory().create(lines);

        return mockMvc.perform(put("/api/v1/world")
                .contentType(contentType)
                .content(toJson(world)))
                .andExpect(status().isOk());
    }

    public boolean isSuspendAssert() {
        return suspendAssert;
    }
}
