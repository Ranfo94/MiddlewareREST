package MiddlewareProject.rest;

import MiddlewareProject.handler.TaskHandler;
import MiddlewareProject.task.HeavyTask;
import MiddlewareProject.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "heavy")
public class HeavyTaskService {

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<String> solveHeavyTask(@RequestBody HeavyTask heavyTask) {

        TaskHandler.getInstance().addHeavyTask(heavyTask);
        return new ResponseEntity<>("ACK", HttpStatus.OK);
    }

}
