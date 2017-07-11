package com.yc.parkcharge2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import com.yc.parkcharge2.common.BaseActivity;
import com.yc.parkcharge2.util.UserStoreUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @ViewById
    EditText phoneNum;
    @ViewById
    EditText pwd;

    @AfterViews
    final void init() {
        Bundle bundle = this.getIntent().getExtras();
        if(bundle !=null){
            String phone = bundle.getString("phone");
            String pwd = bundle.getString("pwd");
            phoneNum.setText(phone);
            this.pwd.setText(pwd);
        }
    }

    @Click(R.id.login)
    public void login(){

        String sPhoneNum = phoneNum.getText().toString();
        String sPwd = pwd.getText().toString();
        RequestParams params = new RequestParams();
        params.put("phone", sPhoneNum);
        params.put("pwd", sPwd);
        String url = this.getString(R.string.server_url)+"sysUser/login";

        this.request(url, params);

    }

    @Override
    protected void doSuccess() {
        //存储用户信息
        try {
            JSONObject user = response.getJSONObject("data");
            UserStoreUtil.saveUserInfo(getApplicationContext(), user);
            MobclickAgent.onProfileSignIn(user.getString("id"));
            //跳转到主页面
            startActivity(new Intent(getApplicationContext(), MainActivity_.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Click(R.id.register)
    public void register(){
        startActivity(new Intent(this, RegisterActivity_.class));
    }
}
