package MiddlewareProject.rest;

import MiddlewareProject.handler.TaskHandler;
import MiddlewareProject.entities.LightTask;
import MiddlewareProject.entities.MiddlewareTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping(path = "light")
public class LightTaskService {

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<LightTask> solveLightTask(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("Sending light task "+id);
        MiddlewareTask middlewareTask = TaskHandler.getInstance().searchTaskByID(id);

        if (middlewareTask == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MiddlewareTask res = TaskHandler.getInstance().sendLightTask(middlewareTask);
        //TODO togliere il nodo dalla taskList??
        TaskHandler.getInstance().getTaskList().remove(middlewareTask);
        return new ResponseEntity<>((LightTask) res.getTask(), HttpStatus.OK);
    }

    @RequestMapping(path = "register", method = RequestMethod.POST)
    public ResponseEntity<Integer> registerLightTask(@RequestBody LightTask lightTask) throws IOException {

        //System.out.println(request.getRemoteAddr());

        //Invia ACK al client
        //responseWriter.sendResponse("Processing Task...",response);
        System.out.println("Task da registrare : "+lightTask.getID()+ " string: "+lightTask.getToEncrypt());

        TaskHandler taskHandler = TaskHandler.getInstance();
        MiddlewareTask middlewareTask = taskHandler.addLightTask(lightTask);
        //MiddlewareTask res = taskHandler.sendLightTask(middlewareTask);

        return new ResponseEntity<>( middlewareTask.getMiddlewareID(), HttpStatus.OK);
    }

}
