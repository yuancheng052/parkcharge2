package com.yc.parkcharge2.fragment;

import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yc.parkcharge2.R;
import com.yc.parkcharge2.common.BaseFragment;
import com.yc.parkcharge2.common.FragmentFactory;
import com.yc.parkcharge2.entity.SysUser;
import com.yc.parkcharge2.util.UserStoreUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.frag_user_pwd)
public class UserPwdFragment extends BaseFragment {

    @ViewById
    EditText oldPwd;
    @ViewById
    EditText newPwd;
    @ViewById
    EditText newPwd2;

    public UserPwdFragment() {

    }

    @AfterViews
    public void init(){

    }

    @Click
    public void save(){
        String oldPwd = this.oldPwd.getText().toString();
        String newPwd = this.newPwd.getText().toString();
        String newPwd2 = this.newPwd2.getText().toString();
        boolean flag = true;
        String msg = "";
        if(oldPwd==null || "".equals(oldPwd)){
            flag = false;
            msg = "旧密码不能为空！";
        }else  if(newPwd==null || "".equals(newPwd)){
            flag = false;
            msg = "新密码不能为空！";
        }else  if(newPwd2==null || "".equals(newPwd2)){
            flag = false;
            msg = "新密码不能为空！";
        }else  if(!newPwd.equals(newPwd2)){
            flag = false;
            msg = "再次输入密码不一致！";
        }else  if(newPwd.equals(oldPwd)){
            flag = false;
            msg = "新密码不能与旧密码相同！";
        }
        if(flag){
            RequestParams params = new RequestParams();
            SysUser user = UserStoreUtil.getUserInfo(this.getActivity());
            params.put("id", user.getId());
            params.put("oldPwd", oldPwd);
            params.put("newPwd", newPwd);
            String url = this.getString(R.string.server_url)+"sysUser/updatePwd";

            this.request(url, params);
        }else{
            Toast.makeText(this.getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void doSuccess() {

        String msg = "密码修改成功";
        Fragment fragment = FragmentFactory.getEmpListFrag();
        Toast.makeText(this.getActivity(), msg, Toast.LENGTH_SHORT).show();
        getFragmentManager().beginTransaction().replace(R.id.container, FragmentFactory.getMyFrag()).commit();
    }

    @Click
    public void btReturn(){
        getFragmentManager().beginTransaction().replace(R.id.container, FragmentFactory.getMyFrag()).commit();
    }
}
