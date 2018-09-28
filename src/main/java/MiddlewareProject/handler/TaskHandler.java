package MiddlewareProject.handler;

import MiddlewareProject.entities.FogNode;
import MiddlewareProject.task.*;
import MiddlewareProject.utils.JsonBuilder;
import MiddlewareProject.utils.UpdateCurrentResourcesFogNode;

import java.io.IOException;
import java.util.ArrayList;

public class TaskHandler {

    private ArrayList<MiddlewareTask> taskList = new ArrayList<>();
    private ArrayList<MiddlewareTask> toBePerformedTasks = new ArrayList<>();

    private String policy = "save-the-battery";

    private DiscoveryHandler discoveryHandler = new DiscoveryHandler();
    private FogNode eligibleFogNode = new FogNode();
    private Integer consumption;

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

    public MiddlewareTask sendLightTask(MiddlewareTask middlewareTask) throws IOException {
    /*
    gestisce il task nel fogNode se viene trovato un nodo adatto, altrimenti trasmetto il task al Cloud
     */
    public MiddlewareTask sendLightTask (MiddlewareTask middlewareTask) throws IOException {
        String payload = jsonBuilder.LightTaskToJSON((LightTask) middlewareTask.getTask());
        consumption = middlewareTask.getTask().getConsumption();
        if (toBePerformedTasks.contains(middlewareTask))
            toBePerformedTasks.remove(middlewareTask);
        eligibleFogNode = discoveryHandler.discoverEligibleFogNode(policy, middlewareTask);
        if (eligibleFogNode != null) {
            //Subtract the consumption from the fog node that is executing the task
            UpdateCurrentResourcesFogNode ucvf = new UpdateCurrentResourcesFogNode();
            ucvf.subtractConsumptionFromResources(eligibleFogNode, consumption);

            String fogNodePort = eligibleFogNode.getPort();
            String requestUrl = "http://localhost:" + fogNodePort + "/light";
            LightTask lightTask = requestHandler.sendLightPostRequest(requestUrl, payload, eligibleFogNode);

            //Add again the subtracted resources from the fog node that has executed the task
            UpdateCurrentResourcesFogNode ucrfn = new UpdateCurrentResourcesFogNode();
            ucrfn.addConsumptionFromResources(eligibleFogNode, consumption);

            middlewareTask.setTask(lightTask);
        }
        else{
            String requestUrl = "http://localhost:8090/lightCloud";
            LightTask lightTask = requestHandler.sendCloudLightPostRequest(requestUrl, payload);
            middlewareTask.setTask(lightTask);
        } else {
            if (!toBePerformedTasks.contains(middlewareTask))
                toBePerformedTasks.add(middlewareTask);
        }
        //TODO gestire il caso in cui il task non viene assegnato a nessun fog node per mancanza di fog node (tutti e 3)
        return middlewareTask;
    }

    /*
    gestisce il task nel fogNode se viene trovato un nodo adatto, altrimenti trasmetto il task al Cloud
     */
    public MiddlewareTask sendMediumTask(MiddlewareTask middlewareTask) throws IOException {
        String payload = jsonBuilder.MediumTaskToJSON((MediumTask) middlewareTask.getTask());
        consumption = middlewareTask.getTask().getConsumption();
        if (toBePerformedTasks.contains(middlewareTask))
            toBePerformedTasks.remove(middlewareTask);
        eligibleFogNode = discoveryHandler.discoverEligibleFogNode(policy, middlewareTask);

        if (eligibleFogNode != null) {
            //Subtract the consumption from the fog node that is executing the task
            UpdateCurrentResourcesFogNode ucvf = new UpdateCurrentResourcesFogNode();
            ucvf.subtractConsumptionFromResources(eligibleFogNode, consumption);

            String fogNodePort = eligibleFogNode.getPort();
            String requestUrl = "http://localhost:" + fogNodePort + "/medium";
            MediumTask mediumTask = requestHandler.sendMediumPostRequest(requestUrl, payload, eligibleFogNode);

            //Add again the subtracted resources from the fog node that has executed the task
            UpdateCurrentResourcesFogNode ucrfn = new UpdateCurrentResourcesFogNode();
            ucrfn.addConsumptionFromResources(eligibleFogNode, consumption);

            middlewareTask.setTask(mediumTask);
        }
        else{
            String requestUrl = "http://localhost:8090/mediumCloud";
            MediumTask mediumTask = requestHandler.sendCloudMediumPostRequest(requestUrl, payload);
            middlewareTask.setTask(mediumTask);
        }  else {
            toBePerformedTasks.add(middlewareTask);
        }
        return middlewareTask;
    }

    /*
    gestisce il task nel fogNode se viene trovato un nodo adatto, altrimenti trasmetto il task al Cloud
     */
    public MiddlewareTask sendHeavyTask(MiddlewareTask middlewareTask) throws IOException {
        String payload = jsonBuilder.HeavyTaskToJSON((HeavyTask) middlewareTask.getTask());
        consumption = middlewareTask.getTask().getConsumption();
        if (toBePerformedTasks.contains(middlewareTask))
            toBePerformedTasks.remove(middlewareTask);
        eligibleFogNode = discoveryHandler.discoverEligibleFogNode(policy, middlewareTask);

        if (eligibleFogNode != null) {
            //Subtract the consumption from the fog node that is executing the task
            UpdateCurrentResourcesFogNode ucvf = new UpdateCurrentResourcesFogNode();
            ucvf.subtractConsumptionFromResources(eligibleFogNode, consumption);

            String fogNodePort = eligibleFogNode.getPort();
            String requestUrl = "http://localhost:" + fogNodePort + "/heavy";
            HeavyTask heavyTask = requestHandler.sendHeavyPostRequest(requestUrl, payload, eligibleFogNode);

            //Add again the subtracted resources from the fog node that has executed the task
            UpdateCurrentResourcesFogNode ucrfn = new UpdateCurrentResourcesFogNode();
            ucrfn.addConsumptionFromResources(eligibleFogNode, consumption);

            middlewareTask.setTask(heavyTask);
        }
        else{
            String requestUrl = "http://localhost:8090/heavyCloud";
            HeavyTask heavyTask = requestHandler.sendCloudHeavyPostRequest(requestUrl, payload);
            middlewareTask.setTask(heavyTask);
        }   else {
            toBePerformedTasks.add(middlewareTask);
            System.out.println("\nTOBEPERFORMEDLIST SIZE = " + toBePerformedTasks.size() + "\n");
        }
        return middlewareTask;
    }

    public MiddlewareTask searchTaskByID(int id){
        for (MiddlewareTask aTaskList : taskList) {
            if (aTaskList.getMiddlewareID() == id) {
                return aTaskList;
            }
        }
        return null;
    }

    Integer getConsumption() {
        return consumption;
    }

    ArrayList<MiddlewareTask> getToBePerformedTasks() {
        return toBePerformedTasks;
    }

    private void printList(){
        System.out.println("******************************************************");
        for (MiddlewareTask aTaskList : taskList) {
            System.out.println("Middleware Task n° : " + aTaskList.getMiddlewareID() + " Device Task n° :" +
                    aTaskList.getTask().getID() + " task type : " + aTaskList.getTask().getType());
        }
        System.out.println("******************************************************");
    }
}
