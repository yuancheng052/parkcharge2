package com.yc.parkcharge2.util;

import android.content.Context;

import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yc.parkcharge2.common.AbstractCallback;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by a on 2017/5/18.
 */

public class HttpRequestUtil {

    public static void request(String url, RequestParams params, final Context context, final AbstractCallback callback){

        HttpClientUtil.post(context, url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               callback.onSuccess(response);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //远程调用失败
                callback.onFailed("网络连接异常");
            }
        });
    }

    public static void request(String url, StringEntity entity, final Context context, final AbstractCallback callback){

        HttpClientUtil.post(context, url, entity, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                callback.onSuccess(response);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //远程调用失败
                callback.onFailed("网络连接异常");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //远程调用失败
                callback.onFailed("网络连接异常");
            }
        });
    }

    public static void download(String url, RequestParams params, final Context context, final AbstractCallback callback){

        String[] contentTypes={"application/vnd.android.package-archive;charset=utf-8"};
        HttpClientUtil.post(context, url, params, new BinaryHttpResponseHandler(contentTypes){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                callback.doSuccess(binaryData);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                //远程调用失败
                callback.onFailed("网络连接异常");
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                callback.doProgress(bytesWritten, totalSize);
            }
        });
    }

}
