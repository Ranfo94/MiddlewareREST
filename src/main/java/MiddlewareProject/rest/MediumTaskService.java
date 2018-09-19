package MiddlewareProject.rest;

import MiddlewareProject.task.MediumTask;
import MiddlewareProject.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "medium")
public class MediumTaskService {

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<String> solveMediumTask(@RequestBody MediumTask mediumTask) {

        System.out.println("id : "+mediumTask.getID()+" type : "+mediumTask.getType());
        System.out.println("Task n° "+mediumTask.getID() +" Received");

        return new ResponseEntity<>("ACK", HttpStatus.OK);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String hello() {

        return "HELLO WORLD!";
    }

}
