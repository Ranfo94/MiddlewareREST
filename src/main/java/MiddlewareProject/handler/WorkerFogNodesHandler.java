package MiddlewareProject.handler;

import MiddlewareProject.entities.FogNode;
import MiddlewareProject.task.MiddlewareTask;
import MiddlewareProject.task.Task;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class WorkerFogNodesHandler {
    ActiveFogNodesHandler activeFogNodesHandler = new ActiveFogNodesHandler();

    private  ArrayList<FogNode> aliveFogNodes = new ArrayList<>();
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
                                    //TODO gestire task interrotto durante l'esecuzione
                                    /*
                                    prima ricavo i task che erano affidati a tale nodo per poterli riassegnare.
                                    Una volta finito posso cancellarlo dalla lista dei nodi occupati e attivi.
                                     */
                                    //TODO worker da correggere
                                    //prendo la lista di id dei task assegnati al nodo andato in crash
                                    /*ArrayList<Integer> TaskIdList = busyFogNodes.getTaskIdList();
                                    //scorro gli id, controllo il tipo e provo un nuovo invio

                                    for(Integer taskId: TaskIdList){
                                        MiddlewareTask middlewareTask = TaskHandler.getInstance().searchTaskByID(taskId);
                                        if(middlewareTask.getTask().getType().equals("LIGHT"))
                                            TaskHandler.getInstance().sendLightTask(middlewareTask);
                                        if(middlewareTask.getTask().getType().equals("MEDIUM"))
                                            TaskHandler.getInstance().sendMediumTask(middlewareTask);
                                        if(middlewareTask.getTask().getType().equals("HEAVY"))
                                            TaskHandler.getInstance().sendHeavyTask(middlewareTask);


                                        //rimuovo l'id del task riassegnato dalla lista. Utile nel caso in cui torni attivo??

                                    }
                                    //una volta terminato posso eliminare il nodo dalla lista dei nodi attivi e occupati
                                    TaskHandler.getInstance().removeBusyNode(busyFogNodes); //rimuovo il nodo che non risponde più
                                    aliveFogNodes = RegistrationHandler.getInstance().getArrayListFogNode();
                                    aliveFogNodes.remove(busyFogNodes);
                                    //TODO aggiungere controllo sul ritorno del fogNode come attivo
                                    */
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