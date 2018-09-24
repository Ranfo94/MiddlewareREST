package MiddlewareProject.handler;

import MiddlewareProject.entities.FogNode;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;


public class ActiveFogNodesHandler {

    private  ArrayList<FogNode> aliveFogNodes = new ArrayList<>();

    /**This method check every 5 seconds if there are active fog nodes. It spawns a thread for every fog node to check
     * If the fog node is not alive, it removes the fog node from the list of the registrated fog nodes.
     */
    public void checkAlivesFogNodes(String print) {
        //In the creation of the Thread we replaced "new Runnable" with the lambda respective function "->"
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    aliveFogNodes = RegistrationHandler.getInstance().getArrayListFogNode();
                    for (FogNode aliveFogNode : aliveFogNodes) {
                        new Thread(() -> {
                            String requestUrl = "http://localhost:" + aliveFogNode.getPort() + "/active";
                            try {
                                Integer aliveCode = getStatus(requestUrl);
                                if (aliveCode != 200) {
                                    RegistrationHandler.getInstance().getArrayListFogNode().remove(aliveFogNode);
                                    aliveFogNodes.remove(aliveFogNode);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (Objects.equals(print, "print")) {
                    synchronized (aliveFogNodes) {
                        if (aliveFogNodes.size() != 0)
                            System.out.println("\nActives fog node: ");
                        for (int i = 0; i < aliveFogNodes.size(); i++) {
                            System.out.println(i + 1 + ". id: " + aliveFogNodes.get(i).getId() +
                                    " - port: " + aliveFogNodes.get(i).getPort());
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * This method sends a ping to the server (the fog node) to check if it is down or not
     * @param url is the url of the fog node to check
     * @return the code of the ping response
     * @throws IOException
     */
    private Integer getStatus(String url) throws IOException {

        // It doesn't found the server, so the server is down
        Integer code = 404;
        try {
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.connect();
            // If it's ok, the code is 200
            code = connection.getResponseCode();
        } catch (Exception e) {
            e.getMessage();
        }
        return code;
    }
}