package com.yc.parkcharge2.interfaces;

import org.json.JSONObject;

/**
 * Created by a on 2017/5/18.
 */

public interface CallbackInterface {

    public void onSuccess(JSONObject response);

    public void onFailed(String msg);
}
