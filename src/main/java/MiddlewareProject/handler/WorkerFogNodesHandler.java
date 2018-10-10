package MiddlewareProject.handler;

import MiddlewareProject.entities.FogNode;
import MiddlewareProject.task.MiddlewareTask;
import MiddlewareProject.task.Task;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.System.exit;

public class WorkerFogNodesHandler {
    ActiveFogNodesHandler activeFogNodesHandler = new ActiveFogNodesHandler();
    private ArrayList<Integer> taskIdList = new ArrayList<Integer>();
    private ArrayList<FogNode> busyFogNodes = new ArrayList<>();
    private ArrayList<MiddlewareTask> taskList = new ArrayList<>();

    public void checkWorkerFogNode(){
        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(1000);
                    //ottengo una lista di nodi occupati
                    if(busyFogNodes.size()>0) {
                        busyFogNodes = TaskHandler.getInstance().getBusyFogNodes();

                        for (FogNode busyNode : busyFogNodes) {
                            new Thread(() -> {
                                String requestUrl = "http://localhost:" + busyNode.getPort() + "/worker";
                                try {
                                    Integer aliveCode = activeFogNodesHandler.getStatus(requestUrl);
                                    if (aliveCode != 200) {
                                        WorkloadHandler workloadHandler = new WorkloadHandler();
                                        taskIdList = workloadHandler.getTaskIdList(busyNode.getId());
                                        taskList = TaskHandler.getInstance().getTaskList();
                                        MiddlewareTask middlewareTask;

                                        for (Integer taskId : taskIdList) {
                                            middlewareTask = taskList.get(taskId);
                                            if (middlewareTask.getTask().getType().equals("LIGHT"))
                                                TaskHandler.getInstance().sendLightTask(middlewareTask);
                                            if (middlewareTask.getTask().getType().equals("MEDIUM"))
                                                TaskHandler.getInstance().sendMediumTask(middlewareTask);
                                            if (middlewareTask.getTask().getType().equals("HEAVY"))
                                                TaskHandler.getInstance().sendHeavyTask(middlewareTask);
                                        }
                                        busyFogNodes = TaskHandler.getInstance().getBusyFogNodes();
                                        busyFogNodes.remove(busyNode);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }).start();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}