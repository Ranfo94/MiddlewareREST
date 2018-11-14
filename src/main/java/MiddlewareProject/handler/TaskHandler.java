package MiddlewareProject.handler;

import MiddlewareProject.entities.*;
import MiddlewareProject.utils.JsonBuilder;
import MiddlewareProject.utils.UpdateCurrentResourcesFogNode;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Objects;

public class TaskHandler {

    private static TaskHandler ourInstance = new TaskHandler();

    private ArrayList<MiddlewareTask> taskList = new ArrayList<>();

    private DiscoveryHandler discoveryHandler = new DiscoveryHandler();
    private UpdateCurrentResourcesFogNode ucrfn = new UpdateCurrentResourcesFogNode();
    private FogNode eligibleFogNode = new FogNode();
    private Integer consumption;

    private JsonBuilder jsonBuilder;
    private RequestHandler requestHandler;

    //todo indirizzo cloud
    public String cloudIp = "a798ee2f2df9211e8b53d0267b98ef8b-757972766.us-east-2.elb.amazonaws.com";

    //TODO la politica va scelta dinamicamente
    private String policy = "save-the-battery";
    //private String policy;

    public void setPolicy(String policy){ this.policy = policy; }

    public String getPolicy(){ return policy; }

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


    /**
     * This method finds the eligble fog node (if theres no node available, it chooses the cloud) and sends the light task.
     * @param middlewareTask : task
     * @return : result
     * @throws IOException
     */
    public MiddlewareTask sendLightTask(MiddlewareTask middlewareTask) throws IOException {
        String payload = jsonBuilder.LightTaskToJSON((LightTask) middlewareTask.getTask());
        consumption = middlewareTask.getTask().getConsumption();
        eligibleFogNode = discoveryHandler.discoverEligibleFogNode(policy, middlewareTask);

        if (eligibleFogNode != null) {
            //Subtract the consumption from the fog node that is executing the task
            for (FogNode fogNode : RegistrationHandler.getInstance().getArrayListFogNode()) {
                if (Objects.equals(eligibleFogNode.getId(), fogNode.getId())) {
                    ucrfn.subtractConsumptionFromResources(fogNode, consumption);
                    break;
                }
            }

            String fogNodePort = eligibleFogNode.getPort();

            //TODO indirizzo nodo
            String requestUrl = "http://localhost:8070/light/" + middlewareTask.getMiddlewareID();
            //String requestUrl = "http://" + fogNodePort + "/light/" + middlewareTask.getMiddlewareID();
            System.out.println("url :" + requestUrl);

            //add to interruption list
            InterruptionHandler.getInstance().addToList(middlewareTask.getMiddlewareID(),fogNodePort);

            try {
                LightTask lightTask = requestHandler.sendLightPostRequest(requestUrl, payload);
                middlewareTask.setTask(lightTask);
            }catch (ConnectException connex){
                //CRASH DI UN NODO
                System.out.println("NODO CRASHATO!");
                MiddlewareTask updatedTask = taskList.get(taskList.indexOf(middlewareTask));
                sendLightTask(updatedTask);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            //Add again the subtracted resources from the fog node that has executed the task
            for (FogNode fogNode : RegistrationHandler.getInstance().getArrayListFogNode()) {
                if (Objects.equals(eligibleFogNode.getId(), fogNode.getId())) {
                    ucrfn.addConsumptionFromResources(eligibleFogNode, consumption);
                    break;
                }
            }
        }   else {
            System.out.println("Non ci sono nodi disponibili al momento per eseguire questo task:" +
                    " verrà inviato direttamente al Cloud");
            //String requestUrl = "http://localhost:8090/lightCloud/"+middlewareTask.getMiddlewareID();
            //String requestUrl = "http://localhost:8090/lightCloud";
            String requestUrl = "http://" + cloudIp + ":8090/lightCloud";

            try {
                LightTask lightTask = requestHandler.sendCloudLightPostRequest(requestUrl, payload);
                System.out.println("********************");
                System.out.println(lightTask.getEncrypted());
                System.out.println("********************");
                middlewareTask.setTask(lightTask);
                //rimuovo il task dalla lista perchè è stato trasmesso al cloud
                taskList.remove(middlewareTask);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return middlewareTask;
    }
    /**
     * This method finds the eligble fog node (if theres no node available, it chooses the cloud) and sends the medium task.
     * @param middlewareTask : task
     * @return : result
     * @throws IOException
     */
    public MiddlewareTask sendMediumTask(MiddlewareTask middlewareTask) throws IOException {
        String payload = jsonBuilder.MediumTaskToJSON((MediumTask) middlewareTask.getTask());
        consumption = middlewareTask.getTask().getConsumption();
        eligibleFogNode = discoveryHandler.discoverEligibleFogNode(policy, middlewareTask);

        if (eligibleFogNode != null) {
            //Subtract the consumption from the fog node that is executing the task
            for (FogNode fogNode : RegistrationHandler.getInstance().getArrayListFogNode()) {
                if (Objects.equals(eligibleFogNode.getId(), fogNode.getId())) {
                    ucrfn.subtractConsumptionFromResources(fogNode, consumption);
                    break;
                }
            }

            String fogNodePort = eligibleFogNode.getPort();
            //String requestUrl = "http://localhost:" + fogNodePort + "/medium/" + middlewareTask.getMiddlewareID();
            String requestUrl = "http://localhost:8070/medium/" + middlewareTask.getMiddlewareID();

            //add to interruption list
            InterruptionHandler.getInstance().addToList(middlewareTask.getMiddlewareID(),fogNodePort);

            try {
                MediumTask mediumTask = requestHandler.sendMediumPostRequest(requestUrl, payload);
                middlewareTask.setTask(mediumTask);
            }catch (ConnectException connex){
                //CRASH DI UN NODO
                System.out.println("NODO CRASHATO!");
                MiddlewareTask updatedTask = taskList.get(taskList.indexOf(middlewareTask));
                sendMediumTask(updatedTask);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            //Add again the subtracted resources from the fog node that has executed the task
            for (FogNode fogNode : RegistrationHandler.getInstance().getArrayListFogNode()) {
                if (Objects.equals(eligibleFogNode.getId(), fogNode.getId())) {
                    ucrfn.addConsumptionFromResources(eligibleFogNode, consumption);
                    break;
                }
            }
        }   else {
            System.out.println("Non ci sono nodi disponibili al momento per eseguire questo task:" +
                    " verrà inviato direttamente al Cloud");
            String requestUrl = "http://localhost:8090/mediumCloud/"+middlewareTask.getMiddlewareID();

            try {
                MediumTask mediumTask = requestHandler.sendCloudMediumPostRequest(requestUrl, payload);
                System.out.println("********************");
                System.out.println(mediumTask.getTime());
                System.out.println("********************");
                middlewareTask.setTask(mediumTask);
                //rimuovo il task dalla lista perchè è stato trasmesso al cloud
                taskList.remove(middlewareTask);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return middlewareTask;
    }
    /**
     * This method finds the eligble fog node (if theres no node available, it chooses the cloud) and sends the heavy task.
     * @param middlewareTask : task
     * @return : result
     * @throws IOException
     */
    public MiddlewareTask sendHeavyTask(MiddlewareTask middlewareTask) {
        String payload = jsonBuilder.HeavyTaskToJSON((HeavyTask) middlewareTask.getTask());
        consumption = middlewareTask.getTask().getConsumption();
        eligibleFogNode = discoveryHandler.discoverEligibleFogNode(policy, middlewareTask);

        if (eligibleFogNode != null) {
            //Subtract the consumption from the fog node that is executing the task
            for (FogNode fogNode : RegistrationHandler.getInstance().getArrayListFogNode()) {
                if (Objects.equals(eligibleFogNode.getId(), fogNode.getId())) {
                    ucrfn.subtractConsumptionFromResources(fogNode, consumption);
                    break;
                }
            }

            String fogNodePort = eligibleFogNode.getPort();
            //String requestUrl = "http://localhost:" + fogNodePort + "/heavy/" + middlewareTask.getMiddlewareID();
            String requestUrl = "http://localhost:8070/heavy/" + middlewareTask.getMiddlewareID();

            //add to interruption list
            InterruptionHandler.getInstance().addToList(middlewareTask.getMiddlewareID(),"http://localhost:" + fogNodePort);

            try {
                HeavyTask heavyTask = requestHandler.sendHeavyPostRequest(requestUrl, payload);
                middlewareTask.setTask(heavyTask);
            }catch (ConnectException connex){
                //CRASH DI UN NODO
                System.out.println("NODO CRASHATO!");
                MiddlewareTask updatedTask = taskList.get(taskList.indexOf(middlewareTask));
                sendHeavyTask(updatedTask);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            //Add again the subtracted resources from the fog node that has executed the task
            for (FogNode fogNode : RegistrationHandler.getInstance().getArrayListFogNode()) {
                if (Objects.equals(eligibleFogNode.getId(), fogNode.getId())) {
                    ucrfn.addConsumptionFromResources(eligibleFogNode, consumption);
                    break;
                }
            }
        }   else {
            System.out.println("Non ci sono nodi disponibili al momento per eseguire questo task:" +
                    " verrà inviato direttamente al Cloud");
            String requestUrl = "http://localhost:8090/heavyCloud/"+middlewareTask.getMiddlewareID();

            //add to interruption list
            InterruptionHandler.getInstance().addToList(middlewareTask.getMiddlewareID(),"http://localhost:8090");

            try {
                HeavyTask heavyTask = requestHandler.sendCloudHeavyPostRequest(requestUrl, payload);
                System.out.println("********************");
                System.out.println(heavyTask.getResponse());
                System.out.println("********************");
                middlewareTask.setTask(heavyTask);
                //rimuovo il task dalla lista perchè è stato trasmesso al cloud
                taskList.remove(middlewareTask);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return middlewareTask;
    }

    /**
     * this method searches for the task matching the id in the list
     * @param id
     * @return
     */
    public MiddlewareTask searchTaskByID(int id){
        for (MiddlewareTask aTaskList : taskList) {
            if (aTaskList.getMiddlewareID() == id) {
                return aTaskList;
            }
        }
        return null;
    }

    Integer getConsumption() { return consumption; }

    private void printList(){
        System.out.println("******************************************************");
        for (MiddlewareTask aTaskList : taskList) {
            System.out.println("Middleware Task n° : " + aTaskList.getMiddlewareID() + " Device Task n° :" +
                    aTaskList.getTask().getID() + " task type : " + aTaskList.getTask().getType());
        }
        System.out.println("******************************************************");
    }

    /**
     * this method calls for the interruption request
     * @param urlString
     * @param id
     * @return
     * @throws IOException
     */
    public String sendInterruptionRequest(String urlString, int id) throws IOException {

        String res = requestHandler.sendInterruptionRequest(urlString, id);
        return res;

    }

    public boolean removeTask(MiddlewareTask task){
        for (int i = 0; i < taskList.size(); i++) {
            if(taskList.get(i).getMiddlewareID() == task.getMiddlewareID()){
                taskList.remove(i);
                return true;
            }
        }
        return false;
    }

    public void updateTask(MiddlewareTask task){

        for (int i = 0; i < taskList.size() ; i++) {
            if(taskList.get(i).getMiddlewareID() == task.getMiddlewareID()) {
                taskList.remove(i);
                taskList.add(task);
            }
        }

    }

    public void printPartial(int id){
        for (int i = 0; i < taskList.size() ; i++) {
            if(taskList.get(i).getMiddlewareID() == id) {
                HeavyTask ht = (HeavyTask) taskList.get(i).getTask();
                System.out.println("partial "+ht.getPartial());
            }
        }
    }
}