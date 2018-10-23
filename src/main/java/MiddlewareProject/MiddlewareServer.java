package MiddlewareProject;

import MiddlewareProject.handler.ActiveFogNodesHandler;
import MiddlewareProject.handler.WorkerFogNodesHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiddlewareServer {

    public static void main(String[] args) {
        SpringApplication.run(MiddlewareServer.class, args);

        ActiveFogNodesHandler activeFogNodesHandler = new ActiveFogNodesHandler();
        activeFogNodesHandler.checkAlivesFogNodes("no-print");
/*
        WorkerFogNodesHandler workerFogNodesHandler = new WorkerFogNodesHandler();
        workerFogNodesHandler.checkWorkerFogNode();
*/
    }
}
