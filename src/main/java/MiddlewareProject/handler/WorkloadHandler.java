package MiddlewareProject.handler;

import java.util.ArrayList;

public class WorkloadHandler {
    private ArrayList<ArrayList<Integer>> fogNodeWorkload = new ArrayList<ArrayList<Integer>>();
    private ArrayList<Integer> taskIdList = new ArrayList<Integer>();

    public void addNewFogNode(Integer fogNodeId) {
        taskIdList.add(fogNodeId);
        fogNodeWorkload.add(taskIdList);
    }

    public void addTaskId(Integer fogNodeId, int taskId) {
        new Thread(() -> {
            while (true) {
                int i, j;
                try {
                    Thread.sleep(1000);
                    /*
                    prendo la lista del carico sui nodi. Prendo la sottolista con indice pari all'id del fogNode,
                    e ci aggiungo l'id del nuovo task
                     */
                    fogNodeWorkload = TaskHandler.getInstance().getFogNodeWorkload();
                    for(i=0; i<fogNodeWorkload.size(); i++){
                        taskIdList = fogNodeWorkload.get(i);
                        if(taskIdList.get(0) == fogNodeId) {
                            taskIdList.add(taskId);
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //funzione che restituisce la taskIdList di un nodo in base al suo ID
    public ArrayList<Integer> getTaskIdList(Integer fogNodeId){
        int i;
        ArrayList<Integer> taskIdListSearched = new ArrayList<Integer>();

        fogNodeWorkload = TaskHandler.getInstance().getFogNodeWorkload();
        for(i=0; i<fogNodeWorkload.size(); i++) {
            taskIdList = fogNodeWorkload.get(i);
            if (taskIdList.get(0) == fogNodeId) {
                 taskIdListSearched = taskIdList;
            }
        }
        return  taskIdListSearched;
    }
}
