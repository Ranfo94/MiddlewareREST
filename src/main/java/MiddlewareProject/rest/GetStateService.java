package MiddlewareProject.rest;

import MiddlewareProject.entities.MediumTaskState;
import MiddlewareProject.handler.TaskHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping(path="mediumTaskState")
public class GetStateService {
    private ArrayList<MediumTaskState> fogTaskStateList = new ArrayList<>();


    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<MediumTaskState> MediumTaskState(@RequestBody MediumTaskState mediumTaskState) throws IOException {

        //System.out.println("State: " + mediumTaskState.getState() + ", Time: " + mediumTaskState.getCurrentTime());

        fogTaskStateList = TaskHandler.getInstance().getFogTaskStateList();
        Boolean thereIsTaskState = false;

        for (MediumTaskState taskState: fogTaskStateList) {
            if (Objects.equals(taskState.getTaskId(), mediumTaskState.getTaskId())) {
                thereIsTaskState = true;
                taskState.setTaskId(mediumTaskState.getTaskId());
                taskState.setCurrentTime(mediumTaskState.getCurrentTime());
                taskState.setState(mediumTaskState.getState());
            }
        }
        if (!thereIsTaskState)
            fogTaskStateList.add(mediumTaskState);

        return new ResponseEntity<>(mediumTaskState, HttpStatus.OK);
    }
}
