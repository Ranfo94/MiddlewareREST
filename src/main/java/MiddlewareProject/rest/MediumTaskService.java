package MiddlewareProject.rest;

import MiddlewareProject.handler.TaskHandler;
import MiddlewareProject.task.MediumTask;
import MiddlewareProject.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping(path = "medium")
public class MediumTaskService {

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<String> solveMediumTask(@RequestBody MediumTask mediumTask,HttpServletRequest request) {

        //System.out.println(request.getRemoteAddr());
        TaskHandler taskHandler = TaskHandler.getInstance();
        taskHandler.addMediumTask(mediumTask);
        String res = taskHandler.sendMediumTask(mediumTask);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}
