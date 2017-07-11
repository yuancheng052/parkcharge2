package com.yc.parkcharge2.fragment;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yc.parkcharge2.R;
import com.yc.parkcharge2.common.BaseFragment;
import com.yc.parkcharge2.common.MyDataAdapter;
import com.yc.parkcharge2.util.DateTimeUtil;
import com.yc.parkcharge2.util.UserStoreUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：已收费数据统计（联网），统计范围：当前停车场所有已上传的收费数据
 */
@EFragment(R.layout.frag_charges_state_list)
public class ChargeStateListFragment extends BaseFragment {

    @ViewById
    TextView startTime;
    @ViewById
    TextView endTime;
    @ViewById(R.id.listviewChargesState)
    ListView charges;


    public ChargeStateListFragment() {
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
        RequestParams params = new RequestParams();
        params.put("parkId", UserStoreUtil.getUserInfo(this.getActivity()).getParkId());
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        String url = this.getString(R.string.server_url)+"chargeRec/state";
        this.request(url, params);
    }

    @Override
    protected void doSuccess() {
        try {
            JSONArray json = response.getJSONArray("data");
            if(json !=null && json.length()>0){
                List<Map<String, String>> recList = new ArrayList<Map<String, String>>();
                for(int i=0; i<json.length(); i++){
                    JSONObject obj = json.getJSONObject(i);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("name", obj.get("name").toString());        //收费员姓名
                    map.put("charges", obj.get("charges").toString());   //收费员收费费用
                    map.put("count", obj.get("num").toString());      //收费员收费次数
                    recList.add(map);
                }
                SimpleAdapter adapter = new MyDataAdapter(this.getActivity(), recList, R.layout.charges_state_list_item,
                        new String[]{"name","charges", "count"},new int[]{R.id.charges_state_name, R.id.charges_state_charges, R.id.charges_state_count});
                charges.setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                ChargeStateListFragment.this.startTime.setText(format(year, month+1, dayOfMonth));
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
                ChargeStateListFragment.this.endTime.setText(format(year, month+1, dayOfMonth));
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
