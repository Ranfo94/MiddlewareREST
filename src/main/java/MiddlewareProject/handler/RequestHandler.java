package MiddlewareProject.handler;

import MiddlewareProject.task.HeavyTask;
import MiddlewareProject.task.LightTask;
import MiddlewareProject.task.MediumTask;
import MiddlewareProject.utils.Requests;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;


public class RequestHandler {

    private ObjectMapper mapper = new ObjectMapper();
    private Requests requests = new Requests();

    public LightTask sendLightPostRequest(String requestUrl, String payload) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        requests.sendPostRequest(requestUrl, payload, jsonString);
        return mapper.readValue(jsonString.toString(), LightTask.class);
    }

    public MediumTask sendMediumPostRequest(String requestUrl, String payload) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        requests.sendPostRequest(requestUrl, payload, jsonString);
        return mapper.readValue(jsonString.toString(), MediumTask.class);
    }

    public HeavyTask sendHeavyPostRequest(String requestUrl, String payload) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        requests.sendPostRequest(requestUrl, payload, jsonString);
        return mapper.readValue(jsonString.toString(), HeavyTask.class);
    }
}
