package MiddlewareProject.utils;

import java.net.HttpURLConnection;
import java.net.URL;

public class GetFogNodeStatus {

    /**
     * This method sends a ping to the server (the fog node) to check if it is down or not
     * @param url is the url of the fog node to check
     * @return the code of the ping response
     */
    public Integer getStatus(String url) {
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
