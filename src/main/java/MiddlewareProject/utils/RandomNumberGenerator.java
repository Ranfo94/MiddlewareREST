package MiddlewareProject.utils;

import java.util.concurrent.ThreadLocalRandom;


public class RandomNumberGenerator {

    public int generateRandom(int start,int end){
        return ThreadLocalRandom.current().nextInt(start, end + 1);
    }
}
