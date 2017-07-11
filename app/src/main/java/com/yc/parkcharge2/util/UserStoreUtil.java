package com.yc.parkcharge2.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.yc.parkcharge2.LoginActivity_;
import com.yc.parkcharge2.R;
import com.yc.parkcharge2.common.Constants;
import com.yc.parkcharge2.entity.SysUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

/**
 * Created by a on 2017/5/7.
 */

public class UserStoreUtil {


    /**
     * 方法描述：存储用户信息
     * @param context
     * @param user
     */
    public static void saveUserInfo(Context context, JSONObject user){

        try {
            SharedPreferences setting = context.getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = setting.edit();
            /*editor.putString("id", user.get("id").toString());
            editor.putString("name", user.get("name").toString());
            editor.putString("phone", user.get("phone").toString());
            editor.putString("parkId", user.get("parkId").toString());
            JSONObject park = user.getJSONObject("park");
            editor.putString("parkName", park.get("name").toString());
            editor.commit();
            */
            PersistentCookieStore cookieStore = new PersistentCookieStore(context);
            BasicClientCookie newCookie = new BasicClientCookie("_user", user.toString());
            //过期时间24小时
            Date expireDate = new Date(new Date().getTime()+24*3600*1000);
            //Date expireDate = new Date(new Date().getTime()+60*1000);
            newCookie.setExpiryDate(expireDate);
            newCookie.setVersion(1);
            //newCookie.setDomain("mydomain.com");
            //newCookie.setPath("/");
            cookieStore.addCookie(newCookie);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 方法描述：获取用户信息
     * @param context
     * @return
     */
    public static SysUser getUserInfo(Context context){
        //SharedPreferences setting = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        List<Cookie> cookies = cookieStore.getCookies();
        JSONObject setting = null;
        SysUser user = null;
        try {
            for(Cookie cookie : cookies){
                if(!cookie.isExpired(new Date()) && "_user".equals(cookie.getName())){
                    setting = new JSONObject(cookie.getValue());
                }
            }
            if(setting !=null){
                user = new SysUser();
                String id = setting.getString("id");
                if(id ==null || "".equals(id)){
                    return null;
                }else{
                    user.setId(id);
                    user.setName(setting.getString("name"));
                    user.setPhone(setting.getString("phone"));
                    user.setParkId(setting.getString("parkId"));
                    user.setRole(setting.getString("role"));
                    JSONObject park = setting.getJSONObject("park");
                    user.setParkName(park.getString("name"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 方法描述：获取员工列表
     * @param context
     * @return
     */
    public static void getEmployees(Context context, final Handler handler){

        SysUser user = getUserInfo(context);
        String parkId = user.getParkId();

        RequestParams params = new RequestParams();
        params.put("parkId", parkId);
        String url = R.string.server_url+"sysUser/findEmployees";

        HttpClientUtil.post(context, url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String code = response.getString("state");
                    if("1".equals(code)){
                        JSONArray data = response.getJSONArray("users");

                        Message msg = new Message();
                        Bundle b = new Bundle();// 存放数据
                        b.putString("users",data.toString());
                        msg.setData(b);
                        handler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    /**
     * 方法描述：清除用户登录信息
     * @param context
     */
    public static void clearUserInfo(Context context){
        try {

            PersistentCookieStore cookieStore = new PersistentCookieStore(context);
            cookieStore.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isMgr(Context context) {

        SysUser user = getUserInfo(context);
        if(Constants.ROLE_MRG.equals(user.getRole())){
            return true;
        }
        return false;
    }
}
