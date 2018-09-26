package MiddlewareProject.task;

/**
 * Created by andreacifola on 18/09/2018.
 */
public class Task {

    private int ID;
    private Type type;
    private Integer consumption;

    public Task(){}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getConsumption() {
        return consumption;
    }

    public void setConsumption(Integer consumption) {
        this.consumption = consumption;
    }
}
