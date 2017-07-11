package com.yc.parkcharge2.common;

import android.support.v4.app.Fragment;

import com.yc.parkcharge2.fragment.ChargeFragment_;
import com.yc.parkcharge2.fragment.ChargeLogListFragment_;
import com.yc.parkcharge2.fragment.ChargeStateListFragment_;
import com.yc.parkcharge2.fragment.DownloadFragment;
import com.yc.parkcharge2.fragment.DownloadFragment_;
import com.yc.parkcharge2.fragment.EmployeeFragment_;
import com.yc.parkcharge2.fragment.EmployeeListFragment_;
import com.yc.parkcharge2.fragment.MyFragment_;
import com.yc.parkcharge2.fragment.ParkFragment_;
import com.yc.parkcharge2.fragment.ParkListFragment_;
import com.yc.parkcharge2.fragment.StrateFragment_;
import com.yc.parkcharge2.fragment.StrateListFragment_;
import com.yc.parkcharge2.fragment.StrateTypeFragment_;
import com.yc.parkcharge2.fragment.SysInfoFragment;
import com.yc.parkcharge2.fragment.SysInfoFragment_;
import com.yc.parkcharge2.fragment.UserPwdFragment_;

/**
 * Created by a on 2017/3/29.
 */

public class FragmentFactory {


    //停车查询
    public static Fragment getParkQueryFrag(){
        return new ParkListFragment_();
    }
    //停车
    public static Fragment getParkFrag(){
        return new ParkFragment_();
    }
    //收费
    public static Fragment getChargeFrag(){
        return new ChargeFragment_();
    }
    //我的
    public static Fragment getMyFrag(){
        return new MyFragment_();
    }
    //费用标准列表
    public static Fragment getStrateListFrag(){
        return new StrateListFragment_();
    }
    //收费方式设置
    public static Fragment getStrateTypeFrag(){
        return new StrateTypeFragment_();
    }
    //费用新增/修改
    public static Fragment getStrateFrag(){
        return new StrateFragment_();
    }
    //雇员列表
    public static Fragment getEmpListFrag(){
        return new EmployeeListFragment_();
    }
    //雇员新增
    public static Fragment getEmpFrag(){
        return new EmployeeFragment_();
    }
    //收费记录列表
    public static Fragment getChargesLogFrag(){
        return new ChargeLogListFragment_();
    }
    //收费记录统计（联网）
    public static Fragment getChargesStateFrag(){
        return new ChargeStateListFragment_();
    }
    //修改密码
    public static Fragment getUserPwdFrag(){
        return new UserPwdFragment_();
    }
    //文件下载
    public static Fragment getDownloadFrag(){
        return new DownloadFragment_();
    }
    //系统信息
    public static Fragment getSysInfoFrag(){
        return new SysInfoFragment_();
    }

}
