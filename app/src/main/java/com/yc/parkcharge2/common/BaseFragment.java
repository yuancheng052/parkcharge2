package com.yc.parkcharge2.common;


import android.support.v4.app.Fragment;

import com.loopj.android.http.RequestParams;
import com.yc.parkcharge2.util.HttpRequestUtil;

import org.json.JSONObject;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by a on 2017/5/9.
 */

abstract  public class BaseFragment extends Fragment {

    //protected BaseFragHandler handler;
    public JSONObject response;


    protected void request(String url, RequestParams params){
        HttpRequestUtil.request(url, params, this.getActivity(), new AbstractCallback(this.getActivity()) {
            @Override
            public void doSuccess(JSONObject response) {
                BaseFragment.this.response = response;
                BaseFragment.this.doSuccess();
            }
        });
    }

    protected void callRemoteService(String url, StringEntity entity){
        HttpRequestUtil.request(url, entity, this.getActivity(), new AbstractCallback(this.getActivity()) {
            @Override
            public void doSuccess(JSONObject response) {
                BaseFragment.this.response = response;
                BaseFragment.this.doSuccess();
            }
        });
    }

    abstract  protected void doSuccess();
}
