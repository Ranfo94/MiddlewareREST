package MiddlewareProject.rest;

import MiddlewareProject.task.HeavyTask;
import MiddlewareProject.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "heavy")
public class HeavyTaskService {

    private ArrayList<HeavyTask> heavyList = new ArrayList<>();

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<String> solveHeavyTask(@RequestBody HeavyTask heavyTask) {

        heavyList.add(heavyTask);
        System.out.println("Task n° "+heavyTask.getID() +" Received");
        System.out.println(heavyList);
        return new ResponseEntity<>("ACK", HttpStatus.OK);
    }

    public ArrayList<HeavyTask> getLightList() {
        return heavyList;
    }

    public void updateLightTask(HeavyTask task){
        int index = heavyList.indexOf(task);
        heavyList.set(index,task);
    }
}
