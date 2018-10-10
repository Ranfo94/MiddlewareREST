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
    private ArrayList<HeavyTaskState> heavyTaskStateList = new ArrayList<>();

    private ArrayList<ArrayList<Integer>> fogNodeWorkload = new ArrayList<ArrayList<Integer>>();

    private String policy = "save-the-battery"; //TODO la politica va scelta dinamicamente

    private DiscoveryHandler discoveryHandler = new DiscoveryHandler();
    private UpdateCurrentResourcesFogNode ucrfn = new UpdateCurrentResourcesFogNode();
    private FogNode eligibleFogNode = new FogNode();
    private Integer consumption;
    private WorkloadHandler workloadHandler = new WorkloadHandler();
    private StateHandler stateHandler = new StateHandler();

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
        Integer id = middlewareTask.getTask().getID();

        stateHandler.setTaskState(id, middlewareTask);  //recupero lo stato del task dalla lista degli stati

        String payload = jsonBuilder.LightTaskToJSON((LightTask) middlewareTask.getTask());
        consumption = middlewareTask.getTask().getConsumption();
        eligibleFogNode = discoveryHandler.discoverEligibleFogNode(policy, middlewareTask);
        FogNode busyFogNodeToRemove = new FogNode();

        //tolgo risorse al nodo scelto e lo aggiungo alla lista dei nodi occupati
        if (eligibleFogNode != null) {
            //Subtract the consumption from the fog node that is executing the task
            for (FogNode fogNode : RegistrationHandler.getInstance().getArrayListFogNode()) {
                if (Objects.equals(eligibleFogNode.getId(), fogNode.getId())) {
                    ucrfn.subtractConsumptionFromResources(fogNode, consumption);
                    //todo controllare
                    //busyFogNodeToRemove = fogNode;
                    busyFogNodes.add(fogNode);
                    break;
                }
            }

            workloadHandler.addTaskId(eligibleFogNode.getId(), middlewareTask.getTask().getID());

            //prendo la porta del nodo scelto, credo la url e tx il task
            String fogNodePort = eligibleFogNode.getPort();
            String requestUrl = "http://localhost:" + fogNodePort + "/light";
            LightTask lightTask = requestHandler.sendLightPostRequest(requestUrl, payload);
            //todo controllare
            /*taskList.remove(middlewareTask);
            busyFogNodes.remove(busyFogNodeToRemove);
*/
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
            //todo controllare
            //taskList.remove(middlewareTask);
        }
        return middlewareTask;
    }
    //todo controllare

    public MiddlewareTask sendMediumTask(MiddlewareTask middlewareTask) throws IOException {
/*
        if (((MediumTask) middlewareTask.getTask()).getState() == null)
            ((MediumTask) middlewareTask.getTask()).setState(0);
        if (((MediumTask) middlewareTask.getTask()).getCurrentTime() == null)
            ((MediumTask) middlewareTask.getTask()).setCurrentTime(0L);
*/
        String payload = jsonBuilder.MediumTaskToJSON((MediumTask) middlewareTask.getTask());
        consumption = middlewareTask.getTask().getConsumption();
        eligibleFogNode = discoveryHandler.discoverEligibleFogNode(policy, middlewareTask);
        FogNode busyFogNodeToRemove = new FogNode();

        //TODO gestire invio dello stato
        if (eligibleFogNode != null) {
            //Subtract the consumption from the fog node that is executing the task
            for (FogNode fogNode : RegistrationHandler.getInstance().getArrayListFogNode()) {
                if (Objects.equals(eligibleFogNode.getId(), fogNode.getId())) {
                    ucrfn.subtractConsumptionFromResources(fogNode, consumption);
                    //busyFogNodeToRemove = fogNode;
                    busyFogNodes.add(fogNode);
                    break;
                }
            }

            //aggiungo l'id del task nella lista del nodo
            workloadHandler.addTaskId(eligibleFogNode.getId(), middlewareTask.getTask().getID());

            String fogNodePort = eligibleFogNode.getPort();
            String requestUrl = "http://localhost:" + fogNodePort + "/medium";
            MediumTask mediumTask = requestHandler.sendMediumPostRequest(requestUrl, payload);
            //todo controllare
            /*
            taskList.remove(middlewareTask);
            busyFogNodes.remove(busyFogNodeToRemove);
            */
            //Add again the subtracted resources from the fog node that has executed the task
            for (FogNode fogNode : RegistrationHandler.getInstance().getArrayListFogNode()) {
                if (Objects.equals(eligibleFogNode.getId(), fogNode.getId())) {
                    ucrfn.addConsumptionFromResources(eligibleFogNode, consumption);
                    break;
                }
            }

            middlewareTask.setTask(mediumTask);
        } else {       //invio al Cloud
            String requestUrl = "http://localhost:8090/mediumCloud";
            MediumTask mediumTask = requestHandler.sendCloudMediumPostRequest(requestUrl, payload);
            middlewareTask.setTask(mediumTask);
            //todo controllare
            //taskList.remove(middlewareTask);
            //toBePerformedTasks.add(middlewareTask);
        }
        return middlewareTask;
    }

    public MiddlewareTask sendHeavyTask(MiddlewareTask middlewareTask) throws IOException {
        String payload = jsonBuilder.HeavyTaskToJSON((HeavyTask) middlewareTask.getTask());
        consumption = middlewareTask.getTask().getConsumption();
        eligibleFogNode = discoveryHandler.discoverEligibleFogNode(policy, middlewareTask);
        FogNode busyFogNodeToRemove = new FogNode();

        if (eligibleFogNode != null) {
            //Subtract the consumption from the fog node that is executing the task
            for (FogNode fogNode : RegistrationHandler.getInstance().getArrayListFogNode()) {
                if (Objects.equals(eligibleFogNode.getId(), fogNode.getId())) {
                    ucrfn.subtractConsumptionFromResources(fogNode, consumption);
                    //todo controllare
                    //busyFogNodeToRemove = fogNode;
                    busyFogNodes.add(fogNode);
                    break;
                }
            }

            //aggiungo l'id del task alla lista del nodo
            workloadHandler.addTaskId(eligibleFogNode.getId(), middlewareTask.getTask().getID());

            String fogNodePort = eligibleFogNode.getPort();
            String requestUrl = "http://localhost:" + fogNodePort + "/heavy";
            HeavyTask heavyTask = requestHandler.sendHeavyPostRequest(requestUrl, payload);
            //TODO controllare
            /*
            taskList.remove(middlewareTask);
            busyFogNodes.remove(busyFogNodeToRemove);
            */
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
            //TODO controllare
            //taskList.remove(middlewareTask);
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

    public ArrayList<LightTaskState> getLightTaskStateList() {
        return lightTaskStateList;
    }

    public ArrayList<MediumTaskState> getMediumTaskStateList() {
        return mediumTaskStateList;
    }
    public ArrayList<HeavyTaskState> getHeavyTaskStateList() { return heavyTaskStateList; }

    void removeFogTask(MiddlewareTask middlewareTask) {
        mediumTaskStateList.remove(middlewareTask);
    }

    public ArrayList<ArrayList<Integer>> getFogNodeWorkload() {
        return fogNodeWorkload;
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