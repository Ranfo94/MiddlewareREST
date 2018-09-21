package MiddlewareProject.handler;


import MiddlewareProject.entities.FogNode;

import java.util.ArrayList;

public class FogNodeListHandler {

    RequestHandler requestHandler = new RequestHandler();

    public void checkAlivesFogNodes() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ArrayList<FogNode> aliveFogNodes = RegistrationHandler.getInstance().getArrayListFogNode();
                    for (int i = 0; i < aliveFogNodes.size(); i++) {


                    }
                }
            }
        }).start();
    }
}
