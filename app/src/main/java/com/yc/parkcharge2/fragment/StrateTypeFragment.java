package com.yc.parkcharge2.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.yc.parkcharge2.MyApplication;
import com.yc.parkcharge2.R;
import com.yc.parkcharge2.calculate.CaclulateModelFactory;
import com.yc.parkcharge2.common.FragmentFactory;
import com.yc.parkcharge2.entity.Strate;
import com.yc.parkcharge2.entity.StrateDays;
import com.yc.parkcharge2.entity.StrateLong;
import com.yc.parkcharge2.entity.StrateTimes;
import com.yc.parkcharge2.entity.StrateType;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.frag_strate_type)
public class StrateTypeFragment extends Fragment {

    @ViewById
    LinearLayout layout_times;
    @ViewById
    LinearLayout layout_days;
    @ViewById
    LinearLayout layout_along;
    @ViewById
    LinearLayout layout_along2;
    @ViewById
    FrameLayout layout_segment;
    @ViewById
    EditText price_times;
    @ViewById
    EditText price_days;

    @ViewById
    EditText price_firstHH;
    @ViewById
    EditText price_secondHH;
    private int id;
    public StrateTypeFragment() {

    }

    @AfterViews
    public void init(){
        //初始化radio选中事件
        this.initEvent();

        Bundle bundle = this.getArguments();
        RadioGroup group = (RadioGroup)this.getActivity().findViewById(R.id.strate_type);
        //从时段（新增页面-保存）返回，页面默认选中时段
        if(bundle !=null){
            String index = bundle.getString("index");
            if(index !=null && !"".equals(index)){
                group.check(R.id.rb_segment);
                return ;
            }
        }
        //元素赋值
        init_checked();
    }

    //绑定事件
    private void initEvent(){

        RadioGroup group = (RadioGroup)this.getActivity().findViewById(R.id.strate_type);
        //绑定一个匿名监听器
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                layout_times.setVisibility(View.GONE);
                layout_days.setVisibility(View.GONE);
                layout_along.setVisibility(View.GONE);
                layout_along2.setVisibility(View.GONE);
                layout_segment.setVisibility(View.GONE);
                int radioButtonId = group.getCheckedRadioButtonId();
                StrateTypeFragment.this.id = radioButtonId;
                switch (id){
                    case R.id.rb_times:
                        layout_times.setVisibility(View.VISIBLE);
                        StrateTypeFragment.this.fill_price_times();
                        break;
                    case R.id.rb_days:
                        layout_days.setVisibility(View.VISIBLE);
                        StrateTypeFragment.this.fill_price_days();
                        break;
                    case R.id.rb_along:
                        layout_along.setVisibility(View.VISIBLE);
                        layout_along2.setVisibility(View.VISIBLE);
                        StrateTypeFragment.this.fill_price_along();
                        break;
                    case R.id.rb_segment:
                        layout_segment.setVisibility(View.VISIBLE);
                        getFragmentManager().beginTransaction().replace(R.id.layout_segment,
                                FragmentFactory.getStrateListFrag()).commit();
                        break;
                }
            }
        });
    }

    //初始化选中
    private void init_checked(){
        RadioGroup group = (RadioGroup)this.getActivity().findViewById(R.id.strate_type);
        //处理数据库存储的值
        List<StrateType> strateTypes = MyApplication.getInstances().getDaoSession().getStrateTypeDao().loadAll();
        if(strateTypes !=null &&  strateTypes.size()>0){
            StrateType type = strateTypes.get(0);
            switch (type.getStrateType()){
                case 1:
                    group.check(R.id.rb_times);
                    break;
                case 2:
                    group.check(R.id.rb_days);
                    break;
                case 3:
                    group.check(R.id.rb_along);
                    break;
                case 4:
                    group.check(R.id.rb_segment);
                    break;
            }
        }
    }


    @Click
    public void save(){

        int index = 1;
        switch (id) {
            case R.id.rb_times:
                MyApplication.getInstances().getDaoSession().deleteAll(StrateTimes.class);
                StrateTimes strate_times = new StrateTimes(null, new Double(price_times.getText().toString()));
                MyApplication.getInstances().getDaoSession().insert(strate_times);
                break;
            case R.id.rb_days:
                MyApplication.getInstances().getDaoSession().deleteAll(StrateDays.class);
                StrateDays strate_days = new StrateDays(null, new Double(price_days.getText().toString()));
                MyApplication.getInstances().getDaoSession().insert(strate_days);
                index = 2;
                break;
            case R.id.rb_along:
                MyApplication.getInstances().getDaoSession().deleteAll(StrateLong.class);
                StrateLong strate_long = new StrateLong(null, new Double(price_firstHH.getText().toString()),
                        new Double(price_secondHH.getText().toString()));
                MyApplication.getInstances().getDaoSession().insert(strate_long);
                index = 3;
                break;
            case R.id.rb_segment:
                MyApplication.getInstances().getDaoSession().deleteAll(StrateTimes.class);
                StrateTimes strate = new StrateTimes(null, new Double(price_times.getText().toString()));
                MyApplication.getInstances().getDaoSession().insert(strate);
                index = 4;
                break;
        }
        MyApplication.getInstances().getDaoSession().deleteAll(StrateType.class);
        StrateType strateType = new StrateType(null, index);
        MyApplication.getInstances().getDaoSession().insert(strateType);
        Toast.makeText(this.getActivity(), "收费方式保存成功！", Toast.LENGTH_SHORT).show();
    }

    //返回
    @Click
    public void btReturn(){
        getFragmentManager().beginTransaction().replace(R.id.container, FragmentFactory.getMyFrag()).commit();
    }

    //填充
    private void fill_price_times(){
        price_times.setText(""+ CaclulateModelFactory.get_price_times());
    }

    private void fill_price_days(){
        price_times.setText(""+ CaclulateModelFactory.get_price_days());
    }

    private void fill_price_along(){
        StrateLong along = CaclulateModelFactory.get_price_along();
        double fisrt_price = 0;
        double second_price = 0;
        if(along !=null){
            fisrt_price = along.getFirstHHPrice();
            second_price = along.getSecondHHPrice();
        }
        price_firstHH.setText(""+fisrt_price);
        price_secondHH.setText(""+second_price);
    }

}
