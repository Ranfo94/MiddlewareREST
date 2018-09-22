package MiddlewareProject.handler;

import MiddlewareProject.entities.FogNode;
import java.util.ArrayList;


public class RegistrationHandler {

    private static RegistrationHandler ourInstance = new RegistrationHandler();
    private ArrayList<FogNode> arrayListFogNode = new ArrayList<>();

    public static RegistrationHandler getInstance() {
        return ourInstance;
    }

    private RegistrationHandler() {
    }

    public ArrayList<FogNode> getArrayListFogNode() {
        return arrayListFogNode;
    }

    public FogNode addNodeToNodeList(FogNode fogNode) {

        arrayListFogNode.add(fogNode);
        printFogNodeList();
        return fogNode;
    }

    private void printFogNodeList() {
        System.out.println("--------------------------------------------------------------------------");
        for (FogNode anArrayListFogNode : arrayListFogNode) {
            System.out.println("FogNode: id = " + anArrayListFogNode.getId() + "; type = " +
                    anArrayListFogNode.getType() + "; CPU = " + anArrayListFogNode.getCpu() + "; RAM = " +
                    anArrayListFogNode.getRam() + "; battery = " + anArrayListFogNode.getBattery() +
                    "; storage = " + anArrayListFogNode.getStorage() + ", port = " + anArrayListFogNode.getPort());
        }
        System.out.println("--------------------------------------------------------------------------");
    }
}