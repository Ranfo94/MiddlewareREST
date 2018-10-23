package MiddlewareProject.rest;

import MiddlewareProject.entities.*;
import MiddlewareProject.handler.TaskHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping(path="state")
public class GetStateService {

    /**
     * Questo metodo aggiorna un light task con lo stato ricevuto dal fogNode.
     * @param lightTaskState is the object with all the info about the state of the light task
     * @return a light task state
     */
    @RequestMapping(path = "light/{midd_id}", method = RequestMethod.POST)
    public ResponseEntity<LightTaskState> lightTaskState(@PathVariable int midd_id, @RequestBody LightTaskState lightTaskState) throws IOException {

        ArrayList<MiddlewareTask> taskList = TaskHandler.getInstance().getTaskList();

        for (MiddlewareTask middlewareTask : taskList){
            if (middlewareTask.getMiddlewareID() == midd_id){
                LightTask task = (LightTask) middlewareTask.getTask();
                task.setLoopCount(lightTaskState.getLoopCount());
                task.setEncrypted(lightTaskState.getEncrypted());
            }
        }

        return new ResponseEntity<>(lightTaskState, HttpStatus.OK);
    }

    /**
     * Questo metodo aggiorna un medium task con lo stato ricevuto dal fogNode.
     * @param mediumTaskState is the object with all the info about the state of the medium task
     * @return a medium task state
     */
    @RequestMapping(path = "medium/{midd_id}", method = RequestMethod.POST)
    public ResponseEntity<MediumTaskState> mediumTaskState(@PathVariable int midd_id, @RequestBody MediumTaskState mediumTaskState) throws IOException {

        ArrayList<MiddlewareTask> taskList = TaskHandler.getInstance().getTaskList();

        for (MiddlewareTask middlewareTask : taskList){
            if (middlewareTask.getMiddlewareID() == midd_id){
                //aggiorna il task con lo stato corrente
                MediumTask task = (MediumTask) middlewareTask.getTask();
                task.setCurrentTime(mediumTaskState.getCurrentTime());
                task.setState(mediumTaskState.getState());
            }
        }

        return new ResponseEntity<>(mediumTaskState, HttpStatus.OK);
    }


    /**
     * Questo metodo aggiorna un heavy task con lo stato ricevuto dal fogNode.
     * @param heavyTaskState is the object with all the info about the state of the heavy task
     * @return an heavy task state
     */
    //TODO rimuovi la risposta al fognode
    @RequestMapping(path = "heavy/{midd_id}", method = RequestMethod.POST)
    public ResponseEntity<HeavyTaskState> heavyTaskState(@PathVariable int midd_id, @RequestBody HeavyTaskState heavyTaskState) throws IOException {

        ArrayList<MiddlewareTask> taskList = TaskHandler.getInstance().getTaskList();

        for (MiddlewareTask middlewareTask : taskList){
            if (middlewareTask.getMiddlewareID() == midd_id){
                HeavyTask task = (HeavyTask) middlewareTask.getTask();
                task.setPartial(heavyTaskState.getPartial());
                task.setLast(heavyTaskState.getLast());
            }
        }

        return new ResponseEntity<>(heavyTaskState, HttpStatus.OK);
    }
}
