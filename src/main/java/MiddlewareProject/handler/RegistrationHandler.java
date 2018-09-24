package MiddlewareProject.handler;

import MiddlewareProject.entities.FogNode;
import java.util.ArrayList;

/**This is a singleton class that creates a single instance for the hanlder of the registration
 */
public class RegistrationHandler {

    private static RegistrationHandler ourInstance = new RegistrationHandler();
    private ArrayList<FogNode> arrayListFogNode = new ArrayList<>();

    public static RegistrationHandler getInstance() {
        return ourInstance;
    }

    private RegistrationHandler() {
    }

    /**This method gets the list of all the active fog nodes
     * @return the list of all the active fog nodes
     */
    public ArrayList<FogNode> getArrayListFogNode() {
        return arrayListFogNode;
    }

    /**This method add the registrated fog nodes in to the list of the active fog nodes
     *
     * @param fogNode is the node to be added in the list of the active fog nodes
     * @return the list of the active fog nodes
     */
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