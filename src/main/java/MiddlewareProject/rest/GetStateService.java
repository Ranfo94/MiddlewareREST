package MiddlewareProject.rest;

import MiddlewareProject.entities.MediumTaskState;
import MiddlewareProject.handler.TaskHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping(path="state")
public class GetStateService {
    private ArrayList<MediumTaskState> fogTaskStateList = new ArrayList<>();


    @RequestMapping(path = "{mediumTask}", method = RequestMethod.POST)
    public ResponseEntity<MediumTaskState> MediumTaskState(@PathVariable MediumTaskState mediumTaskState, HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("mediumTaskState" + mediumTaskState);

        fogTaskStateList = TaskHandler.getInstance().getFogTaskStateList();
        Boolean thereIsTaskState = false;

        for (MediumTaskState taskState: fogTaskStateList) {
            if (Objects.equals(taskState.getTaskId(), mediumTaskState.getTaskId())) {
                thereIsTaskState = true;
                taskState.setCurrentTime(mediumTaskState.getCurrentTime());
                taskState.setState(mediumTaskState.getState());
            }
        }
        if (!thereIsTaskState)
            fogTaskStateList.add(mediumTaskState);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
