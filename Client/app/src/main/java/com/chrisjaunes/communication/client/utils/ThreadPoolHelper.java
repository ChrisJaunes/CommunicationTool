package com.chrisjaunes.communication.client.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolHelper {
    private ExecutorService executorService = null;
    private static ThreadPoolHelper instance = null;
    public static ThreadPoolHelper getInstance(){
        if(instance == null ) instance = new ThreadPoolHelper();
        return instance;
    }
    public void execute(Runnable r){
        if( executorService == null ) executorService = Executors.newFixedThreadPool(10);
        executorService.execute(r);
    }
}
