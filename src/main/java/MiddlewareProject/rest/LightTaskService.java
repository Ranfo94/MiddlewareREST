package MiddlewareProject.rest;

import MiddlewareProject.handler.TaskHandler;
import MiddlewareProject.entities.LightTask;
import MiddlewareProject.entities.MiddlewareTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping(path = "light")
public class LightTaskService {

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<LightTask> solveLightTask(@PathVariable int id) throws IOException {

        System.out.println("Mando il Light Task al nodo fog "+id);

        MiddlewareTask middlewareTask = TaskHandler.getInstance().searchTaskByID(id);

        if (middlewareTask == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MiddlewareTask res = TaskHandler.getInstance().sendLightTask(middlewareTask);
        TaskHandler.getInstance().updateTask(res);

        LightTask task = (LightTask) res.getTask();
        if(task.getLoopCount() == -2){
            //task completato senza interruzione, posso eliminarlo
            TaskHandler.getInstance().removeTask(res);
        }

        return new ResponseEntity<>((LightTask)res.getTask(), HttpStatus.OK);
    }

    @RequestMapping(path = "register", method = RequestMethod.POST)
    public ResponseEntity<Integer> registerLightTask(@RequestBody LightTask lightTask) throws IOException {

        //Invia ACK al client
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Task da registrare : "+lightTask.getID()+ " string: "+lightTask.getToEncrypt());

        TaskHandler taskHandler = TaskHandler.getInstance();
        MiddlewareTask middlewareTask = taskHandler.addLightTask(lightTask);

        return new ResponseEntity<>( middlewareTask.getMiddlewareID(), HttpStatus.OK);
    }
}
