package MiddlewareProject.rest;

import MiddlewareProject.entities.HeavyTask;
import MiddlewareProject.entities.LightTask;
import MiddlewareProject.entities.MediumTask;
import MiddlewareProject.entities.MiddlewareTask;
import MiddlewareProject.handler.InterruptionHandler;
import MiddlewareProject.handler.TaskHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping(path = "interruption")
public class InterruptionService {

    /**
     * this function identifies the FogNode that is currently processing the task
     * and sends the interruption request.
     * @param id : identifies the task that needs to be interrupted
     * @return ACK/NACK
     */
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<String> interruptTask(@PathVariable int id) throws IOException {

        //identify the FogNode that's currently processing the task
        String nodePort = InterruptionHandler.getInstance().getPortByTask(id);
        //interruption request
        String res = TaskHandler.getInstance().sendInterruptionRequest(nodePort,id);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    /**
     * this function retrieves the previously interrupted light task and delegates its processing to a FogNode.
     * @param id : identifies the task that needs to be resumed
     */
    @RequestMapping("resumelight/{id}")
    public ResponseEntity<LightTask> resumeLightTask(@PathVariable int id) throws IOException {

        System.out.println("richiesta di ripresa task "+id+"\n");

        //retrieving the task
        ArrayList<MiddlewareTask> taskList = TaskHandler.getInstance().getTaskList();
        MiddlewareTask resumeTask = null;
        for (int i = 0; i < taskList.size(); i++) {
            if(taskList.get(i).getMiddlewareID() == id){
                resumeTask = taskList.get(i);
            }
        }
        //task request sent to fognode
        MiddlewareTask res = TaskHandler.getInstance().sendLightTask(resumeTask);
        return new ResponseEntity<>((LightTask) res.getTask(), HttpStatus.OK);
    }

    /**
     * this function retrieves the previously interrupted medium task and delegates its processing to a FogNode.
     * @param id : identifies the task that needs to be resumed
     */
    @RequestMapping("resumemedium/{id}")
    public ResponseEntity<MediumTask> resumeMediumTask(@PathVariable int id, HttpServletRequest request) throws IOException {

        System.out.println("richiesta di ripresa task "+id+"\n");
        //retrieving the task
        ArrayList<MiddlewareTask> taskList = TaskHandler.getInstance().getTaskList();
        MiddlewareTask resumeTask = null;
        for (MiddlewareTask middlewareTask : taskList){
            if (middlewareTask.getMiddlewareID() == id){
                resumeTask = middlewareTask;
            }
        }
        MiddlewareTask res = TaskHandler.getInstance().sendMediumTask(resumeTask);
        return new ResponseEntity<>((MediumTask) res.getTask(), HttpStatus.OK);
    }

    /**
     * this function retrieves the previously interrupted heavy task and delegates its processing to a FogNode.
     * @param id : identifies the task that needs to be resumed
     */
    @RequestMapping("resumeheavy/{id}")
    public ResponseEntity<HeavyTask> resumeHeavyTask(@PathVariable int id, HttpServletRequest request){

        System.out.println("richiesta di ripresa task "+id+"\n");
        //retrieving the task
        ArrayList<MiddlewareTask> taskList = TaskHandler.getInstance().getTaskList();
        MiddlewareTask resumeTask = null;
        for (MiddlewareTask middlewareTask : taskList){
            if (middlewareTask.getMiddlewareID() == id){
               resumeTask = middlewareTask;
            }
        }

        /*
        HeavyTask task = (HeavyTask) resumeTask.getTask();
        System.out.println("state -> partial: "+ task.getPartial() + ", last: "+task.getLast()+"\n");
        */

        MiddlewareTask res = TaskHandler.getInstance().sendHeavyTask(resumeTask);
        return new ResponseEntity<>((HeavyTask) res.getTask(), HttpStatus.OK);

    }


}