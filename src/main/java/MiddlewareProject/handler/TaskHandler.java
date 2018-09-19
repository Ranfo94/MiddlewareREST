package MiddlewareProject.handler;

import MiddlewareProject.task.*;

import java.util.ArrayList;

public class TaskHandler {

    private ArrayList<MiddlewareTask> taskList = new ArrayList<>();

    private static TaskHandler ourInstance = new TaskHandler();

    public static TaskHandler getInstance() {
        return ourInstance;
    }

    private TaskHandler() {
    }

    public ArrayList<MiddlewareTask> getTaskList() {
        return taskList;
    }

    public void addLightTask(LightTask task){
        MiddlewareTask middlewareTask = new MiddlewareTask(task);
        taskList.add(middlewareTask);
        printList();
    }

    public void addMediumTask(MediumTask task){
        MiddlewareTask middlewareTask = new MiddlewareTask(task);
        taskList.add(middlewareTask);
        printList();
    }

    public void addHeavyTask(HeavyTask task){
        MiddlewareTask middlewareTask = new MiddlewareTask(task);
        taskList.add(middlewareTask);
        printList();
    }

    private void printList(){
        System.out.println("******************************************************");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println("Middleware Task n° : "+ taskList.get(i).getMiddlewareID()+ " Device Task n° :"+taskList.get(i).getTask().getID()+" task type : "+taskList.get(i).getTask().getType());
        }
        System.out.println("******************************************************");
    }
}
