package MiddlewareProject.utils;

import MiddlewareProject.entities.LightTask;
import MiddlewareProject.entities.MediumTask;
import MiddlewareProject.entities.HeavyTask;

public class JsonBuilder {

    /**
     * This method creates a Json with all the charateristics of a light task, in order to send this Json object
     * to the eligible fog node
     * @param task the task to be transformed in a Json object
     * @return a string which represents the Json object
     */
    public String LightTaskToJSON(LightTask task){
        String payload="{ \"id\" : \"" + task.getID() + "\" , \"type\" : \"" + task.getType() + "\", \"toEncrypt\": \"" +
                task.getToEncrypt() + "\", \"consumption\" : " + task.getConsumption() + ", \"latitude\" : \"" +
                task.getLatitude() + "\", \"longitude\" : \"" + task.getLongitude() + "\", \"loopCount\" : " +
                task.getLoopCount() + "}";
        System.out.println(payload);
        return payload;
    }

    /**
     * This method creates a Json with all the charateristics of a medium task, in order to send this Json object
     * to the eligible fog node
     * @param task the task to be transformed in a Json object
     * @return a string which represents the Json object
     */
    public String MediumTaskToJSON(MediumTask task){
        String payload="{ \"id\" : \"" + task.getID() + "\" ,\"state\" : \"" + task.getState() + "\", " +
                "\"type\" : \"" + task.getType() + "\", \"time\" : \"" + task.getTime() + "\", " +
                "\"currentTime\" : \"" + task.getCurrentTime() + "\"," +
                "\"number\": \"" + task.getNumber() + "\", \"consumption\" : \"" + task.getConsumption() + "\"," +
                "\"latitude\" : \"" + task.getLatitude() + "\", \"longitude\" : \"" + task.getLongitude() + "\"," +
                "\"state\" : \"" + task.getState() + "\"}";
        System.out.println(payload);
        return payload;
    }

    /**
     * This method creates a Json with all the charateristics of a heavy task, in order to send this Json object
     * to the eligible fog node
     * @param task the task to be transformed in a Json object
     * @return a string which represents the Json object
     */
    public String HeavyTaskToJSON(HeavyTask task){
        String payload="{ \"id\" : \"" + task.getID() + "\" , \"type\" : \"" + task.getType() +"\" ,"+
                " \"n\" : \""+ task.getN()+"\", \"partial\" : \""+task.getPartial()+"\", \"last\" : \""+task.getLast()+
                "\", \"consumption\" : \"" + task.getConsumption() + "\", \"response\" : \""+task.getResponse()+"\"," +
                " \"latitude\" : \"" + task.getLatitude() + "\", \"longitude\" : \"" + task.getLongitude() + "\"}";
        System.out.println(payload);
        return payload;
    }
}
