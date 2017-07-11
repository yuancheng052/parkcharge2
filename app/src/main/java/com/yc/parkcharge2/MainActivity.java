package com.yc.parkcharge2;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.yc.parkcharge2.common.FragmentFactory;
import com.yc.parkcharge2.util.ScheduledServiceUtil;
import com.yc.parkcharge2.util.UserStoreUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.ScheduledExecutorService;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }

    @AfterViews
    final void initMainActivity() {
        bottomBar.setOnTabSelectListener(new OnTabSelectListener(){

            @Override
            public void onTabSelected(@IdRes int tabId) {
                int[] tabs = new int[]{
                        R.id.park,
                        R.id.charge,
                        R.id.my
                };

                for (int i = 0; i < tabs.length; ++i) {
                    if (tabs[i] == tabId) {
                        onNavigationDrawerItemSelected(i);
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        if(UserStoreUtil.getUserInfo(this.getApplicationContext())==null){
            //如果用户没登录，跳转到登录页面
            startActivity(new Intent(this, LoginActivity_.class));
        }else{
            //启动任务轮询
            ScheduledServiceUtil.execute();
        }
        super.onStart();
    }

    public void onNavigationDrawerItemSelected(int position) {
        int mSelectPos = position;
        Fragment fragment = null;
        switch (position) {
            case 0:
                //停车
                fragment = FragmentFactory.getParkFrag();
                break;
            case 1:
                //收费
                fragment = FragmentFactory.getParkQueryFrag();
                break;
            case 2:
                // 我的
                fragment = FragmentFactory.getMyFrag();
                break;
        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }
    }

    public void selectedTab(int index){
        bottomBar.selectTabWithId(index);
    }
}
