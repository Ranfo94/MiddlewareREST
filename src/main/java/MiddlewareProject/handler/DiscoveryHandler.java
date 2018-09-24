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

        } else {
            //TODO implementare la politica di scheduling complessa
        }
        return eligibleFogNode;
    }

    private FogNode getEligibleRandomFogNode(ArrayList<FogNode> eligibleHeavyFogNodes, ArrayList<FogNode> eligibleMediumFogNodes,
                                             ArrayList<FogNode> eligibleLightFogNodes, MiddlewareTask currentTask, Boolean thereIsRandomFogNode) {

        if (currentTask.getTask().getType().equals(Type.LIGHT)) {
            if (eligibleLightFogNodes.size() != 0) {
                eligibleFogNode = choosingRandomFogNode(eligibleLightFogNodes);
                thereIsRandomFogNode = true;
            }
        }
        if ((currentTask.getTask().getType().equals(Type.LIGHT) ||
                currentTask.getTask().getType().equals(Type.MEDIUM)) && !thereIsRandomFogNode) {
            if (eligibleMediumFogNodes.size() != 0) {
                eligibleFogNode = choosingRandomFogNode(eligibleMediumFogNodes);
                thereIsRandomFogNode = true;
            }
        }
        if ((currentTask.getTask().getType().equals(Type.LIGHT) ||
                currentTask.getTask().getType().equals(Type.MEDIUM) ||
                currentTask.getTask().getType().equals(Type.HEAVY)) && !thereIsRandomFogNode) {
            if (eligibleHeavyFogNodes.size() != 0) {
                eligibleFogNode = choosingRandomFogNode(eligibleHeavyFogNodes);
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

            rrObject = findRRFogNode(rrLightCounter, eligibleHeavyFogNodes);
            eligibleFogNode = rrObject.getEligible();
            rrLightCounter = rrObject.getRoundRobinCounter();
        }
        if ((currentTask.getTask().getType().equals(Type.LIGHT) ||
                currentTask.getTask().getType().equals(Type.MEDIUM)) && !thereIsRRFogNode) {

            rrObject = findRRFogNode(rrMediumCounter, eligibleMediumFogNodes);
            eligibleFogNode = rrObject.getEligible();
            rrMediumCounter = rrObject.getRoundRobinCounter();
        }
        if ((currentTask.getTask().getType().equals(Type.LIGHT) ||
                currentTask.getTask().getType().equals(Type.MEDIUM) ||
                currentTask.getTask().getType().equals(Type.HEAVY)) && !thereIsRRFogNode) {
            rrObject = findRRFogNode(rrHeavyCounter, eligibleLightFogNodes);
            eligibleFogNode = rrObject.getEligible();
            rrHeavyCounter = rrObject.getRoundRobinCounter();
        }
        if (!thereIsRRFogNode) {
            System.out.println("Non ci sono nodi fog disponibili al momento per eseguire questo task");
            eligibleFogNode = null;
        }
        return eligibleFogNode;
    }

    private RRObject findRRFogNode(Integer roundRobinNode, ArrayList<FogNode> eligibleFogNodes) {
        FogNode eligibleFogNode = new FogNode();
        if (eligibleFogNodes.size() != 0) {
            thereIsRRFogNode = true;
            if (roundRobinNode > eligibleFogNodes.size()-1)
                roundRobinNode = 0;
            eligibleFogNode = eligibleFogNodes.get(roundRobinNode);
            if (roundRobinNode+1 > eligibleFogNodes.size()-1)
                roundRobinNode = 0;
            else
                roundRobinNode += 1;
        }
        return new RRObject(eligibleFogNode, roundRobinNode);
    }

    /**This method chooses randomly the eligible fog node among the list of possible eligible fog nodes
     * @param eligibleFogNodes is the list of all the eligible fog nodes
     * @return the single eligible fog node
     */
    private FogNode choosingRandomFogNode(ArrayList<FogNode> eligibleFogNodes) {
        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
        Integer randomFogNode;
        randomFogNode = randomNumberGenerator.generateRandom(0, eligibleFogNodes.size() - 1);
        eligibleFogNode = eligibleFogNodes.get(randomFogNode);
        return eligibleFogNode;
    }


    private class RRObject {
        private FogNode eligible;
        private Integer roundRobinCounter;

        public RRObject(FogNode eligible, Integer roundRobinCounter) {
            this.eligible = eligible;
            this.roundRobinCounter = roundRobinCounter;
        }

        public FogNode getEligible() {
            return eligible;
        }

        public void setEligible(FogNode eligible) {
            this.eligible = eligible;
        }

        public Integer getRoundRobinCounter() {
            return roundRobinCounter;
        }

        public void setRoundRobinCounter(Integer roundRobinCounter) {
            this.roundRobinCounter = roundRobinCounter;
        }
    }
}