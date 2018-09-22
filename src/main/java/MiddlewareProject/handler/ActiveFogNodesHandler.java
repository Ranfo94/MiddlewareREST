package MiddlewareProject.handler;

import MiddlewareProject.entities.FogNode;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ActiveFogNodesHandler {

    private  ArrayList<FogNode> aliveFogNodes = new ArrayList<>();

    public void checkAlivesFogNodes() {
        //In the creation of the Thread we replaced "new Runnable" with the lambda respective function "->"
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
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
                synchronized (aliveFogNodes) {
                    if (aliveFogNodes.size() != 0)
                        System.out.println("\nActives fog node: ");
                    for (int i = 0; i < aliveFogNodes.size(); i++) {
                        System.out.println(i+1 + ". id: " + aliveFogNodes.get(i).getId() +
                                " - port: " + aliveFogNodes.get(i).getPort());
                    }
                }
            }
        }).start();
    }

    private Integer getStatus(String url) throws IOException {

        Integer code = 1000;
        try {
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.connect();
            code = connection.getResponseCode();
        } catch (Exception e) {
            e.getMessage();
        }
        return code;
    }
}