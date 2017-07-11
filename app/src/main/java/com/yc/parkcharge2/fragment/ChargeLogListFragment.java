package com.yc.parkcharge2.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yc.parkcharge2.R;
import com.yc.parkcharge2.common.Constants;
import com.yc.parkcharge2.common.MyDataAdapter;
import com.yc.parkcharge2.entity.ChargeRec;
import com.yc.parkcharge2.gen.ChargeRecDao;
import com.yc.parkcharge2.util.DateTimeUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yc.parkcharge2.MyApplication.getInstances;

/**
 * 类描述：收费历史列表页面，排序：收费日期倒序
 */
@EFragment(R.layout.frag_charges_log_list)
public class ChargeLogListFragment extends Fragment {

    @ViewById
    TextView startTime;
    @ViewById
    TextView endTime;
    @ViewById(R.id.listviewCharges)
    ListView charges;


    public ChargeLogListFragment() {
        // Required empty public constructor
        //List<ParkRec> res = getInstances().getDaoSession().loadAll(ParkRec.class);
        //System.out.println(res);
    }

    @AfterViews
    public void init(){
        Date now = new Date();
        String startTime = DateTimeUtil.formatDate(now);
        String endTime = DateTimeUtil.formatDate(now);
        this.startTime.setText(startTime);
        this.endTime.setText(endTime);
        this.bindListView(startTime, endTime);

    }

    @Click(R.id.btQuery)
    public void query(){

        this.bindListView(this.startTime.getText().toString(), this.endTime.getText().toString());
    }
    public void bindListView(String startTime, String endTime){

        startTime+=" 00:00:00";
        endTime+=" 23:59:59";
        //List<ChargeRec> recs = getInstances().getDaoSession().queryRaw(ChargeRec.class," WHERE datetime(END_TIME) >=datetime('"+startTime+"') AND datetime(END_TIME) <datetime('"+endTime+"') ORDER BY END_TIME DESC,SIGN",
          //      new String[]{});
        QueryBuilder builder= getInstances().getDaoSession().getChargeRecDao().queryBuilder()
                .where(ChargeRecDao.Properties.EndTime.gt(DateTimeUtil.parseDateTime(startTime)))
                .where(ChargeRecDao.Properties.EndTime.lt(DateTimeUtil.parseDateTime(endTime)));
        List<ChargeRec> recs =builder.orderDesc(ChargeRecDao.Properties.EndTime).list();
        List<Map<String, String>> recList = new ArrayList<Map<String, String>>();
        for(ChargeRec rec : recs){
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", rec.getId().toString());
            map.put("carNo", rec.getCarNo());
            map.put("endTime", DateTimeUtil.formatDateTime(rec.getEndTime()));
            map.put("charges", ""+rec.getCharges());
            map.put("sign", rec.getSign());
            recList.add(map);
        }
        SimpleAdapter adapter = new MyDataAdapter(this.getActivity(), recList, R.layout.charges_list_item,
                new String[]{"carNo","endTime", "charges"},new int[]{R.id.rec_car_no, R.id.rec_end_time, R.id.rec_charges}){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if(!Constants.UPLOAD_SIGN.equals(((Map<String, String>)getItem(position)).get("sign"))){
                    view.setBackgroundColor((Color.parseColor(this.getContext().getString(R.string.row_red))));
                }
                return view;
            }
        };
        charges.setAdapter(adapter);
    }

    @Click(R.id.startTime)
    public void showStDialog(){

        final String startTime = this.startTime.getText().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateTimeUtil.parseDate(startTime));
        int year = calendar.get(Calendar.YEAR), month = calendar.get(Calendar.MONTH), day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ChargeLogListFragment.this.startTime.setText(format(year, month+1, dayOfMonth));
            }
        },  year, month, day).show();
    }

    @Click(R.id.endTime)
    public void showEdDialog(){

        final String endTime = this.endTime.getText().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateTimeUtil.parseDate(endTime));
        int year = calendar.get(Calendar.YEAR), month = calendar.get(Calendar.MONTH), day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ChargeLogListFragment.this.endTime.setText(format(year, month+1, dayOfMonth));
            }
        },  year, month, day).show();
    }

    private String format(int year, int month, int dayOfMonth){

        String res = ""+year;
        if(month<10){
            res +=("-0"+month);
        }else{
            res+=("-"+month);
        }
        if(dayOfMonth<10){
            res +=("-0"+dayOfMonth);
        }else{
            res +=("-"+dayOfMonth);
        }
        return res;
    }
}
