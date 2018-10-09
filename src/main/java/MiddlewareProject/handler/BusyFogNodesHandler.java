package MiddlewareProject.handler;

import MiddlewareProject.entities.FogNode;
import MiddlewareProject.entities.MediumTaskState;
import MiddlewareProject.entities.MiddlewareTask;
import MiddlewareProject.utils.GetFogNodeStatus;
import java.util.ArrayList;


public class BusyFogNodesHandler {
    
    private ArrayList<FogNode> busyFogNodes = new ArrayList<>();
    private GetFogNodeStatus getFogNodeStatus = new GetFogNodeStatus();
    private ArrayList<MiddlewareTask> taskList = TaskHandler.getInstance().getTaskList();
    private ArrayList<MediumTaskState> mediumTaskStateList = TaskHandler.getInstance().getMediumTaskStateList();

    public void checkWorkerFogNode(){
        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(1000);
                    //ottengo una lista di nodi occupati
                    busyFogNodes = TaskHandler.getInstance().getBusyFogNodes();

                    for(FogNode busyFogNodes : busyFogNodes) {
                        new Thread(() -> {
                            String requestUrl = "http://localhost:" + busyFogNodes.getPort() + "/worker";
                            Integer aliveCode = getFogNodeStatus.getStatus(requestUrl);

                            if (aliveCode != 200) {
                                TaskHandler.getInstance().removeBusyNode(busyFogNodes);
                            }
                        }).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    
}