package MiddlewareProject.rest;

import MiddlewareProject.handler.TaskHandler;
import MiddlewareProject.task.HeavyTask;
import MiddlewareProject.task.MiddlewareTask;
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

@RestController
@RequestMapping(path = "heavy")
public class HeavyTaskService {

    ResponseWriter responseWriter = new ResponseWriter();

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<HeavyTask> solveHeavyTask(@RequestBody HeavyTask heavyTask,HttpServletRequest request, HttpServletResponse response) throws IOException {

        //System.out.println(request.getRemoteAddr());

        //Invia ACK al client
        //responseWriter.sendResponse("Processing Task...",response);
        System.out.println("Task Received - MIDDLEWARE");

        TaskHandler taskHandler = TaskHandler.getInstance();
        MiddlewareTask middlewareTask = taskHandler.addHeavyTask(heavyTask);
        MiddlewareTask res = taskHandler.sendHeavyTask(middlewareTask);

        return new ResponseEntity<>((HeavyTask) res.getTask(), HttpStatus.OK);
    }

}
