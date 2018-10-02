package MiddlewareProject.handler;

import MiddlewareProject.entities.FogNode;

import java.io.IOException;
import java.util.ArrayList;

public class WorkerFogNodesHandler {
    ActiveFogNodesHandler activeFogNodesHandler = new ActiveFogNodesHandler();

    private ArrayList<FogNode> busyFogNodes = new ArrayList<>();

    /*
    //lista di lightTask che non sono stati assegnati
    private ArrayList<MiddlewareTask> hangingTaskList = new ArrayList<>();

    public MiddlewareTask addHangingLightTask(MiddlewareTask middlewareTask){
        hangingTaskList.add(middlewareTask);
        //printList();
        return middlewareTask;
    }
    */

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
                            try {
                                Integer aliveCode = activeFogNodesHandler.getStatus(requestUrl);
                                if (aliveCode != 200) {
                                    //TODO gestire task interrotto ed eventualmente lo stato
                                    TaskHandler.getInstance().removeBusyNode(busyFogNodes);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
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