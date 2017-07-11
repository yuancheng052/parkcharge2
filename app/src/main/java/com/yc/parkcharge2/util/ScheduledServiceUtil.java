package com.yc.parkcharge2.util;

import android.app.Service;
import android.content.Intent;

import com.yc.parkcharge2.MyApplication;
import com.yc.parkcharge2.service.ChargesRecUploadService;
import com.yc.parkcharge2.service.ParkRecDownloadService;
import com.yc.parkcharge2.service.ParkRecUploadService;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 类描述：任务轮询工具类
 */
public class ScheduledServiceUtil {


    //轮询时间间隔 --1分钟
    private static long delay = 1000*60;

    //public static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private  static boolean flag = false;

    public synchronized static void execute(){

        if(!flag){
            flag = true;
            new Thread(new ServiceRunnable()).start();
        }

    }



    static class  ServiceRunnable implements Runnable{


        public ServiceRunnable(){
        }
        @Override
        public void run() {
            try{
                while (true){
                    new ParkRecUploadService().execute();
                    Thread.sleep(delay);
                    new ParkRecDownloadService().execute();
                    Thread.sleep(delay);
                    new ChargesRecUploadService().execute();
                    Thread.sleep(delay);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
