package MiddlewareProject.rest;

import MiddlewareProject.task.MediumTask;
import MiddlewareProject.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "medium")
public class MediumTaskService {

    private ArrayList<MediumTask> mediumList = new ArrayList<>();

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<String> solveMediumTask(@RequestBody MediumTask mediumTask) {

        mediumList.add(mediumTask);
        System.out.println("Task n° "+mediumTask.getID() +" Received");
        System.out.println(mediumList);
        return new ResponseEntity<>("ACK", HttpStatus.OK);
    }

    public ArrayList<MediumTask> getLightList() {
        return mediumList;
    }

    public void updateLightTask(MediumTask task){
        int index = mediumList.indexOf(task);
        mediumList.set(index,task);
    }

}
