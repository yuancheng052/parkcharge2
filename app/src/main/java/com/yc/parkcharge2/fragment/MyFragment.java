package com.yc.parkcharge2.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import com.yc.parkcharge2.LoginActivity_;
import com.yc.parkcharge2.MyApplication;
import com.yc.parkcharge2.R;
import com.yc.parkcharge2.common.AbstractCallback;
import com.yc.parkcharge2.common.BaseFragment;
import com.yc.parkcharge2.common.Constants;
import com.yc.parkcharge2.common.FragmentFactory;
import com.yc.parkcharge2.entity.ChargeRec;
import com.yc.parkcharge2.entity.ParkRec;
import com.yc.parkcharge2.entity.SysUser;
import com.yc.parkcharge2.gen.ChargeRecDao;
import com.yc.parkcharge2.service.ChargesRecUploadService;
import com.yc.parkcharge2.service.FileDownloadService;
import com.yc.parkcharge2.service.ParkRecDownloadService;
import com.yc.parkcharge2.service.ParkRecUploadService;
import com.yc.parkcharge2.util.DateTimeUtil;
import com.yc.parkcharge2.util.HttpRequestUtil;
import com.yc.parkcharge2.util.UserStoreUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.entity.StringEntity;

import static com.yc.parkcharge2.MyApplication.getInstances;

/**
 * 类描述：我的设置
 */
@EFragment(R.layout.frag_my)
public class MyFragment extends Fragment {

    //操作标识，0:未收费数据上传;1:未收费数据下载;2:已收费数据上传
    private String operSign = "0";
    @ViewById
    Button btChargeLogNet;
    @ViewById
    Button btEmpList;

    public MyFragment() {

    }

    @AfterViews
    public void init(){
        if(!UserStoreUtil.isMgr(this.getActivity())){
            //非管理员，隐藏功能模块： 费用统计、收费员管理
            btChargeLogNet.setVisibility(View.INVISIBLE);
            btEmpList.setVisibility(View.INVISIBLE);
        }
    }

    @Click(R.id.btStrate)
    public void strateList(){
        //Fragment fragment = FragmentFactory.getStrateListFrag();
        getFragmentManager().beginTransaction().replace(R.id.container,
                FragmentFactory.getStrateTypeFrag()).commit();
    }
    //停车记录上传
    @Click(R.id.btUpParkRec)
    public void btUpParkRec(){
        new ParkRecUploadService().execute();
    }
    //停车记录下载
    @Click(R.id.btDownParkRec)
    public void btDownParkRec(){
        new ParkRecDownloadService().execute();
    }
    //收费记录
    @Click(R.id.btChargeLog)
    public void btChargeLog(){
        getFragmentManager().beginTransaction().replace(R.id.container, FragmentFactory.getChargesLogFrag()).commit();
    }
    //收费记录统计（联网）
    @Click(R.id.btChargeLogNet)
    public void btChargeLogNet(){
        getFragmentManager().beginTransaction().replace(R.id.container, FragmentFactory.getChargesStateFrag()).commit();
    }
    //收费人员管理
    @Click(R.id.btEmpList)
    public void btEmpList(){
        getFragmentManager().beginTransaction().replace(R.id.container, FragmentFactory.getEmpListFrag()).commit();
    }
    //已收费数据（统计数据）上传
    @Click(R.id.btUpState)
    public void uploadState(){
        new ChargesRecUploadService().execute();
    }
    //密码修改
    @Click(R.id.btPwd)
    public void btPwd(){
        getFragmentManager().beginTransaction().replace(R.id.container, FragmentFactory.getUserPwdFrag()).commit();
    }

    //系统信息
    @Click(R.id.btSysInfo)
    public void btSysInfo(){
        getFragmentManager().beginTransaction().replace(R.id.container, FragmentFactory.getSysInfoFrag()).commit();
    }

    //退出
    @Click(R.id.btExit)
    public void btExit(){
        UserStoreUtil.clearUserInfo(this.getActivity());
        MobclickAgent.onProfileSignOff();
        //跳转到登录页面
        startActivity(new Intent(this.getActivity(), LoginActivity_.class));
    }

}
