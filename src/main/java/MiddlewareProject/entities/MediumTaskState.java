package MiddlewareProject.entities;

public class MediumTaskState {

    private Integer taskId;
    private Integer state;
    private long currentTime;

    public MediumTaskState() {}

    public MediumTaskState(Integer taskId, Integer state, Integer currentTime) {
        this.taskId = taskId;
        this.state = state;
        this.currentTime = currentTime;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }
}