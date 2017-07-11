package com.yc.parkcharge2.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.umeng.analytics.MobclickAgent;
import com.yc.parkcharge2.LoginActivity_;
import com.yc.parkcharge2.MainActivity;
import com.yc.parkcharge2.R;
import com.yc.parkcharge2.common.BaseFragment;
import com.yc.parkcharge2.common.FragmentFactory;
import com.yc.parkcharge2.service.ChargesRecUploadService;
import com.yc.parkcharge2.service.ParkRecDownloadService;
import com.yc.parkcharge2.service.ParkRecUploadService;
import com.yc.parkcharge2.util.UserStoreUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 类描述：系统信息
 */
@EFragment(R.layout.frag_sys_info)
public class SysInfoFragment extends Fragment {



    public SysInfoFragment() {

    }

    @AfterViews
    public void init(){

    }
    //版本更新
    @Click(R.id.btUpdate)
    public void btUpdate(){
        getFragmentManager().beginTransaction().replace(R.id.container, FragmentFactory.getDownloadFrag()).commit();
    }

    //保存二维码
    public void save(){
        UserStoreUtil.clearUserInfo(this.getActivity());
        MobclickAgent.onProfileSignOff();
        //跳转到登录页面
        startActivity(new Intent(this.getActivity(), LoginActivity_.class));
    }
    //返回
    @Click(R.id.btReturn)
    public void btReturn(){
        getFragmentManager().beginTransaction().replace(R.id.container, FragmentFactory.getMyFrag()).commit();
    }

}
