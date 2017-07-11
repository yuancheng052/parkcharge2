package com.yc.parkcharge2.util;

import android.os.Looper;
import android.widget.Toast;

import com.yc.parkcharge2.MyApplication;

/**
 * Created by a on 2017/6/1.
 */

public class MsgUtil {

    public static void showMsg(String msg){
        if(Looper.getMainLooper().getThread() == Thread.currentThread()){
            //UI(主)线程内才提示消息
            Toast.makeText(MyApplication.getInstances(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
