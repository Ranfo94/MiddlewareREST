package MiddlewareProject.handler;

import MiddlewareProject.entities.FogNode;
import MiddlewareProject.entities.LightTaskState;
import MiddlewareProject.entities.MediumTaskState;
import MiddlewareProject.task.*;
import MiddlewareProject.utils.JsonBuilder;
import MiddlewareProject.utils.UpdateCurrentResourcesFogNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class TaskHandler {

    private ArrayList<MiddlewareTask> taskList = new ArrayList<>();

    private ArrayList<FogNode> busyFogNodes = new ArrayList<>();
    private ArrayList<LightTaskState> lightTaskStateList = new ArrayList<>();
    private ArrayList<MediumTaskState> mediumTaskStateList = new ArrayList<>();

    private String policy = "save-the-battery";

    private DiscoveryHandler discoveryHandler = new DiscoveryHandler();
    private UpdateCurrentResourcesFogNode ucrfn = new UpdateCurrentResourcesFogNode();
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
        String payload = jsonBuilder.LightTaskToJSON((LightTask) middlewareTask.getTask());
        consumption = middlewareTask.getTask().getConsumption();
        eligibleFogNode = discoveryHandler.discoverEligibleFogNode(policy, middlewareTask);

        if (eligibleFogNode != null) {
            //Subtract the consumption from the fog node that is executing the task
            for (FogNode fogNode : RegistrationHandler.getInstance().getArrayListFogNode()) {
                if (Objects.equals(eligibleFogNode.getId(), fogNode.getId())) {
                    ucrfn.subtractConsumptionFromResources(fogNode, consumption);
                    busyFogNodes.add(fogNode);
                    break;
                }
            }


            String fogNodePort = eligibleFogNode.getPort();
            String requestUrl = "http://localhost:" + fogNodePort + "/light";
            LightTask lightTask = requestHandler.sendLightPostRequest(requestUrl, payload, eligibleFogNode);

            //Add again the subtracted resources from the fog node that has executed the task
            for (FogNode fogNode : RegistrationHandler.getInstance().getArrayListFogNode()) {
                if (Objects.equals(eligibleFogNode.getId(), fogNode.getId())) {
                   ucrfn.addConsumptionFromResources(eligibleFogNode, consumption);
                    break;
                }
            }

            middlewareTask.setTask(lightTask);
        } else {
            String requestUrl = "http://localhost:8090/lightCloud";
            LightTask lightTask = requestHandler.sendCloudLightPostRequest(requestUrl, payload);
            middlewareTask.setTask(lightTask);
            /*
            if (!toBePerformedTasks.contains(middlewareTask))
                toBePerformedTasks.add(middlewareTask);
                */
        }
        return middlewareTask;
    }

    public MiddlewareTask sendMediumTask(MiddlewareTask middlewareTask) throws IOException {
        String payload = jsonBuilder.MediumTaskToJSON((MediumTask) middlewareTask.getTask());
        consumption = middlewareTask.getTask().getConsumption();
        eligibleFogNode = discoveryHandler.discoverEligibleFogNode(policy, middlewareTask);

        if (eligibleFogNode != null) {
            //Subtract the consumption from the fog node that is executing the task
            for (FogNode fogNode : RegistrationHandler.getInstance().getArrayListFogNode()) {
                if (Objects.equals(eligibleFogNode.getId(), fogNode.getId())) {
                    ucrfn.subtractConsumptionFromResources(fogNode, consumption);
                    busyFogNodes.add(fogNode);
                    break;
                }
            }


            String fogNodePort = eligibleFogNode.getPort();
            String requestUrl = "http://localhost:" + fogNodePort + "/medium";
            MediumTask mediumTask = requestHandler.sendMediumPostRequest(requestUrl, payload, eligibleFogNode);

            //Add again the subtracted resources from the fog node that has executed the task
            for (FogNode fogNode : RegistrationHandler.getInstance().getArrayListFogNode()) {
                if (Objects.equals(eligibleFogNode.getId(), fogNode.getId())) {
                    ucrfn.addConsumptionFromResources(eligibleFogNode, consumption);
                    break;
                }
            }

            middlewareTask.setTask(mediumTask);
        }  else {
            String requestUrl = "http://localhost:8090/mediumCloud";
            MediumTask mediumTask = requestHandler.sendCloudMediumPostRequest(requestUrl, payload);
            middlewareTask.setTask(mediumTask);
            //toBePerformedTasks.add(middlewareTask);
        }
        return middlewareTask;
    }

    public MiddlewareTask sendHeavyTask(MiddlewareTask middlewareTask) throws IOException {
        String payload = jsonBuilder.HeavyTaskToJSON((HeavyTask) middlewareTask.getTask());
        consumption = middlewareTask.getTask().getConsumption();
        eligibleFogNode = discoveryHandler.discoverEligibleFogNode(policy, middlewareTask);

        if (eligibleFogNode != null) {
            //Subtract the consumption from the fog node that is executing the task
            for (FogNode fogNode : RegistrationHandler.getInstance().getArrayListFogNode()) {
                if (Objects.equals(eligibleFogNode.getId(), fogNode.getId())) {
                    ucrfn.subtractConsumptionFromResources(fogNode, consumption);
                    //TODO eliminare nodo fog dai busy quando ha finito la computazione
                    busyFogNodes.add(fogNode);
                    break;
                }
            }


            String fogNodePort = eligibleFogNode.getPort();
            String requestUrl = "http://localhost:" + fogNodePort + "/heavy";
            HeavyTask heavyTask = requestHandler.sendHeavyPostRequest(requestUrl, payload, eligibleFogNode);

            //Add again the subtracted resources from the fog node that has executed the task
            for (FogNode fogNode : RegistrationHandler.getInstance().getArrayListFogNode()) {
                if (Objects.equals(eligibleFogNode.getId(), fogNode.getId())) {
                    ucrfn.addConsumptionFromResources(eligibleFogNode, consumption);
                    break;
                }
            }

            middlewareTask.setTask(heavyTask);
        }   else {
            String requestUrl = "http://localhost:8090/heavyCloud";
            HeavyTask heavyTask = requestHandler.sendCloudHeavyPostRequest(requestUrl, payload);
            middlewareTask.setTask(heavyTask);
            /*
            toBePerformedTasks.add(middlewareTask);
            System.out.println("\nTOBEPERFORMEDLIST SIZE = " + toBePerformedTasks.size() + "\n");
            */
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

    ArrayList<FogNode> getBusyFogNodes() {
        return busyFogNodes;
    }

    void removeBusyNode(FogNode fogNode) {
        busyFogNodes.remove(fogNode);
    }

    public ArrayList<MediumTaskState> getMediumTaskStateList() {
        return mediumTaskStateList;
    }

    public ArrayList<LightTaskState> getLightTaskStateList() {
        return lightTaskStateList;
    }

    void removeFogTask(MiddlewareTask middlewareTask) {
        mediumTaskStateList.remove(middlewareTask);
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