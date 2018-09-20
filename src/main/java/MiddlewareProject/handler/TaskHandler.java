package MiddlewareProject.handler;

import MiddlewareProject.task.*;
import MiddlewareProject.utils.JsonBuilder;

import java.util.ArrayList;

public class TaskHandler {

    private ArrayList<MiddlewareTask> taskList = new ArrayList<>();

    private static TaskHandler ourInstance = new TaskHandler();

    private JsonBuilder jsonBuilder;
    private RequestHandler requestHandler;

    public static TaskHandler getInstance() {
        return ourInstance;
    }

    private TaskHandler() {
        jsonBuilder = new JsonBuilder();
        requestHandler = new RequestHandler();
    }

    public ArrayList<MiddlewareTask> getTaskList() {
        return taskList;
    }

    public void addLightTask(LightTask task){
        MiddlewareTask middlewareTask = new MiddlewareTask(task);
        taskList.add(middlewareTask);
        //printList();
    }

    public void addMediumTask(MediumTask task){
        MiddlewareTask middlewareTask = new MiddlewareTask(task);
        taskList.add(middlewareTask);
        //printList();
    }

    public void addHeavyTask(HeavyTask task){
        MiddlewareTask middlewareTask = new MiddlewareTask(task);
        taskList.add(middlewareTask);
        //printList();
    }

    public String sendLightTask (LightTask lightTask){
        String payload = jsonBuilder.LightTaskToJSON(lightTask);
        String requestUrl="http://localhost:8090/light";
        return requestHandler.sendPostRequest(requestUrl, payload);
    }

    public String sendMediumTask(MediumTask mediumTask){
        String payload = jsonBuilder.MediumTaskToJSON(mediumTask);
        String requestUrl="http://localhost:8090/medium";
        return requestHandler.sendPostRequest(requestUrl, payload);
    }

    public String sendHeavyTask(HeavyTask heavyTask){
        String payload = jsonBuilder.HeavyTaskToJSON(heavyTask);
        String requestUrl="http://localhost:8090/heavy";
        return requestHandler.sendPostRequest(requestUrl, payload);
    }

    private void printList(){
        System.out.println("******************************************************");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println("Middleware Task n° : "+ taskList.get(i).getMiddlewareID()+ " Device Task n° :"+taskList.get(i).getTask().getID()+" task type : "+taskList.get(i).getTask().getType());
        }
        System.out.println("******************************************************");
    }
}
