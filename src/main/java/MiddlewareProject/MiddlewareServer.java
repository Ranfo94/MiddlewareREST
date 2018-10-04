package MiddlewareProject;

import MiddlewareProject.handler.ActiveFogNodesHandler;
import MiddlewareProject.handler.TaskHandler;
import MiddlewareProject.handler.ToBePerformedTaskHandler;
import MiddlewareProject.handler.WorkerFogNodesHandler;
import MiddlewareProject.task.MiddlewareTask;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class MiddlewareServer {

    public static void main(String[] args) {
        SpringApplication.run(MiddlewareServer.class, args);

        ActiveFogNodesHandler activeFogNodesHandler = new ActiveFogNodesHandler();
        activeFogNodesHandler.checkAlivesFogNodes("no-print");

        WorkerFogNodesHandler workerFogNodesHandler = new WorkerFogNodesHandler();
        workerFogNodesHandler.checkWorkerFogNode();

        //ToBePerformedTaskHandler toBePerformedTaskHandler = new ToBePerformedTaskHandler();
        //toBePerformedTaskHandler.executeToBePerformedTasks();
    }
}
