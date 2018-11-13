package MiddlewareProject.rest;

import MiddlewareProject.handler.TaskHandler;
import MiddlewareProject.entities.LightTask;
import MiddlewareProject.entities.MiddlewareTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "lightCloud")
public class LightTaskCloudService{

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<LightTask> solveLightCloudTask(@PathVariable int id) throws IOException {

        System.out.println("Mando il Light Task al Cloud "+id);
        MiddlewareTask middlewareTask = TaskHandler.getInstance().searchTaskByID(id);

        if (middlewareTask == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MiddlewareTask res = TaskHandler.getInstance().sendLightTask(middlewareTask);

        return new ResponseEntity<>((LightTask) res.getTask(), HttpStatus.OK);
    }
}