package com.yc.parkcharge2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yc.parkcharge2.common.BaseActivity;
import com.yc.parkcharge2.common.Constants;
import com.yc.parkcharge2.common.FragmentFactory;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    @ViewById
    EditText phoneNum;
    @ViewById
    EditText parkName;

    @AfterViews
    final void initMainActivity() {
    }

    @Click(R.id.register)
    public void register(){

        String sPhoneNum = phoneNum.getText().toString();
        String sParkName = parkName.getText().toString();
        if(sPhoneNum.matches(Constants.phonePattern)){
            if(!"".equals(sParkName.trim())){
                RequestParams params = new RequestParams();
                params.put("name", sParkName);
                params.put("phone", sPhoneNum);
                String url = this.getString(R.string.server_url)+"carPark/insert";
                this.request(url, params);
            }else{
                Toast.makeText(this, "停车场名称不能为空", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void doSuccess() {
        String sPhoneNum = phoneNum.getText().toString();
        String sParkName = parkName.getText().toString();
        Intent intent = new Intent(this, LoginActivity_.class);
        Bundle bundle = new Bundle();
        bundle.putString("phone", sPhoneNum);
        bundle.putString("pwd", Constants.PWD);
        intent.putExtras(bundle);
        startActivity(intent);
        Toast.makeText(this, "用户注册成功，初始密码："+Constants.PWD, Toast.LENGTH_LONG).show();
    }

    //返回登录页面
    @Click(R.id.bt_return)
    public void back(){
        startActivity(new Intent(this, LoginActivity_.class));
    }
}
