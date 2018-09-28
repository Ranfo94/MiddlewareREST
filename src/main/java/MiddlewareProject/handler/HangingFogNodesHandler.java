package MiddlewareProject.handler;

import MiddlewareProject.task.MiddlewareTask;

import java.util.ArrayList;

public class HangingFogNodesHandler {
    //lista di lightTask che non sono stati assegnati
    private ArrayList<MiddlewareTask> hangingTaskList = new ArrayList<>();

    public MiddlewareTask addHangingLightTask(MiddlewareTask middlewareTask){
        hangingTaskList.add(middlewareTask);
        //printList();
        return middlewareTask;
    }

    private void printList(){
        System.out.println("******************************************************");
        for (int i = 0; i < hangingTaskList.size(); i++) {
            System.out.println("HANGING TASK LIST = Middleware Task n° : " + hangingTaskList.get(i).getMiddlewareID() + " Device Task n° :" +
                    hangingTaskList.get(i).getTask().getID() + " task type : " + hangingTaskList.get(i).getTask().getType());
        }
        System.out.println("******************************************************");
    }
}
