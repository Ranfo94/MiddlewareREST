package MiddlewareProject.utils;


import MiddlewareProject.entities.HeavyTask;
import MiddlewareProject.entities.LightTask;
import MiddlewareProject.entities.MediumTask;

public class JsonBuilder {

    public String LightTaskToJSON(LightTask task){
        String payload="{ \"id\" : \"" + task.getID() + "\" , \"type\" : \"" + task.getType() + "\", \"toEncrypt\": \"" +
                task.getToEncrypt() + "\", \"consumption\" : " + task.getConsumption() + ", \"latitude\" : \"" +
                task.getLatitude() + "\", \"longitude\" : \"" + task.getLongitude() + "\"}";
        System.out.println(payload);
        return payload;
    }

    public String MediumTaskToJSON(MediumTask task){
        String payload="{ \"id\" : \"" + task.getID() + "\" , \"type\" : \"" + task.getType() + "\", \"number\": \"" +
                task.getNumber() + "\", \"consumption\" : " + task.getConsumption() + ", \"latitude\" : \"" +
                task.getLatitude() + "\", \"longitude\" : \"" + task.getLongitude() + "\"}";
        System.out.println(payload);
        return payload;
    }

    public String HeavyTaskToJSON(HeavyTask task){
        String payload="{ \"id\" : \"" + task.getID() + "\" , \"type\" : \"" + task.getType() + "\", \"response\" : \"\"" +
                ", \"consumption\" : " + task.getConsumption() + ", \"latitude\" : \"" +
                task.getLatitude() + "\", \"longitude\" : \"" + task.getLongitude() + "\"}";
        System.out.println(payload);
        return payload;
    }
}
