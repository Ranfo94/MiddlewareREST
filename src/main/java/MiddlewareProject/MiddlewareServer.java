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

        InetAddress IP=InetAddress.getLocalHost();
        System.out.println("IP of my system is := "+IP.getHostAddress());

        ActiveFogNodesHandler activeFogNodesHandler = new ActiveFogNodesHandler();
        activeFogNodesHandler.checkAlivesFogNodes("no-print");
    }
}
