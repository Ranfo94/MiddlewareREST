package MiddlewareProject.handler;

import MiddlewareProject.entities.FogNode;
import MiddlewareProject.task.HeavyTask;
import MiddlewareProject.task.LightTask;
import MiddlewareProject.task.MediumTask;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestHandler {

    ObjectMapper mapper = new ObjectMapper();

    ActiveFogNodesHandler activeFogNodesHandler = new ActiveFogNodesHandler();

    public LightTask sendLightPostRequest(String requestUrl, String payload, FogNode eligibleFogNode) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        sendPost(requestUrl, payload, jsonString);
        activeFogNodesHandler.checkWorkerFogNode(eligibleFogNode);
        return mapper.readValue(jsonString.toString(), LightTask.class);
    }

    public MediumTask sendMediumPostRequest(String requestUrl, String payload, FogNode eligibleFogNode) throws IOException {
    //public MediumTask sendMediumPostRequest(String requestUrl, String payload. FogNode eligibleFogNode) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        sendPost(requestUrl, payload, jsonString);
        activeFogNodesHandler.checkWorkerFogNode(eligibleFogNode);
        return mapper.readValue(jsonString.toString(), MediumTask.class);
    }

    public HeavyTask sendHeavyPostRequest(String requestUrl, String payload, FogNode eligibleFogNode) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        sendPost(requestUrl, payload, jsonString);
        activeFogNodesHandler.checkWorkerFogNode(eligibleFogNode);
        return mapper.readValue(jsonString.toString(), HeavyTask.class);
    }

    public LightTask sendCloudLightPostRequest(String requestUrl, String payload) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        sendPost(requestUrl, payload, jsonString);
        return mapper.readValue(jsonString.toString(), LightTask.class);
    }

    public MediumTask sendCloudMediumPostRequest(String requestUrl, String payload) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        sendPost(requestUrl, payload, jsonString);
        return mapper.readValue(jsonString.toString(), MediumTask.class);
    }

    public HeavyTask sendCloudHeavyPostRequest(String requestUrl, String payload) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        sendPost(requestUrl, payload, jsonString);
        return mapper.readValue(jsonString.toString(), HeavyTask.class);
    }

    public void sendPost(String requestUrl, String payload, StringBuilder jsonString) {
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(payload);
            writer.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();
            connection.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String sendGetRequest(String requestUrl) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}