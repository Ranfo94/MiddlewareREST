package MiddlewareProject.handler;

import MiddlewareProject.entities.FogNode;
import MiddlewareProject.task.MiddlewareTask;
import MiddlewareProject.task.Type;
import MiddlewareProject.utils.RandomNumberGenerator;
import java.util.ArrayList;
import java.util.Objects;


public class DiscoveryHandler {

    private FogNode eligibleFogNode = new FogNode();

    private Boolean thereIsRRFogNode = false;

    private Integer rrLightCounter = 0;
    private Integer rrMediumCounter = 0;
    private Integer rrHeavyCounter = 0;

    private Integer lightThreshold = 100*100;
    private Integer mediumThreshold = 20*100*100;
    private Integer heavyThreshold = 40*100*100;

    /**This method returns the fog node eligible to solve a specific task
     *
     * @param policy is the chosen policy (random or the other)
     * @return the fog node eligible to solve a specific task
     */
    public FogNode discoverEligibleFogNode(String policy, MiddlewareTask currentTask) {
        ArrayList<FogNode> eligibleLightFogNodes = new ArrayList<>();
        ArrayList<FogNode> eligibleMediumFogNodes = new ArrayList<>();
        ArrayList<FogNode> eligibleHeavyFogNodes = new ArrayList<>();
        ArrayList<FogNode> eligibleFogNodes = RegistrationHandler.getInstance().getArrayListFogNode();

        Boolean thereIsRandomFogNode = false;

        // We create 3 sublists because if we have a light task, we want to occupy a light fog node before and, only
        // if there are no light fog nodes, we can look for medium or heavy fog nodes.
        for (FogNode eligible: eligibleFogNodes) {
            if (Objects.equals(eligible.getType(), Type.LIGHT))
                eligibleLightFogNodes.add(eligible);
            if (Objects.equals(eligible.getType(), Type.MEDIUM))
                eligibleMediumFogNodes.add(eligible);
            if (Objects.equals(eligible.getType(), Type.HEAVY))
                eligibleHeavyFogNodes.add(eligible);

        }
        if (Objects.equals(policy, "random")) {

            eligibleFogNode = getEligibleRandomFogNode(eligibleHeavyFogNodes, eligibleMediumFogNodes,
                    eligibleLightFogNodes, currentTask, thereIsRandomFogNode);

        } else if (Objects.equals(policy, "round-robin")) {

            eligibleFogNode = getEligibleRRFogNode(eligibleHeavyFogNodes, eligibleMediumFogNodes,
                    eligibleLightFogNodes, currentTask);

        } else if (Objects.equals(policy, "save-the-battery")) {
            eligibleFogNode = getEligibleSaveBatteryFogNode(eligibleHeavyFogNodes, eligibleMediumFogNodes,
                    eligibleLightFogNodes, currentTask);
        }else {
            System.out.println("La politica richiesta non è implementata nel middleware!");
        }
        return eligibleFogNode;
    }

    private FogNode getEligibleRandomFogNode(ArrayList<FogNode> eligibleHeavyFogNodes, ArrayList<FogNode> eligibleMediumFogNodes,
                                             ArrayList<FogNode> eligibleLightFogNodes, MiddlewareTask currentTask,
                                             Boolean thereIsRandomFogNode) {

        if (currentTask.getTask().getType().equals(Type.LIGHT)) {
            if (eligibleLightFogNodes.size() != 0) {
                eligibleFogNode = findRandomFogNode(eligibleLightFogNodes, lightThreshold);
                thereIsRandomFogNode = true;
            }
        }
        if ((currentTask.getTask().getType().equals(Type.LIGHT) ||
                currentTask.getTask().getType().equals(Type.MEDIUM)) && !thereIsRandomFogNode) {
            if (eligibleMediumFogNodes.size() != 0) {
                eligibleFogNode = findRandomFogNode(eligibleMediumFogNodes, mediumThreshold);
                thereIsRandomFogNode = true;
            }
        }
        if ((currentTask.getTask().getType().equals(Type.LIGHT) ||
                currentTask.getTask().getType().equals(Type.MEDIUM) ||
                currentTask.getTask().getType().equals(Type.HEAVY)) && !thereIsRandomFogNode) {
            if (eligibleHeavyFogNodes.size() != 0) {
                eligibleFogNode = findRandomFogNode(eligibleHeavyFogNodes, heavyThreshold);
                thereIsRandomFogNode = true;
            }
        }
        if (!thereIsRandomFogNode) {
            System.out.println("Non ci sono nodi fog disponibili al momento per eseguire questo task");
            eligibleFogNode = null;
        }
        return eligibleFogNode;
    }

