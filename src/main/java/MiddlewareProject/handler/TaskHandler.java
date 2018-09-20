package MiddlewareProject.handler;

import MiddlewareProject.task.*;
import MiddlewareProject.utils.JsonBuilder;
import com.sun.deploy.net.HttpResponse;

import java.io.IOException;
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

    public MiddlewareTask addLightTask(LightTask task){
        MiddlewareTask middlewareTask = new MiddlewareTask(task);
        taskList.add(middlewareTask);
        return middlewareTask;
        //printList();
    }

    public MiddlewareTask addMediumTask(MediumTask task){
        MiddlewareTask middlewareTask = new MiddlewareTask(task);
        taskList.add(middlewareTask);
        return middlewareTask;
        //printList();
    }

    public MiddlewareTask addHeavyTask(HeavyTask task){
        MiddlewareTask middlewareTask = new MiddlewareTask(task);
        taskList.add(middlewareTask);
        return middlewareTask;
        //printList();
    }

    public MiddlewareTask sendLightTask (MiddlewareTask middlewareTask) throws IOException {
        String payload = jsonBuilder.LightTaskToJSON((LightTask) middlewareTask.getTask());
        String requestUrl="http://localhost:8090/light";
        LightTask lightTask = requestHandler.sendLightPostRequest(requestUrl, payload);
        middlewareTask.setTask(lightTask);
        return middlewareTask;
    }

    public MiddlewareTask sendMediumTask(MiddlewareTask middlewareTask) throws IOException {
        String payload = jsonBuilder.MediumTaskToJSON((MediumTask) middlewareTask.getTask());
        String requestUrl="http://localhost:8090/medium";
        MediumTask mediumTask = requestHandler.sendMediumPostRequest(requestUrl, payload);
        middlewareTask.setTask(mediumTask);
        return middlewareTask;
    }

    public MiddlewareTask sendHeavyTask(MiddlewareTask middlewareTask) throws IOException {
        String payload = jsonBuilder.HeavyTaskToJSON((HeavyTask) middlewareTask.getTask());
        String requestUrl="http://localhost:8090/heavy";
        HeavyTask heavyTask =  requestHandler.sendHeavyPostRequest(requestUrl, payload);
        middlewareTask.setTask(heavyTask);
        return middlewareTask;
    }

    private void printList(){
        System.out.println("******************************************************");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println("Middleware Task n° : " + taskList.get(i).getMiddlewareID() + " Device Task n° :" +
                    taskList.get(i).getTask().getID() + " task type : " + taskList.get(i).getTask().getType());
        }
        System.out.println("******************************************************");
    }
}
