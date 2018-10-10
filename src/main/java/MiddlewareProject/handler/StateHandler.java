package MiddlewareProject.handler;

import MiddlewareProject.entities.HeavyTaskState;
import MiddlewareProject.entities.LightTaskState;
import MiddlewareProject.entities.MediumTaskState;
import MiddlewareProject.task.HeavyTask;
import MiddlewareProject.task.LightTask;
import MiddlewareProject.task.MediumTask;
import MiddlewareProject.task.MiddlewareTask;

import java.util.ArrayList;

public class StateHandler {

    ArrayList<LightTaskState> lightTaskStateList = TaskHandler.getInstance().getLightTaskStateList();
    ArrayList<MediumTaskState> mediumTaskStateList = TaskHandler.getInstance().getMediumTaskStateList();
    ArrayList<HeavyTaskState> heavyTaskStateList = TaskHandler.getInstance().getHeavyTaskStateList();

    public void setTaskState(Integer taskId, MiddlewareTask middlewareTask) {
        if(middlewareTask.getTask().getType().equals("LIGHT")){
            LightTask lightTask = (LightTask) middlewareTask.getTask();
            for (LightTaskState taskState : lightTaskStateList) {
                if(taskState.getTaskId().equals(taskId)) {
                    //aggiorno lo stato nel task con le info contenute nel lightTaskState
                    lightTask.setLoopCount(taskState.getLoopCount());
                    lightTask.setEncrypted(taskState.getEncrypted());
                }
            }
        }
        if(middlewareTask.getTask().getType().equals("MEDIUM")){
            MediumTask mediumTask = (MediumTask) middlewareTask.getTask();
            for (MediumTaskState taskState : mediumTaskStateList) {
                if(taskState.getTaskId().equals(taskId)) {
                    //aggiorno lo stato nel task con le info contenute nel mediumTaskState
                    mediumTask.setState(taskState.getState());
                    mediumTask.setTime(taskState.getCurrentTime());
                }
            }
        }
        if(middlewareTask.getTask().getType().equals("HEAVY")){
            /*
            HeavyTask heavyTask = (HeavyTask) middlewareTask.getTask();
            for (HeavyTaskState taskState : heavyTaskStateList) {
                if(taskState.getTaskId().equals(taskId)) {
                    //aggiorno lo stato nel task con le info contenute nell'heavyTaskState

                }
            }
            */
        }
    }
}
