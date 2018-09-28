package MiddlewareProject.rest;

import MiddlewareProject.entities.FogNode;
import MiddlewareProject.handler.TaskHandler;
import MiddlewareProject.task.MediumTask;
import MiddlewareProject.task.MiddlewareTask;
import MiddlewareProject.utils.ResponseWriter;
import MiddlewareProject.utils.UpdateCurrentResourcesFogNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(path = "medium")
public class MediumTaskService {

    ResponseWriter responseWriter = new ResponseWriter();

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<MediumTask> solveMediumTask(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("Sending task "+id);
        MiddlewareTask middlewareTask = TaskHandler.getInstance().searchTaskByID(id);

        if (middlewareTask == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MiddlewareTask res = TaskHandler.getInstance().sendMediumTask(middlewareTask);
        TaskHandler.getInstance().getTaskList().remove(middlewareTask);
        return new ResponseEntity<>((MediumTask) res.getTask(), HttpStatus.OK);
    }

    @RequestMapping(path = "register", method = RequestMethod.POST)
    public ResponseEntity<Integer> registerMediumTask(@RequestBody MediumTask mediumTask) throws IOException {

        //System.out.println(request.getRemoteAddr());

        //Invia ACK al client
        //responseWriter.sendResponse("Processing Task...",response);
        System.out.println("Task da registrare : "+mediumTask.getID()+ " number: "+mediumTask.getNumber());

        TaskHandler taskHandler = TaskHandler.getInstance();
        MiddlewareTask middlewareTask = taskHandler.addMediumTask(mediumTask);
        //MiddlewareTask res = taskHandler.sendLightTask(middlewareTask);

        return new ResponseEntity<>( middlewareTask.getMiddlewareID(), HttpStatus.OK);
    }
}
