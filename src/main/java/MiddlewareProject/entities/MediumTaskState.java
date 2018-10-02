package MiddlewareProject.entities;

public class MediumTaskState {

    private Integer taskId;
    private Integer state;
    private Integer currentTime;

    public MediumTaskState(Integer taskId, Integer state, Integer currentTime) {
        this.taskId = taskId;
        this.state = state;
        this.currentTime = currentTime;
    }

    public MediumTaskState() {
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

    public Integer getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Integer currentTime) {
        this.currentTime = currentTime;
    }
}
