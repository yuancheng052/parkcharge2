package com.yc.parkcharge2.fragment;


import android.support.v4.app.Fragment;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yc.parkcharge2.MainActivity;
import com.yc.parkcharge2.MyApplication;
import com.yc.parkcharge2.R;
import com.yc.parkcharge2.calculate.AbstractCalculateModel;
import com.yc.parkcharge2.calculate.CaclulateModelFactory;
import com.yc.parkcharge2.common.Constants;
import com.yc.parkcharge2.common.FragmentFactory;
import com.yc.parkcharge2.entity.ChargeRec;
import com.yc.parkcharge2.entity.ParkRec;
import com.yc.parkcharge2.entity.Strate;
import com.yc.parkcharge2.util.ChargeComputCore;
import com.yc.parkcharge2.util.DateTimeUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Date;
import java.util.List;

/**
 * 类描述：收费
 */
@EFragment(R.layout.frag_charge)
public class ChargeFragment extends Fragment {

    @ViewById
    TextView carNo;
    @ViewById
    TextView startTime;
    @ViewById
    TextView parkCharges;
    @ViewById
    TextView chargesDetail;

    private double totalHours;    //停车总时长
    private double totalCharges;    //停车总费用

    private String pageName = "ChargeFragment";
    public ChargeFragment() {
        // Required empty public constructor
    }


    @AfterViews
    public void init(){

        String id = getArguments().getString("id");
        ParkRec parkRec = MyApplication.getInstances().getDaoSession().load(ParkRec.class, Long.parseLong(id));

        carNo.setText(parkRec.getCarNo());
        startTime.setText(DateTimeUtil.formatDateTime(parkRec.getCreateTime()));
        this.computeCharge(parkRec.getCreateTime(),"0");
    }

    /**
     * 方法描述：收费
     */
    @Click(R.id.btSaveCharge)
    public void save(){
        MobclickAgent.onEvent(this.getActivity(), "charge");
        //收费核心逻辑，插入费用统计表，删除停车记录
        String id = getArguments().getString("id");
        ParkRec parkRec = MyApplication.getInstances().getDaoSession().load(ParkRec.class, Long.parseLong(id));
        String sCarNo = carNo.getText().toString();
        //插入费用统计表
        ChargeRec chargeRec = new ChargeRec(null, sCarNo, 0, parkRec.getCreateTime(),new Date(),
                totalCharges, Constants.UNUPLOAD_SING, parkRec.getRecId());
        MyApplication.getInstances().getDaoSession().insert(chargeRec);
        //删除停车记录
        MyApplication.getInstances().getDaoSession().delete(parkRec);
        getFragmentManager().beginTransaction().replace(R.id.container, FragmentFactory.getParkQueryFrag()).commit();
        //((MainActivity)this.getActivity()).selectedTab(R.id.charge);
        Toast.makeText(this.getActivity(), "收费成功！", Toast.LENGTH_SHORT).show();
    }

    //计算总费用
    private double computeCharge(Date pStartTime, String carType){

        /*List<Strate> strates = MyApplication.getInstances().getDaoSession().queryRaw(Strate.class,
                "where car_type ="+carType);


        if(strates !=null){
            try{
                ChargeComputCore.ChargeCpuModel model = ChargeComputCore.computeCharges(pStartTime, new Date(), strates);
                parkCharges.setText(""+model.getCharges());
                chargesDetail.setText(model.getDetails());
                totalCharges = model.getCharges();
            }catch (Exception e){

                e.printStackTrace();
            }
        }*/
        try{
            AbstractCalculateModel.ChargeCpuModel model = CaclulateModelFactory.computeCharges(pStartTime, new Date());
            parkCharges.setText(""+model.getCharges());
            chargesDetail.setText(model.getDetails());
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this.getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return 0D;
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


}
