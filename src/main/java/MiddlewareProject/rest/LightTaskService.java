package MiddlewareProject.rest;


import MiddlewareProject.task.LightTask;
import MiddlewareProject.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "light")
public class LightTaskService {

    private ArrayList<LightTask> lightList = new ArrayList<>();

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<String> solveLightTask(@RequestBody LightTask lightTask) {

        lightList.add(lightTask);

        //solve light task
        System.out.println("Task n° "+lightTask.getID() +" Received");
        System.out.println(lightList);

        return new ResponseEntity<>("ACK", HttpStatus.OK);
    }

    public ArrayList<LightTask> getLightList() {
        return lightList;
    }

    public void updateLightTask(LightTask task){
        int index = lightList.indexOf(task);
        lightList.set(index,task);
    }

}
