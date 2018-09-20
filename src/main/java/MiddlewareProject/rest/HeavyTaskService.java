package MiddlewareProject.rest;

import MiddlewareProject.handler.TaskHandler;
import MiddlewareProject.task.HeavyTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "heavy")
public class HeavyTaskService {

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<String> solveHeavyTask(@RequestBody HeavyTask heavyTask,HttpServletRequest request) {

        //System.out.println(request.getRemoteAddr());
        TaskHandler taskHandler = TaskHandler.getInstance();
        taskHandler.addHeavyTask(heavyTask);
        String res = taskHandler.sendHeavyTask(heavyTask);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
