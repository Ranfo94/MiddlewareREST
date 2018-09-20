package MiddlewareProject.handler;


import MiddlewareProject.entities.FogNode;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class RegistrationHandler {

    private static final AtomicInteger countMiddleware = new AtomicInteger(-1);
    private static RegistrationHandler ourInstance = new RegistrationHandler();
    private ArrayList<FogNode> arrayListFogNode = new ArrayList<>();

    public static RegistrationHandler getInstance() {
        return ourInstance;
    }

    private RegistrationHandler() {
    }

    public FogNode addNodeToNodeList(FogNode fogNode) {
        fogNode.setId(countMiddleware.incrementAndGet());
        arrayListFogNode.add(fogNode);
        printFogNodeList();
        return fogNode;
    }

    private void printFogNodeList() {
        System.out.println("--------------------------------------------------------------------------");
        for (FogNode anArrayListFogNode : arrayListFogNode) {
            System.out.println("FogNode: id = " + anArrayListFogNode.getId() + "; type = " + anArrayListFogNode.getType() +
                    "; CPU = " + anArrayListFogNode.getCpu() + "; RAM = " + anArrayListFogNode.getRam() + "; battery = " +
                    anArrayListFogNode.getBattery() + "; storage = " + anArrayListFogNode.getStorage());
        }
        System.out.println("--------------------------------------------------------------------------");
    }
}