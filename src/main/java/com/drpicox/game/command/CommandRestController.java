package com.drpicox.game.command;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/commands")
public class CommandRestController {

    private CommandCollector commandCollector;

    public CommandRestController(CommandCollector commandCollector) {
        this.commandCollector = commandCollector;
    }

    @PostMapping()
    public CommandResponse run(@RequestBody CommandRequest request) {
        var response = new CommandResponse();
        commandCollector.run(request, response);
        return response;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalCommandException.class})
    public Map handleException(IllegalCommandException e) {
        return new HashMap() {{
            put("code", e.getCode());
            put("message", e.getMessage());
        }};
    }

}
