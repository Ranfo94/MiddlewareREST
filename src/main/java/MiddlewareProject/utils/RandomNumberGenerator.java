package MiddlewareProject.utils;

import java.util.concurrent.ThreadLocalRandom;


public class RandomNumberGenerator {

    /**This method return a random integer in the range specified as parameters of the method
     * @param start is the first value of the range
     * @param end is the last value of the range
     * @return the random integer
     */
    public int generateRandom(int start,int end){

        return ThreadLocalRandom.current().nextInt(start, end + 1);
    }
}
