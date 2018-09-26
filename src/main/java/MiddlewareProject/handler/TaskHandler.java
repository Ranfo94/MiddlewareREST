package MiddlewareProject.handler;

import MiddlewareProject.entities.FogNode;
import MiddlewareProject.task.*;
import MiddlewareProject.utils.JsonBuilder;
import MiddlewareProject.utils.RandomNumberGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class TaskHandler {

    private ArrayList<MiddlewareTask> taskList = new ArrayList<>();

    private DiscoveryHandler discoveryHandler = new DiscoveryHandler();
    private FogNode eligibleFogNode = new FogNode();
    Integer consumption;

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
        consumption = middlewareTask.getTask().getConsumption();
        eligibleFogNode = discoveryHandler.discoverEligibleFogNode("random", middlewareTask);

        if (eligibleFogNode != null) {
            //TODO riaggiungere quello tolto quando ritorna il task (tranne la batteria)
            String fogNodePort = eligibleFogNode.getPort();
            String requestUrl = "http://localhost:" + fogNodePort + "/light";
            LightTask lightTask = requestHandler.sendLightPostRequest(requestUrl, payload);
            middlewareTask.setTask(lightTask);
        }
        //TODO gestire il caso in cui il task non viene assegnato a nessun fog node per mancanza di fog node
        return middlewareTask;
    }

    public MiddlewareTask sendMediumTask(MiddlewareTask middlewareTask) throws IOException {
        String payload = jsonBuilder.MediumTaskToJSON((MediumTask) middlewareTask.getTask());
        consumption = middlewareTask.getTask().getConsumption();
        eligibleFogNode = discoveryHandler.discoverEligibleFogNode("random", middlewareTask);

        if (eligibleFogNode != null) {
            FogNode newFog = new FogNode();
            newFog = updateCurrentValuesFogNode();
            String fogNodePort = eligibleFogNode.getPort();
            String requestUrl = "http://localhost:" + fogNodePort + "/medium";
            MediumTask mediumTask = requestHandler.sendMediumPostRequest(requestUrl, payload);
            middlewareTask.setTask(mediumTask);
        }
        //TODO gestire il caso in cui il task non viene assegnato a nessun fog node per mancanza di fog node
        return middlewareTask;
    }

    public MiddlewareTask sendHeavyTask(MiddlewareTask middlewareTask) throws IOException {
        String payload = jsonBuilder.HeavyTaskToJSON((HeavyTask) middlewareTask.getTask());
        consumption = middlewareTask.getTask().getConsumption();
        eligibleFogNode = discoveryHandler.discoverEligibleFogNode("random", middlewareTask);

        if (eligibleFogNode != null) {
            FogNode newFog = new FogNode();
            newFog = updateCurrentValuesFogNode();
            String fogNodePort = eligibleFogNode.getPort();
            String requestUrl = "http://localhost:" + fogNodePort + "/heavy";
            HeavyTask heavyTask = requestHandler.sendHeavyPostRequest(requestUrl, payload);
            middlewareTask.setTask(heavyTask);
        }
        //TODO gestire il caso in cui il task non viene assegnato a nessun fog node per mancanza di fog node
        return middlewareTask;
    }

    private FogNode updateCurrentValuesFogNode() {
        ArrayList<FogNode> currentFogNodes = RegistrationHandler.getInstance().getArrayListFogNode();
        for (FogNode eligibleNode : currentFogNodes) {
            if (Objects.equals(eligibleNode.getId(), eligibleFogNode.getId())) {
                eligibleNode.setCurrentRam(eligibleFogNode.getCurrentRam() - consumption);
                eligibleNode.setCurrentCpu(eligibleFogNode.getCurrentCpu() - consumption);
                //TODO alla batteria togliere i msec quando
                //eligibleNode.setCurrentBattery(eligibleFogNode.getCurrentBattery() - consumption);
                eligibleNode.setCurrentStorage(eligibleFogNode.getCurrentStorage() - consumption);
                return eligibleNode;
            }
        }
        return eligibleFogNode;
    }

    public MiddlewareTask searchTaskByID(int id){
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getMiddlewareID() == id){
                return taskList.get(i);
            }
        }
        return null;
    }

    public Integer getConsumption() {
        return consumption;
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
