package MiddlewareProject.entities;

import java.math.BigInteger;

public class HeavyTaskState {

    private Integer taskId;
    private BigInteger partial;
    private int last;

    public HeavyTaskState() {
    }
    public HeavyTaskState(Integer taskId) { this.taskId = taskId; }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public BigInteger getPartial() { return partial; }

    public void setPartial(BigInteger partial) { this.partial = partial; }

    public int getLast() { return last; }

    public void setLast(int last) { this.last = last; }

}
