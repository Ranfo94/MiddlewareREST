package MiddlewareProject.utils;

import MiddlewareProject.entities.FogNode;
import MiddlewareProject.handler.RegistrationHandler;

import java.util.ArrayList;
import java.util.Objects;

public class UpdateCurrentResourcesFogNode {

    /**
     * This method subtract the consumption that a task needs from the eligible fog node. This is done in order to
     * simulate the real case, that is that while a node/device is executing a task, it does not have the 100% of
     * available resources. But, if we can assign a lighter task to this node which asks less resources than the
     * available resources of the node, we can assign this task to the node. The same for a third task and so on
     * @param eligibleFogNode is the fog node where we need to subctract the resources
     * @param consumption the consumption asked from the task
     */
    public void subtractConsumptionFromResources(FogNode eligibleFogNode, Integer consumption) {
        ArrayList<FogNode> currentFogNodes = RegistrationHandler.getInstance().getArrayListFogNode();
        for (FogNode eligibleNode : currentFogNodes) {
            if (Objects.equals(eligibleNode.getId(), eligibleFogNode.getId())) {
                eligibleNode.setCurrentRam(eligibleFogNode.getCurrentRam() - consumption);
                eligibleNode.setCurrentCpu(eligibleFogNode.getCurrentCpu() - consumption);
                eligibleNode.setCurrentStorage(eligibleFogNode.getCurrentStorage() - consumption);
                //Decrease the currentBattery only if the fog node is not electricity supplied, otherwise it's no sense
                if (eligibleFogNode.getPowered().equals("no"))
                    eligibleNode.setCurrentBattery(eligibleFogNode.getCurrentBattery() - consumption);
                return;
            }
        }
    }

    /**
     * This method is required when the node has executed the task, so it adds to the subtracted node again the
     * available resources. We'll add again all the resources but the battery, of course
     * @param eligibleFogNode is the fog node where we need to subctract the resources
     * @param consumption the consumption asked from the task
     */
    public void addConsumptionFromResources(FogNode eligibleFogNode, Integer consumption) {
        ArrayList<FogNode> currentFogNodes = RegistrationHandler.getInstance().getArrayListFogNode();
        for (FogNode eligibleNode : currentFogNodes) {
            if (Objects.equals(eligibleNode.getId(), eligibleFogNode.getId())) {
                eligibleNode.setCurrentRam(eligibleFogNode.getCurrentRam() + consumption);
                eligibleNode.setCurrentCpu(eligibleFogNode.getCurrentCpu() + consumption);
                eligibleNode.setCurrentStorage(eligibleFogNode.getCurrentStorage() + consumption);
                //We can't add again the battery!
                return;
            }
        }
    }
}