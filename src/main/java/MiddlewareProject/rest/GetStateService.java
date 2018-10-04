package MiddlewareProject.rest;

import MiddlewareProject.entities.LightTaskState;
import MiddlewareProject.entities.MediumTaskState;
import MiddlewareProject.handler.TaskHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping(path="state")
public class GetStateService {

    @RequestMapping(path = "light", method = RequestMethod.POST)
    public ResponseEntity<LightTaskState> lightTaskState(@RequestBody LightTaskState lightTaskState) throws IOException {

        System.out.println("LoopCount: " + lightTaskState.getLoopCount() + ", Encrypted: " + lightTaskState.getEncrypted());

        ArrayList<LightTaskState> lightTaskStateList = TaskHandler.getInstance().getLightTaskStateList();
        Boolean thereIsLightTaskState = false;

        for (LightTaskState taskState : lightTaskStateList) {
            if (Objects.equals(lightTaskState.getTaskId(), taskState.getTaskId())) {
                thereIsLightTaskState = true;
                taskState.setTaskId(lightTaskState.getTaskId());
                taskState.setLoopCount(lightTaskState.getLoopCount());
                taskState.setEncrypted(lightTaskState.getEncrypted());
            }
        }
        if (!thereIsLightTaskState)
            lightTaskStateList.add(lightTaskState);

        return new ResponseEntity<>(lightTaskState, HttpStatus.OK);
    }

    @RequestMapping(path = "medium", method = RequestMethod.POST)
    public ResponseEntity<MediumTaskState> mediumTaskState(@RequestBody MediumTaskState mediumTaskState) throws IOException {

        //System.out.println("State: " + mediumTaskState.getState() + ", Time: " + mediumTaskState.getCurrentTime());

        ArrayList<MediumTaskState> mediumTaskStateList = TaskHandler.getInstance().getMediumTaskStateList();
        Boolean thereIsMediumTaskState = false;

        for (MediumTaskState taskState: mediumTaskStateList) {
            if (Objects.equals(taskState.getTaskId(), mediumTaskState.getTaskId())) {
                thereIsMediumTaskState = true;
                taskState.setTaskId(mediumTaskState.getTaskId());
                taskState.setCurrentTime(mediumTaskState.getCurrentTime());
                taskState.setState(mediumTaskState.getState());
            }
        }
        if (!thereIsMediumTaskState)
            mediumTaskStateList.add(mediumTaskState);

        return new ResponseEntity<>(mediumTaskState, HttpStatus.OK);
    }
}
