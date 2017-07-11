package com.yc.parkcharge2.common;

import android.content.Context;
import android.widget.Toast;

import com.yc.parkcharge2.interfaces.CallbackInterface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by a on 2017/5/18.
 */

public abstract class AbstractCallback implements CallbackInterface{

    //protected BaseFragHandler handler;
    private JSONObject response;

    private byte[] dataBytes;

    private Context context;

    public AbstractCallback(Context context){
        this.context = context;
    }

    @Override
    public void onSuccess(JSONObject response) {
        this.response = response;
        try {
            if(Constants.SUCCESS.equals(response.getString(Constants.STATUS))){
                this.doSuccess(response);
            }else{
                doFailed(response.getString(Constants.MSG));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(String msg) {
        this.doFailed(msg);
    }

    public abstract void doSuccess(JSONObject response);

    public  void doSuccess(byte[] bytes){

    }

    public void doProgress(long bytesWritten, long totalSize){

    }

    public void doFailed(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
