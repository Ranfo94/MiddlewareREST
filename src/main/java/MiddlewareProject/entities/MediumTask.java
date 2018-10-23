package MiddlewareProject.entities;


public class MediumTask extends Task {

    private int number;
    private long time;
    private Integer state;
    private Long currentTime;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Integer getState() { return state; }

    public void setState(Integer state) { this.state = state; }

    public Long getCurrentTime() { return currentTime; }

    public void setCurrentTime(Long currentTime) { this.currentTime = currentTime; }
}
