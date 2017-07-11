package com.yc.parkcharge2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.yc.parkcharge2.R;
import com.yc.parkcharge2.common.FragmentFactory;
import com.yc.parkcharge2.common.MyDataAdapter;
import com.yc.parkcharge2.entity.ParkRec;
import com.yc.parkcharge2.entity.Strate;
import com.yc.parkcharge2.util.DateTimeUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yc.parkcharge2.MyApplication.getInstances;
import static com.yc.parkcharge2.R.id.carNo;

/**
 * 类描述：收费标准列表
 */
@EFragment(R.layout.frag_strate_list)
public class StrateListFragment extends Fragment {


    @ViewById(R.id.listviewStrates)
    ListView strates;


    public StrateListFragment() {
        // Required empty public constructor
        //List<ParkRec> res = getInstances().getDaoSession().loadAll(ParkRec.class);
        //System.out.println(res);
    }

    @AfterViews
    public void init(){
        this.bindListView();

    }
    public void bindListView(){

        List<Strate> recs = getInstances().getDaoSession().loadAll(Strate.class);
        List<Map<String, String>> recList = new ArrayList<Map<String, String>>();
        for(Strate strate : recs){
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", strate.getId().toString());
            map.put("startTime", strate.getStartTime()+"至"+strate.getEndTime());
            map.put("charges", ""+strate.getCharges()+"/"+strate.getRatio());
            recList.add(map);
        }
        SimpleAdapter adapter = new MyDataAdapter(this.getActivity(), recList, R.layout.strate_item,
                new String[]{"startTime","charges"},new int[]{R.id.startTime, R.id.charges});
        strates.setAdapter(adapter);
        //item 单击事件
        strates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //转入收费标准维护
                ListView lv = (ListView) parent;
                Map<String, String> rec = (Map<String, String>) lv.getItemAtPosition(position);

                Fragment fragment = FragmentFactory.getStrateFrag();
                Bundle bundle = new Bundle();
                bundle.putString("id", rec.get("id"));
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
    }

    /**
     * 方法描述：新增收费标准
     */
    @Click
    public void addStrate(){

        Fragment fragment = FragmentFactory.getStrateFrag();
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

}
