package MiddlewareProject.task;

import java.util.concurrent.atomic.AtomicInteger;

public class MiddlewareTask {

    private static final AtomicInteger countMiddleware = new AtomicInteger(-1);
    private int middlewareID;
    private Task task;

    public MiddlewareTask(Task task) {
        this.task = task;
        middlewareID = countMiddleware.incrementAndGet();
    }

    public int getMiddlewareID() {
        return middlewareID;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
