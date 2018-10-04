package MiddlewareProject.rest;

import MiddlewareProject.handler.TaskHandler;
import MiddlewareProject.entities.MediumTask;
import MiddlewareProject.entities.MiddlewareTask;
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
@RequestMapping(path = "mediumCloud")
public class MediumTaskCloudService {

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<MediumTask> solveMediumCloudTask(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("Sending medium task to Cloud "+id);
        MiddlewareTask middlewareTask = TaskHandler.getInstance().searchTaskByID(id);

        if (middlewareTask == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MiddlewareTask res = TaskHandler.getInstance().sendMediumTask(middlewareTask);

        return new ResponseEntity<>((MediumTask) res.getTask(), HttpStatus.OK);
    }
}