package com.yc.parkcharge2.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.yc.parkcharge2.MyApplication;
import com.yc.parkcharge2.R;
import com.yc.parkcharge2.common.FragmentFactory;
import com.yc.parkcharge2.entity.ParkRec;
import com.yc.parkcharge2.entity.Strate;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

@EFragment(R.layout.frag_strate)
public class StrateFragment extends Fragment {

    @ViewById
    TextView startTime;
    @ViewById
    TextView endTime;
    @ViewById
    EditText charges;
    @ViewById
    EditText ratio;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private String id;

    public StrateFragment() {

    }

    @AfterViews
    public void init(){
        Bundle bundle = this.getArguments();
        String startTime="00:00:00", endTime="00:00:00", charges = "2.0", ratio = "1.0";
        if(bundle !=null){
            String id = bundle.getString("id");
            if(id !=null && !"".equals(id)){
                this.id = id;
                Strate strate = MyApplication.getInstances().getDaoSession().load(Strate.class, Long.parseLong(id));
                startTime = strate.getStartTime();
                endTime = strate.getEndTime();
                charges = ""+strate.getCharges();
                ratio = ""+strate.getRatio();
            }
        }
        startHour = Integer.parseInt(startTime.split(":")[0]);
        startMinute = Integer.parseInt(startTime.split(":")[1]);
        endHour = Integer.parseInt(endTime.split(":")[0]);
        endMinute = Integer.parseInt(endTime.split(":")[1]);
        this.startTime.setText(startTime);
        this.endTime.setText(endTime);
        this.charges.setText(charges);
        this.ratio.setText(ratio);
    }

    @Click(R.id.startTime)
    public void showStDialog(){

        new TimePickerDialog(this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {

                startTime.setText(format(hour)+":"+format(minute)+":00");
            }
        }, startHour, startMinute, true).show();
    }

    @Click(R.id.endTime)
    public void showEdDialog(){

        new TimePickerDialog(this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                String seconds=":00";
                if(minute ==59){
                    seconds=":59";
                }
                endTime.setText(format(hour)+":"+format(minute)+ seconds);
            }
        }, endHour, endMinute, true).show();
    }

    @Click
    public void save(){
        Strate strate = new Strate(null, 0, startTime.getText().toString(), endTime.getText().toString(),
                Double.parseDouble(ratio.getText().toString()),
                Double.parseDouble(charges.getText().toString()));
        if(this.id ==null){
            //新增
            MyApplication.getInstances().getDaoSession().insert(strate);
        }else{
            //修改
            strate.setId(Long.parseLong(this.id));
            MyApplication.getInstances().getDaoSession().update(strate);
        }

        Toast.makeText(this.getActivity(), "收费标准保存成功！", Toast.LENGTH_SHORT).show();
    }

    @Click
    public void delete(){
        if(id !=null){
            MyApplication.getInstances().getDaoSession().getStrateDao().deleteByKey(Long.parseLong(id));
            Toast.makeText(this.getActivity(), "收费标准删除成功！", Toast.LENGTH_SHORT).show();
            getFragmentManager().beginTransaction().replace(R.id.container, FragmentFactory.getStrateListFrag()).commit();
        }else{
            Toast.makeText(this.getActivity(), "请选择要删除的记录！", Toast.LENGTH_SHORT).show();
        }
    }

    //返回
    @Click
    public void btReturn(){

        Fragment fragment = FragmentFactory.getStrateTypeFrag();
        Bundle bundle = new Bundle();
        bundle.putString("index", "4");
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    /**
     * 方法描述：格式化数字，返回两位数
     * @param hour
     * @return
     */
    private String format(int hour){
        if(hour<10){
            return "0"+hour;
        }else{
            return ""+hour;
        }
    }

}
