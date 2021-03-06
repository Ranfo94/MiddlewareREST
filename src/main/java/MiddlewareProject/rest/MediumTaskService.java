package MiddlewareProject.rest;

import MiddlewareProject.entities.MediumTask;
import MiddlewareProject.handler.TaskHandler;
import MiddlewareProject.entities.MiddlewareTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping(path = "medium")
public class MediumTaskService {

    /**
     * REST method: device asks for its task to be resolved
     */
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<MediumTask> solveMediumTask(@PathVariable int id) throws IOException {

        System.out.println("Mando il Medium Task "+id);
        MiddlewareTask middlewareTask = TaskHandler.getInstance().searchTaskByID(id);

        if (middlewareTask == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MiddlewareTask res = TaskHandler.getInstance().sendMediumTask(middlewareTask);
        TaskHandler.getInstance().updateTask(res);

        MediumTask task = (MediumTask) res.getTask();
        if(task.getState() == -2){
            //task completato senza interruzione, posso eliminarlo
            TaskHandler.getInstance().removeTask(res);
        }
        return new ResponseEntity<>((MediumTask) res.getTask(), HttpStatus.OK);
    }
    /**
     * REST method: device registers its task
     */
    @RequestMapping(path = "register", method = RequestMethod.POST)
    public ResponseEntity<Integer> registerMediumTask(@RequestBody MediumTask mediumTask) throws IOException {

        //Invia ACK al client
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Task da registrare : "+mediumTask.getID()+ " number: "+mediumTask.getNumber());

        TaskHandler taskHandler = TaskHandler.getInstance();
        MiddlewareTask middlewareTask = taskHandler.addMediumTask(mediumTask);

        return new ResponseEntity<>( middlewareTask.getMiddlewareID(), HttpStatus.OK);
    }
}
