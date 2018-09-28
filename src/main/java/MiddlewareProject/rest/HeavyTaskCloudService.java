package MiddlewareProject.rest;

import MiddlewareProject.handler.TaskHandler;
import MiddlewareProject.task.HeavyTask;
import MiddlewareProject.task.MiddlewareTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@RestController
@RequestMapping(path = "heavyCloud")
public class HeavyTaskCloudService {

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<HeavyTask> solveHeavyCloudTask(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("Sending heavy task to Cloud "+id);
        MiddlewareTask middlewareTask = TaskHandler.getInstance().searchTaskByID(id);

        if (middlewareTask == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MiddlewareTask res = TaskHandler.getInstance().sendHeavyTask(middlewareTask);

        return new ResponseEntity<>((HeavyTask) res.getTask(), HttpStatus.OK);
    }
}