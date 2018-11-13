package MiddlewareProject.entities;


public class LightTaskState {

    private Integer taskId;
    private Integer loopCount;
    private String encrypted;

    public LightTaskState(Integer taskId, Integer loopCount, String encrypted) {
        this.taskId = taskId;
        this.loopCount = loopCount;
        this.encrypted = encrypted;
    }

    public LightTaskState() {
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getLoopCount() {
        return loopCount;
    }

    public void setLoopCount(Integer loopCount) {
        this.loopCount = loopCount;
    }

    public String getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }
}