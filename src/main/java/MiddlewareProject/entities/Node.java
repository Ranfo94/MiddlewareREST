package MiddlewareProject.entities;


public class Node {
    private Integer id;
    private Integer ram;
    private Integer cpu;
    private Integer totalBattery;
    private Integer percentageBattery;
    private Integer storage;

    public Node() { }

    public Node(Integer id, Integer ram, Integer cpu, Integer totalBattery, Integer percentageBattery, Integer storage) {
        this.id = id;
        this.ram = ram;
        this.cpu = cpu;
        this.totalBattery = totalBattery;
        this.percentageBattery = percentageBattery;
        this.storage = storage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getCpu() {
        return cpu;
    }

    public void setCpu(Integer cpu) {
        this.cpu = cpu;
    }

    public Integer getTotalBattery() {
        return totalBattery;
    }

    public void setTotalBattery(Integer totalBattery) {
        this.totalBattery = totalBattery;
    }

    public Integer getPercentageBattery() {
        return percentageBattery;
    }

    public void setPercentageBattery(Integer percentageBattery) {
        this.percentageBattery = percentageBattery;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }
}
