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
import com.yc.parkcharge2.gen.ChargeRecDao;
import com.yc.parkcharge2.gen.ParkRecDao;
import com.yc.parkcharge2.util.DateTimeUtil;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yc.parkcharge2.MyApplication.*;

@EFragment(R.layout.frag_park_list)
public class ParkListFragment extends Fragment {

    @ViewById
    EditText carNo;
    @ViewById(R.id.listviewCars)
    ListView cars;


    public ParkListFragment() {
        // Required empty public constructor
        //List<ParkRec> res = getInstances().getDaoSession().loadAll(ParkRec.class);
        //System.out.println(res);
    }

    @AfterViews
    public void init(){
        this.bindListView();
    }

    public void bindListView(){

        String sCarNo = carNo.getText().toString();
        List<ParkRec> recs ;
        if(sCarNo !=null && !"".equals(sCarNo)){
            recs = getInstances().getDaoSession().getParkRecDao().queryBuilder().where(ParkRecDao.Properties.CarNo.like("%"+sCarNo+"%")).list();
        }else{
            recs = getInstances().getDaoSession().loadAll(ParkRec.class);
        }
        List<Map<String, String>> recList = new ArrayList<Map<String, String>>();
        for(ParkRec rec : recs){
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", rec.getId().toString());
            map.put("carNo", rec.getCarNo());
            map.put("createTime", DateTimeUtil.formatDateTime(rec.getCreateTime()));
            recList.add(map);
        }
        SimpleAdapter adapter = new MyDataAdapter(this.getActivity(), recList, R.layout.car_list_item,
                new String[]{"carNo","createTime"},new int[]{R.id.rec_car_no, R.id.rec_crdtime});
        cars.setAdapter(adapter);
        //item 单击事件
        cars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //转入收费活动
                ListView lv = (ListView) parent;
                Map<String, String> rec = (Map<String, String>) lv.getItemAtPosition(position);

                Fragment fragment = FragmentFactory.getChargeFrag();
                Bundle bundle = new Bundle();
                bundle.putString("id", rec.get("id"));
                fragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
    }

    @AfterTextChange(R.id.carNo)
    public void textChanage(){
        this.bindListView();
    }

}
