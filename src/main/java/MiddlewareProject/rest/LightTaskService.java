package MiddlewareProject.rest;


import MiddlewareProject.task.LightTask;
import MiddlewareProject.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "light")
public class LightTaskService {

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<String> solveLightTask(@RequestBody LightTask lightTask) {

        System.out.println(lightTask);
        //solve light task
        System.out.println("id : "+lightTask.getID()+" type : "+lightTask.getType()+ " string : "+lightTask.getToEncrypt());
        System.out.println("Task n° "+lightTask.getID() +" Received");

        return new ResponseEntity<>("ACK", HttpStatus.OK);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String hello() {

        return "HELLO WORLD!";
    }

}
