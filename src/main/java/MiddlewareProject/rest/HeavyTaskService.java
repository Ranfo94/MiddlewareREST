package MiddlewareProject.rest;

import MiddlewareProject.handler.TaskHandler;
import MiddlewareProject.entities.HeavyTask;
import MiddlewareProject.entities.MiddlewareTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping(path = "heavy")
public class HeavyTaskService {

    /**
     * REST method: device asks for its task to be resolved
     */
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<HeavyTask> solveHeavyTask(@PathVariable int id) throws IOException {

        System.out.println("Mando l'Heavy Task "+id);

        MiddlewareTask middlewareTask = TaskHandler.getInstance().searchTaskByID(id);
        if (middlewareTask == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MiddlewareTask res = TaskHandler.getInstance().sendHeavyTask(middlewareTask);
        TaskHandler.getInstance().updateTask(res);
        HeavyTask task = (HeavyTask) res.getTask();
        if(task.getLast() == -2){
            System.out.println("task completato, lo elimino dal sistema");
            //task completato senza interruzione, posso eliminarlo
            TaskHandler.getInstance().removeTask(res);
        }else{
            System.out.println("task interrotto, non lo elimino");
        }
        return new ResponseEntity<>((HeavyTask) res.getTask(), HttpStatus.OK);
    }

    /**
     * REST method: device registers its task
     */
    @RequestMapping(path = "register", method = RequestMethod.POST)
    public ResponseEntity<Integer> registerHeavyTask(@RequestBody HeavyTask heavyTask) throws IOException {

        //Invia ACK al client
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Task da registrare : "+heavyTask.getID()+ " response: "+heavyTask.getResponse());

        TaskHandler taskHandler = TaskHandler.getInstance();
        MiddlewareTask middlewareTask = taskHandler.addHeavyTask(heavyTask);

        return new ResponseEntity<>( middlewareTask.getMiddlewareID(), HttpStatus.OK);
    }
}
