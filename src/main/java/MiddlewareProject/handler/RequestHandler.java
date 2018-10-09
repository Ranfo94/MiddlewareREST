package MiddlewareProject.handler;

import MiddlewareProject.entities.HeavyTask;
import MiddlewareProject.entities.LightTask;
import MiddlewareProject.entities.MediumTask;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestHandler {

    private ObjectMapper mapper = new ObjectMapper();

    LightTask sendLightPostRequest(String requestUrl, String payload) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        //activeFogNodesHandler.checkWorkerFogNode(eligibleFogNode);
        sendPost(requestUrl, payload, jsonString);
        return mapper.readValue(jsonString.toString(), LightTask.class);
    }

    MediumTask sendMediumPostRequest(String requestUrl, String payload) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        //activeFogNodesHandler.checkWorkerFogNode(eligibleFogNode);
        sendPost(requestUrl, payload, jsonString);
        return mapper.readValue(jsonString.toString(), MediumTask.class);
    }

    HeavyTask sendHeavyPostRequest(String requestUrl, String payload) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        //activeFogNodesHandler.checkWorkerFogNode(eligibleFogNode);
        sendPost(requestUrl, payload, jsonString);
        return mapper.readValue(jsonString.toString(), HeavyTask.class);
    }

    LightTask sendCloudLightPostRequest(String requestUrl, String payload) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        sendPost(requestUrl, payload, jsonString);
        return mapper.readValue(jsonString.toString(), LightTask.class);
    }

    MediumTask sendCloudMediumPostRequest(String requestUrl, String payload) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        sendPost(requestUrl, payload, jsonString);
        return mapper.readValue(jsonString.toString(), MediumTask.class);
    }

    HeavyTask sendCloudHeavyPostRequest(String requestUrl, String payload) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        sendPost(requestUrl, payload, jsonString);
        return mapper.readValue(jsonString.toString(), HeavyTask.class);
    }

    /**
     * This is a standard method to send the POST to the server
     * @param requestUrl is the url to reach the server
     * @param payload is te JSON object
     * @param jsonString
     */
    private void sendPost(String requestUrl, String payload, StringBuilder jsonString) {
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