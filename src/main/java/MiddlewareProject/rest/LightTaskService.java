package MiddlewareProject.rest;


import MiddlewareProject.handler.TaskHandler;
import MiddlewareProject.task.LightTask;
import MiddlewareProject.task.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping(path = "light")
public class LightTaskService {

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<String> solveLightTask(@RequestBody LightTask lightTask,HttpServletRequest request) {

        //System.out.println(request.getRemoteAddr());
        TaskHandler taskHandler = TaskHandler.getInstance();
        taskHandler.addLightTask(lightTask);
        String res = taskHandler.sendLightTask(lightTask);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
