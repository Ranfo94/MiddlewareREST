package MiddlewareProject.utils;


import MiddlewareProject.task.HeavyTask;
import MiddlewareProject.task.LightTask;
import MiddlewareProject.task.MediumTask;

public class JsonBuilder {

    public String LightTaskToJSON(LightTask task){
        String payload="{ \"id\" : \""+task.getID()+"\" , \"type\" : \""+task.getType()+"\", \"toEncrypt\": \""+task.getToEncrypt()+"\"}";
        System.out.println(payload);
        return payload;
    }

    public String MediumTaskToJSON(MediumTask task){
        String payload="{ \"id\" : \""+task.getID()+"\" , \"type\" : \""+task.getType()+"\", \"number\": \""+task.getNumber()+"\"}";
        System.out.println(payload);
        return payload;
    }

    public String HeavyTaskToJSON(HeavyTask task){
        String payload="{ \"id\" : \""+task.getID()+"\" , \"type\" : \""+task.getType()+"\", \"response\" : \"\"}";
        System.out.println(payload);
        return payload;
    }
}