    private FogNode getEligibleRRFogNode(ArrayList<FogNode> eligibleHeavyFogNodes, ArrayList<FogNode> eligibleMediumFogNodes,
                                             ArrayList<FogNode> eligibleLightFogNodes, MiddlewareTask currentTask) {
        RRObject rrObject;
        thereIsRRFogNode = false;

        if (currentTask.getTask().getType().equals(Type.LIGHT)) {

            rrObject = findRRFogNode(rrLightCounter, eligibleLightFogNodes, lightThreshold);
            eligibleFogNode = rrObject.getEligible();
            rrLightCounter = rrObject.getRoundRobinCounter();
        }
        if ((currentTask.getTask().getType().equals(Type.LIGHT) ||
                currentTask.getTask().getType().equals(Type.MEDIUM)) && !thereIsRRFogNode) {

            rrObject = findRRFogNode(rrMediumCounter, eligibleMediumFogNodes, mediumThreshold);
            eligibleFogNode = rrObject.getEligible();
            rrMediumCounter = rrObject.getRoundRobinCounter();
        }
        if ((currentTask.getTask().getType().equals(Type.LIGHT) ||
                currentTask.getTask().getType().equals(Type.MEDIUM) ||
                currentTask.getTask().getType().equals(Type.HEAVY)) && !thereIsRRFogNode) {
            rrObject = findRRFogNode(rrHeavyCounter, eligibleHeavyFogNodes, heavyThreshold);
            eligibleFogNode = rrObject.getEligible();
            rrHeavyCounter = rrObject.getRoundRobinCounter();
        }
        if (!thereIsRRFogNode) {
            System.out.println("Non ci sono nodi fog disponibili al momento per eseguire questo task");
            eligibleFogNode = null;
        }
        return eligibleFogNode;
    }

    private FogNode getEligibleSaveBatteryFogNode(ArrayList<FogNode> eligibleHeavyFogNodes, ArrayList<FogNode> eligibleMediumFogNodes,
                                                  ArrayList<FogNode> eligibleLightFogNodes, MiddlewareTask currentTask) {
        Boolean thereIsSaveBatteryFogNode = false;
        Integer maxPercentageBattery = 0;

        if (currentTask.getTask().getType().equals(Type.LIGHT)) {

            eligibleFogNode = findSaveBatteryFogNode(eligibleLightFogNodes, maxPercentageBattery,lightThreshold);
            if (eligibleFogNode != null)
                thereIsSaveBatteryFogNode = true;
        }
        if ((currentTask.getTask().getType().equals(Type.LIGHT) ||
                currentTask.getTask().getType().equals(Type.MEDIUM)) && !thereIsSaveBatteryFogNode) {

            eligibleFogNode = findSaveBatteryFogNode(eligibleMediumFogNodes, maxPercentageBattery,mediumThreshold);
            if (eligibleFogNode != null)
                thereIsSaveBatteryFogNode = true;
        }
        if ((currentTask.getTask().getType().equals(Type.LIGHT) ||
                currentTask.getTask().getType().equals(Type.MEDIUM) ||
                currentTask.getTask().getType().equals(Type.HEAVY)) && !thereIsSaveBatteryFogNode) {

            eligibleFogNode = findSaveBatteryFogNode(eligibleHeavyFogNodes, maxPercentageBattery,heavyThreshold);
            if (eligibleFogNode != null)
                thereIsSaveBatteryFogNode = true;
        }
        if (!thereIsSaveBatteryFogNode) {
            System.out.println("Non ci sono nodi fog disponibili al momento per eseguire questo task");
            eligibleFogNode = null;
        }
        return eligibleFogNode;
    }

