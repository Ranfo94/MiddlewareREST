package MiddlewareProject;

import MiddlewareProject.handler.ActiveFogNodesHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class MiddlewareServer {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(MiddlewareServer.class, args);

        //todo correggere errore su schermo quando non trova un nodo e invia al cloud

        ActiveFogNodesHandler activeFogNodesHandler = new ActiveFogNodesHandler();
        activeFogNodesHandler.checkAlivesFogNodes("no-print");

    }
}
