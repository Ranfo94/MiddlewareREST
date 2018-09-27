package MiddlewareProject.utils;


import MiddlewareProject.entities.FogNode;
import MiddlewareProject.handler.RegistrationHandler;

import java.util.ArrayList;
import java.util.Objects;

public class UpdateCurrentResourcesFogNode {

    public void subtractConsumptionFromResources(FogNode eligibleFogNode, Integer consumption) {
        ArrayList<FogNode> currentFogNodes = RegistrationHandler.getInstance().getArrayListFogNode();
        for (FogNode eligibleNode : currentFogNodes) {
            if (Objects.equals(eligibleNode.getId(), eligibleFogNode.getId())) {
                eligibleNode.setCurrentRam(eligibleFogNode.getCurrentRam() - consumption);
                eligibleNode.setCurrentCpu(eligibleFogNode.getCurrentCpu() - consumption);
                eligibleNode.setCurrentStorage(eligibleFogNode.getCurrentStorage() - consumption);
                //Decrease the currentBattery only if the fog node is not electricity supplied, otherwise it's no sense
                if (eligibleFogNode.getProva().equals("no"))
                    eligibleNode.setCurrentBattery(eligibleFogNode.getCurrentBattery() - consumption);
                return;
            }
        }
    }

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