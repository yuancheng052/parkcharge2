package com.yc.parkcharge2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yc.parkcharge2.R;
import com.yc.parkcharge2.common.BaseFragment;
import com.yc.parkcharge2.common.Constants;
import com.yc.parkcharge2.common.FragmentFactory;
import com.yc.parkcharge2.entity.SysUser;
import com.yc.parkcharge2.util.UserStoreUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.frag_employee)
public class EmployeeFragment extends BaseFragment {

    @ViewById
    EditText empName;
    @ViewById
    EditText empPhone;
    //操作标识，0：新增 1：修改，2：删除
    int operSign = 0;

    public EmployeeFragment() {

    }

    @AfterViews
    public void init(){
        Bundle bundle = this.getArguments();
        if(bundle !=null) {
            String id = bundle.getString("id");
            String phone = bundle.getString("phone");
            String name = bundle.getString("name");
            empName.setText(name);
            empPhone.setText(phone);
        }
    }

    @Click
    public void save(){
        String name = empName.getText().toString().trim();
        String phone = empPhone.getText().toString();

        if(phone.matches(Constants.phonePattern)){
            if(!"".equals(name)){
                RequestParams params = new RequestParams();
                params.put("name", name);
                params.put("phone", phone);
                SysUser user = UserStoreUtil.getUserInfo(this.getActivity());
                params.put("parkId", user.getParkId());
                String url = this.getString(R.string.server_url)+"sysUser/insert";
                Bundle bundle = this.getArguments();
                if(bundle !=null) {
                    String id = bundle.getString("id");
                    if (id != null && !"".equals(id)) {
                        //用户姓名修改
                        url = this.getString(R.string.server_url)+"sysUser/update";
                        params.put("id", id);
                        operSign = 1;
                    }else{
                        //用户新增
                    }
                }
                this.request(url, params);
            }else{
                Toast.makeText(this.getActivity(), "姓名不能为空", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this.getActivity(), "请输入正确的手机号", Toast.LENGTH_LONG).show();
        }

    }

    @Click
    public void del(){
        RequestParams params = new RequestParams();
        Bundle bundle = this.getArguments();
        if(bundle !=null) {
            String id = bundle.getString("id");
            String url = this.getString(R.string.server_url)+"sysUser/delete";
            params.put("id", id);
            operSign = 2;
            this.request(url, params);
        }
    }

    @Override
    protected void doSuccess() {

        String msg = "";
        if(0 == operSign){
            //人员新增
            msg = "人员新增成功，初始密码0000";
        }else if(1 == operSign){
            //人员修改
            msg = "人员修改成功";
        }else{
            //人员删除
            msg = "人员删除成功";
        }
        Fragment fragment = FragmentFactory.getEmpListFrag();
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        Toast.makeText(this.getActivity(), msg, Toast.LENGTH_LONG).show();
    }


    @Click
    public void close(){
        Fragment fragment = FragmentFactory.getEmpListFrag();
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
