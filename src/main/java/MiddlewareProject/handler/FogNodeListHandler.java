package MiddlewareProject.handler;

import MiddlewareProject.entities.FogNode;
import MiddlewareProject.utils.Requests;
import java.util.ArrayList;


public class FogNodeListHandler {

    private Requests requests = new Requests();

    public void checkAlivesFogNodes() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                        ArrayList<FogNode> aliveFogNodes = RegistrationHandler.getInstance().getArrayListFogNode();
                        for (FogNode aliveFogNode : aliveFogNodes) {
                            StringBuilder jsonString = new StringBuilder();
                            String requestUrl = "http://localhost:" +
                                    aliveFogNode.getPort() +
                                    aliveFogNode.getId();
                            requests.sendPostRequest(requestUrl, "Are you alive?", jsonString);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
