package MiddlewareProject.entities;

public class LightTask extends Task {

    private String toEncrypt;
    //stato del lightTask
    private String encrypted;
    private Integer loopCount;

    public LightTask(){}

    public LightTask(String string){
        this.toEncrypt = string;
    }

    public String getToEncrypt() {
        return toEncrypt;
    }

    public void setToEncrypt(String toEncrypt) {
        this.toEncrypt = toEncrypt;
    }

    public String getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }

    public Integer getLoopCount() { return loopCount; }

    public void setLoopCount(Integer loopCount) { this.loopCount = loopCount; }
}
