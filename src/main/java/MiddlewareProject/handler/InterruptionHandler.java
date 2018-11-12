package MiddlewareProject.handler;

import java.util.ArrayList;

public class InterruptionHandler {

    private static InterruptionHandler ourInstance = new InterruptionHandler();

    /**
     * This list keeps track of all the tasks received and the nodes doing the processing.
     */
    public ArrayList<TaskNode> listTaskPort = new ArrayList<>();

    public static InterruptionHandler getInstance(){
        return ourInstance;
    }

    public void addToList(int id,String p){
        TaskNode taskNode = new TaskNode(id,p);
        listTaskPort.add(taskNode);
    }

    /**
     * This function returns the address of the FogNode currently processing the task
     */
    public String getPortByTask(int id){
        for (int i = 0; i < listTaskPort.size(); i++) {
            if(listTaskPort.get(i).getTaskId() == id){
                return listTaskPort.get(i).getNodeAddress();
            }
        }
        return null;
    }

    public void removeTask(int id){

        for (int i = 0; i < listTaskPort.size(); i++) {
            if(listTaskPort.get(i).getTaskId() ==id){
                listTaskPort.remove(i);
            }
        }
    }
}

/**
 * This class associates each task to the fog node processing it.
 */
class TaskNode{
    private int taskId;
    private String nodeAddress;

    public TaskNode(int id, String p){
        this.taskId=id;
        this.nodeAddress=p;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getNodeAddress() {
        return nodeAddress;
    }

    public void setNodeAddress(String port) {
        this.nodeAddress = port;
    }
}
