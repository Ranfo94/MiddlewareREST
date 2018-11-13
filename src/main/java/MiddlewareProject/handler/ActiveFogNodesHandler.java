package MiddlewareProject.handler;

import MiddlewareProject.entities.FogNode;
import MiddlewareProject.utils.GetFogNodeStatus;
import java.util.ArrayList;
import java.util.Objects;

public class ActiveFogNodesHandler {
    private  ArrayList<FogNode> aliveFogNodes = new ArrayList<>();
    private GetFogNodeStatus getFogNodeStatus = new GetFogNodeStatus();

    /**
     * This method check every second if there are active fog nodes. It spawns a thread for every fog node to check
     * and if the fog node is not alive, the method removes it from the list of the registrated fog nodes.
     */
    public void checkAlivesFogNodes(String print) {
        //In the creation of the Thread we replaced "new Runnable" with the lambda respective function "->"
        new Thread(() -> {
            Long start = System.currentTimeMillis();
            while (true) {
                try {
                    Thread.sleep(1000);

                    //ottengo una lista di nodi attivi
                    aliveFogNodes = RegistrationHandler.getInstance().getArrayListFogNode();

                        for (FogNode aliveFogNode : aliveFogNodes) {
                            new Thread(() -> {
                                //todo indirizzo fogNode
                                String requestUrl = "http://localhost:" + aliveFogNode.getPort() + "/active";
                                //String requestUrl = aliveFogNode.getAddr() + aliveFogNode.getPort() + "/active";
                                Integer aliveCode = getFogNodeStatus.getStatus(requestUrl);

                                if (aliveCode != 200) {
                                    synchronized (aliveFogNodes) {
                                        RegistrationHandler.getInstance().getArrayListFogNode().remove(aliveFogNode);
                                        aliveFogNodes.remove(aliveFogNode);
                                    }
                                }
                            }).start();
                        }
                } catch(Exception e){
                    e.printStackTrace();
                }
                if (Objects.equals(print, "print")) {
                    synchronized (aliveFogNodes) {
                        if (aliveFogNodes.size() > 0) {
                            System.out.println("\nActives fog node: ");
                            for (int i = 0; i < aliveFogNodes.size(); i++) {
                                System.out.println(i + 1 + ". id: " + aliveFogNodes.get(i).getId() +
                                        " - port: " + aliveFogNodes.get(i).getPort());
                            }
                        }
                        else
                            System.out.println("no active node");
                    }
                }
            }
        }).start();
    }
}