    private FogNode findSaveBatteryFogNode(ArrayList<FogNode> eligibleFogNodes, Integer maxPercentageBattery,
                                           Integer threshold) {
        ArrayList<FogNode> moreEligibleNodes = new ArrayList<>();
        Integer consumption = TaskHandler.getInstance().getConsumption();

        // The first loop chooses only the nodes that can execute the task, based only on consumption
        for (FogNode eligible : eligibleFogNodes) {
            if (eligible.getCurrentRam() >= consumption && eligible.getCurrentCpu() >= consumption &&
                    eligible.getCurrentBattery() >= consumption * threshold && eligible.getCurrentStorage() >= consumption)
                moreEligibleNodes.add(eligible);
        }

        // The second loop iterates only on the nodes that can execute the task and chooses
        // the one with the max percentage battery
        for (FogNode moreEligible : moreEligibleNodes)
            if (moreEligible.getBattery() > maxPercentageBattery) {
                maxPercentageBattery = moreEligible.getBattery();
                eligibleFogNode = moreEligible;
            }
        // If the chosen node, that has the max percentage battery (only among the nodes that can execute the task based
        // on consumption!), has the battery less than a fixed threshold, we have no fog nodes able to execute the task
        if (maxPercentageBattery < threshold)
            return null;
        else
            return eligibleFogNode;
    }

    private RRObject findRRFogNode(Integer roundRobinNode, ArrayList<FogNode> eligibleFogNodes, Integer threshold) {
        Integer consumption = TaskHandler.getInstance().getConsumption();
        FogNode eligibleNode = null;

        for (FogNode eligible : eligibleFogNodes) {
            if (eligible.getCurrentRam() >= consumption && eligible.getCurrentCpu() >= consumption &&
                    eligible.getCurrentBattery() >= consumption*threshold && eligible.getCurrentStorage() >= consumption) {
                thereIsRRFogNode = true;
                eligibleNode = eligible;
                break;
            }
        }
        if (eligibleNode == null)
            return new RRObject(null, roundRobinNode);
        else {
            if (roundRobinNode + 1 > eligibleFogNodes.size() - 1)
                roundRobinNode = 0;
            else
                roundRobinNode += 1;
            return new RRObject(eligibleNode, roundRobinNode);
        }
    }

    /**This method chooses randomly the eligible fog node among the list of possible eligible fog nodes
     * @param eligibleFogNodes is the list of all the eligible fog nodes
     * @return the single eligible fog node
     */
    private FogNode findRandomFogNode(ArrayList<FogNode> eligibleFogNodes, Integer threshold) {
        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
        Integer randomFogNode;
        randomFogNode = randomNumberGenerator.generateRandom(0, eligibleFogNodes.size() - 1);
        Integer consumption = TaskHandler.getInstance().getConsumption();
        for (FogNode eligible : eligibleFogNodes) {
            if (eligible.getCurrentRam() >= consumption && eligible.getCurrentCpu() >= consumption &&
                    eligible.getCurrentBattery() >= consumption*threshold && eligible.getCurrentStorage() >= consumption) {
                eligibleFogNode = eligibleFogNodes.get(randomFogNode);
                return eligibleFogNode;
            }
        }
        return null;
    }

    private class RRObject {
        private FogNode eligible;
        private Integer roundRobinCounter;

        private RRObject(FogNode eligible, Integer roundRobinCounter) {
            this.eligible = eligible;
            this.roundRobinCounter = roundRobinCounter;
        }

        private FogNode getEligible() {
            return eligible;
        }

        private Integer getRoundRobinCounter() {
            return roundRobinCounter;
        }
    }
}