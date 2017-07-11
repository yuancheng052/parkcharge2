package com.yc.parkcharge2.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;
import com.yc.parkcharge2.MyApplication;


import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;


/**
 * 类描述：网络工具类
 */
public class HttpClientUtil {


    public static void put(Context context, String url, RequestParams params, ResponseHandlerInterface response) {
        AsyncHttpClient client = HttpClientUtil.createClient(context);
        client.put(context, url, params, response);
    }

    public static void post(Context context, String url, RequestParams params, ResponseHandlerInterface response) {
        AsyncHttpClient client = HttpClientUtil.createClient(context);
        client.post(context, url, params, response);
    }

    public static void post(Context context, String url, StringEntity entity, ResponseHandlerInterface response) {
        AsyncHttpClient client = HttpClientUtil.createClient(context);

        client.post(context, url, entity,"application/json;charset=UTF-8", response);
    }

    public static void post(Context context, String url, ResponseHandlerInterface response) {
        post(context, url, new RequestParams(), response);
    }

    public static void get(Context context, String url, ResponseHandlerInterface response) {
        AsyncHttpClient client = HttpClientUtil.createClient(context);
        client.get(context, url, response);
    }

    public static void get(Context context, String url, RequestParams params, ResponseHandlerInterface response) {

        AsyncHttpClient client = HttpClientUtil.createClient(context);
        client.get(context, url, params, response);
    }

    public static void delete(Context context, String url, ResponseHandlerInterface response) {
        AsyncHttpClient client = HttpClientUtil.createClient(context);
        client.delete(context, url, response);
    }

    private static HashMap<String, String> mapHeaders = new HashMap<>();

    public static void init(Context context) {
        mapHeaders.clear();
        mapHeaders.put("Referer", "https://coding.net");

        String versionName = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = pInfo.versionName;
        } catch (Exception e) {
        }

        String userAgentValue = String.format("coding_android/%s (%s)", versionName, Build.VERSION.SDK_INT);

        mapHeaders.put("User-Agent", userAgentValue);
        mapHeaders.put("Accept", "*/*");
    }

    public static AsyncHttpClient createClient(Context context) {
        AsyncHttpClient client ;
        if(Looper.getMainLooper().getThread() == Thread.currentThread()){
            //UI(主)线程使用异步网络请求
            client = new AsyncHttpClient();
        }else{
            //新线程内使用同步网络请求
            client = new SyncHttpClient();
        }
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);

        client.setCookieStore(cookieStore);

        for (String item : mapHeaders.keySet()) {
            client.addHeader(item, mapHeaders.get(item));
        }

        client.setTimeout(60 * 1000);
        return client;
    }


    public static HashMap<String, String> getMapHeaders() {
        return mapHeaders;
    }
}
