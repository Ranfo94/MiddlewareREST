package MiddlewareProject.handler;

import MiddlewareProject.task.MiddlewareTask;
import MiddlewareProject.task.Type;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


public class ToBePerformedTaskHandler {

    private TaskHandler taskHandler = TaskHandler.getInstance();

    public void executeToBePerformedTasks() {
        /*while (true) {
            try {
                Thread.sleep(5000);
                ArrayList<MiddlewareTask> tasks = taskHandler.getToBePerformedTasks();

                for (MiddlewareTask toBePerformedTask : tasks) {
                    Type type = toBePerformedTask.getTask().getType();
                    taskHandler.getToBePerformedTasks().remove(toBePerformedTask);

                    if (type.equals(Type.LIGHT))
                        taskHandler.sendLightTask(toBePerformedTask);
                    else if (type.equals(Type.MEDIUM))
                        taskHandler.sendMediumTask(toBePerformedTask);
                    else if (type.equals(Type.HEAVY))
                        taskHandler.sendHeavyTask(toBePerformedTask);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }
}