package MiddlewareProject.handler;


import MiddlewareProject.entities.FogNode;
import MiddlewareProject.task.MiddlewareTask;
import MiddlewareProject.utils.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.Objects;

public class DiscoveryHandler {

    private FogNode discoverEligibleFogNodes(Integer policy) {
        ArrayList<FogNode> activeFogNodes = RegistrationHandler.getInstance().getArrayListFogNode();
        ArrayList<MiddlewareTask> currentTasks = TaskHandler.getInstance().getTaskList();
        ArrayList<FogNode> eligibleFogNodes = new ArrayList<>();
        ArrayList<FogNode> eligibleLightFogNodes = new ArrayList<>();
        ArrayList<FogNode> eligibleMediumFogNodes = new ArrayList<>();
        ArrayList<FogNode> eligibleHeavyFogNodes = new ArrayList<>();
        FogNode eligibleFogNode = new FogNode();

        for (MiddlewareTask currentTask : currentTasks) {
            switch (currentTask.getTask().getType()) {
                case LIGHT:
                    eligibleFogNodes = discoveringNodesforLightTasks(activeFogNodes);
                    break;
                case MEDIUM:
                    eligibleFogNodes = discoveringNodesforMediumTasks(activeFogNodes);
                    break;
                case HEAVY:
                    eligibleFogNodes = activeFogNodes;
                    break;
            }
        }
        // We create 3 sublists because if we have a light task, we want to occupy a light fog node before and, only
        // if there are no light fog nodes, we can look for medium or heavy fog nodes.
        for (FogNode eligible: eligibleFogNodes) {
            if (Objects.equals(eligible.getType(), "LIGHT"))
                eligibleLightFogNodes.add(eligible);
            if (Objects.equals(eligible.getType(), "MEDIUM"))
                eligibleMediumFogNodes.add(eligible);
            if (Objects.equals(eligible.getType(), "HEAVY"))
                eligibleHeavyFogNodes.add(eligible);

        }
        if (policy == 1) {

            for (MiddlewareTask currentTask : currentTasks) {
                switch (currentTask.getTask().getType()) {
                    case LIGHT:
                        if (eligibleLightFogNodes.size() != 0)
                            eligibleFogNode = choosingFogNode(eligibleLightFogNodes);
                        else if (eligibleMediumFogNodes.size() != 0)
                            eligibleFogNode = choosingFogNode(eligibleMediumFogNodes);
                        else
                            eligibleFogNode = choosingFogNode(eligibleHeavyFogNodes);
                        break;
                    case MEDIUM:
                        if (eligibleMediumFogNodes.size() != 0)
                            eligibleFogNode = choosingFogNode(eligibleMediumFogNodes);
                        else
                            eligibleFogNode = choosingFogNode(eligibleHeavyFogNodes);
                        break;
                    case HEAVY:
                        eligibleFogNode = choosingFogNode(eligibleHeavyFogNodes);
                        break;
                }
            }

        } else {
            //TODO implementare la seconda politica di scheduling
        }
        return eligibleFogNode;
    }

    private FogNode choosingFogNode(ArrayList<FogNode> eligibleFogNodes) {
        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
        Integer randomFogNode;
        FogNode eligibleFogNode;
        randomFogNode = randomNumberGenerator.generateRandom(0, eligibleFogNodes.size() - 1);
        eligibleFogNode = eligibleFogNodes.get(randomFogNode);
        return eligibleFogNode;
    }

    private ArrayList<FogNode> discoveringNodesforLightTasks(ArrayList<FogNode> activeFogNodes) {
        ArrayList<FogNode> eligibleFogNodes = new ArrayList<>();
        for (FogNode activeFogNode: activeFogNodes)
            if (Objects.equals(activeFogNode.getType(), "LIGHT"))
                eligibleFogNodes.add(activeFogNode);
        return eligibleFogNodes;
    }

    private ArrayList<FogNode> discoveringNodesforMediumTasks(ArrayList<FogNode> activeFogNodes) {
        ArrayList<FogNode> eligibleFogNodes = new ArrayList<>();
        for (FogNode activeFogNode: activeFogNodes)
            if (Objects.equals(activeFogNode.getType(), "LIGHT") ||
                    Objects.equals(activeFogNode.getType(), "MEDIUM"))
                eligibleFogNodes.add(activeFogNode);
        return eligibleFogNodes;
    }

    public void sendTaskToTheRightFogNode() {
        FogNode eligibleFogNode = discoverEligibleFogNodes(1);
        String fogNodePort = eligibleFogNode.getPort();
        //TODO Mandare task al nodo fog con le funzioni send***Task in TaskHandler e mettere porta del nodo fog dinamicamente
    }
}