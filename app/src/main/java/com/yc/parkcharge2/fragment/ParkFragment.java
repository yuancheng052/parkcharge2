package com.yc.parkcharge2.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yc.parkcharge2.MainActivity;
import com.yc.parkcharge2.MyApplication;
import com.yc.parkcharge2.R;
import com.yc.parkcharge2.common.Constants;
import com.yc.parkcharge2.common.FragmentFactory;
import com.yc.parkcharge2.entity.ParkRec;
import com.yc.parkcharge2.util.KeyboardUtil;
import com.yc.parkcharge2.util.UUIDGenerator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Date;
import java.util.UUID;

@EFragment(R.layout.frag_park)
public class ParkFragment extends Fragment {

    @ViewById
    EditText carNo;
    String pageName = "ParkFragment";
    KeyboardUtil keyboardUtil;
    public ParkFragment() {

    }

    @AfterViews
    public void init(){

        if(keyboardUtil == null){
            keyboardUtil = new KeyboardUtil(ParkFragment.this.getActivity(), carNo);
            keyboardUtil.hideSoftInputMethod();
            keyboardUtil.showKeyboard();
        }
        carNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                /*if(keyboardUtil == null){
                    keyboardUtil = new KeyboardUtil(ParkFragment.this.getActivity(), carNo);
                    keyboardUtil.hideSoftInputMethod();
                    keyboardUtil.showKeyboard();
                }else{
                    keyboardUtil.showKeyboard();
                }*/
                keyboardUtil.showKeyboard();
                return false;
            }
        });

    }

    /**
     * 方法描述：停车
     */
    @Click(R.id.btParkRecsave)
    public void save(){
        MobclickAgent.onEvent(this.getActivity(), "parkCar");
        String sCarNo = carNo.getText().toString();
        sCarNo = sCarNo.trim();
        if(!"".equals(sCarNo)){
            ParkRec parkRec = new ParkRec(null, sCarNo, Constants.CAR_TYPE_S, Constants.UNUPLOAD_SING,
                    UUIDGenerator.getUUID(),new Date());
            MyApplication.getInstances().getDaoSession().insert(parkRec);
            ((MainActivity)this.getActivity()).selectedTab(R.id.park);

            Toast.makeText(this.getActivity(), "停车保存成功！", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this.getActivity(), "车牌号不能为空！", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPageEnd(pageName);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onPageStart(pageName);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(keyboardUtil.isShow()){
                keyboardUtil.hideKeyboard();
            }else{
                //finish();
            }
        }
        return false;
    }

}
