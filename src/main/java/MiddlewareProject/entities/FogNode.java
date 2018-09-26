package MiddlewareProject.entities;


import MiddlewareProject.task.Type;

public class FogNode {
    private Integer id;
    private Integer ram;
    private Integer cpu;
    private Integer battery;
    private Integer storage;
    private Type type;
    private String port;
    private Integer currentRam;
    private Integer currentCpu;
    private Float currentBattery;
    private Integer currentStorage;

    public FogNode() { }

    public FogNode(Integer id, Integer ram, Integer cpu, Integer battery, Integer storage, Type type, String port,
                   Integer currentRam, Integer currentCpu, Float currentBattery, Integer currentStorage) {
        this.id = id;
        this.ram = ram;
        this.cpu = cpu;
        this.battery = battery;
        this.storage = storage;
        this.type = type;
        this.port = port;
        this.currentRam = currentRam;
        this.currentCpu =currentCpu;
        this.currentBattery = currentBattery;
        this.currentStorage = currentStorage;
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

    public Integer getBattery() {
        return battery;
    }

    public void setBattery(Integer battery) {
        this.battery = battery;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getCurrentRam() {
        return currentRam;
    }

    public void setCurrentRam(Integer currentRam) {
        this.currentRam = currentRam;
    }

    public Integer getCurrentCpu() {
        return currentCpu;
    }

    public void setCurrentCpu(Integer currentCpu) {
        this.currentCpu = currentCpu;
    }

    public Float getCurrentBattery() {
        return currentBattery;
    }

    public void setCurrentBattery(Float currentBattery) {
        this.currentBattery = currentBattery;
    }

    public Integer getCurrentStorage() {
        return currentStorage;
    }

    public void setCurrentStorage(Integer currentStorage) {
        this.currentStorage = currentStorage;
    }
}
