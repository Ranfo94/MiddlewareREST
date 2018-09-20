package MiddlewareProject.rest;

import MiddlewareProject.handler.TaskHandler;
import MiddlewareProject.task.MediumTask;
import MiddlewareProject.task.MiddlewareTask;
import MiddlewareProject.task.Task;
import MiddlewareProject.utils.ResponseWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping(path = "medium")
public class MediumTaskService {

    ResponseWriter responseWriter = new ResponseWriter();

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<MediumTask> solveMediumTask(@RequestBody MediumTask mediumTask,HttpServletRequest request, HttpServletResponse response) throws IOException {

        //System.out.println(request.getRemoteAddr());

        //Invia ACK al client
        //responseWriter.sendResponse("Processing Task...",response);
        System.out.println("Task Received - MIDDLEWARE");

        TaskHandler taskHandler = TaskHandler.getInstance();
        MiddlewareTask middlewareTask = taskHandler.addMediumTask(mediumTask);
        MiddlewareTask res = taskHandler.sendMediumTask(middlewareTask);

        return new ResponseEntity<>((MediumTask) res.getTask(), HttpStatus.OK);
    }


}
