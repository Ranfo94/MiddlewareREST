package MiddlewareProject.entities;

public class HeavyTaskState {
    //TODO da finire una volta scelto il tipo di task heavy

    private Integer taskId;

    public HeavyTaskState() {
    }
    public HeavyTaskState(Integer taskId) { this.taskId = taskId; }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

}
