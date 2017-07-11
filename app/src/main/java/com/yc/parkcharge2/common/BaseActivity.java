package com.yc.parkcharge2.common;


import android.support.v7.app.AppCompatActivity;

import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import com.yc.parkcharge2.util.HttpRequestUtil;

import org.json.JSONObject;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by a on 2017/5/9.
 */

abstract  public class BaseActivity extends AppCompatActivity {

    public JSONObject response;
    protected  String pageName = "base";

    protected void request(String url, RequestParams params){
        HttpRequestUtil.request(url, params, this, new AbstractCallback(this) {
            @Override
            public void doSuccess(JSONObject response) {
                BaseActivity.this.response = response;
                BaseActivity.this.doSuccess();
            }
        });
    }

    protected void request(String url, StringEntity entity){
        HttpRequestUtil.request(url, entity, this, new AbstractCallback(this) {
            @Override
            public void doSuccess(JSONObject response) {
                BaseActivity.this.response = response;
                BaseActivity.this.doSuccess();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(pageName);
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(pageName);
        MobclickAgent.onPause(this);
    }


    abstract  protected void doSuccess();


}
