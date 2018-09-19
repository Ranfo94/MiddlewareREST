package MiddlewareProject.rest;

import MiddlewareProject.task.HeavyTask;
import MiddlewareProject.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "heavy")
public class HeavyTaskService {

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<String> solveHeavyTask(@RequestBody HeavyTask heavyTask) {

        System.out.println("id : "+heavyTask.getID()+" type : "+heavyTask.getType());

        System.out.println("Task n° "+heavyTask.getID() +" Received");

        return new ResponseEntity<>("ACK", HttpStatus.OK);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String hello() {

        return "HELLO WORLD!";
    }
}
