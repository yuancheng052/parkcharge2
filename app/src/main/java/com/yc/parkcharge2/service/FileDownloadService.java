package com.yc.parkcharge2.service;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.widget.RemoteViews;

import com.loopj.android.http.RequestParams;
import com.yc.parkcharge2.BuildConfig;
import com.yc.parkcharge2.MyApplication;
import com.yc.parkcharge2.R;
import com.yc.parkcharge2.common.AbstractCallback;
import com.yc.parkcharge2.common.Constants;
import com.yc.parkcharge2.util.HttpRequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by a on 2017/5/17.
 */

public class FileDownloadService extends Service{

    private final int NotificationID = 0x10000;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    Notification notification;
    String fileName;
    int newVersionNo;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        newVersionNo = intent.getExtras().getInt("newVersionNo");
        //sendNotice();
        //downAPK(newVersionNo);
        return super.onStartCommand(intent, flags, startId);
    }

    //发送通知栏
    public void sendNotice(){
        RemoteViews mRemoteViews = new RemoteViews(this.getPackageName(), R.layout.download_progress);
        mNotificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        //PendingIntent pendingIntent= PendingIntent.getService();
        mBuilder.setContent(mRemoteViews)
                 .setContentIntent(null)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setContentTitle("正在下载")
                .setContentText("测试内容")
                .setProgress(0, 100, true)
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(true);

        notification = mBuilder.build();
        notification.contentView = mRemoteViews;
       //mRemoteViews.setTextViewText(R.id.notice_txt, "0%");
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(NotificationID, notification);
    }


}
