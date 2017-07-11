package com.yc.parkcharge2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yc.parkcharge2.R;
import com.yc.parkcharge2.common.BaseFragment;
import com.yc.parkcharge2.common.FragmentFactory;
import com.yc.parkcharge2.common.MyDataAdapter;
import com.yc.parkcharge2.util.UserStoreUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工列表
 */
@EFragment(R.layout.frag_employee_list)
public class EmployeeListFragment extends BaseFragment {

    @ViewById(R.id.listviewEmployees)
    ListView employees;

    public EmployeeListFragment() {
    }

    @AfterViews
    public void init(){
        this.bindListView();
    }
    public void bindListView(){

        RequestParams params = new RequestParams();
        params.put("parkId", UserStoreUtil.getUserInfo(this.getActivity()).getParkId());
        String url = this.getString(R.string.server_url)+"sysUser/findEmployees";
        this.request(url, params);

    }

    @Click(R.id.emp_add)
    //增加员工
    public void empAdd(){
        Fragment fragment = FragmentFactory.getEmpFrag();
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
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
                    map.put("id", obj.get("id").toString());
                    map.put("name", obj.get("name").toString());
                    map.put("phone", obj.get("phone").toString());
                    recList.add(map);
                }
                SimpleAdapter adapter = new MyDataAdapter(this.getActivity(), recList, R.layout.employee_list_item,
                        new String[]{"name","phone"},new int[]{R.id.rec_emp_name, R.id.rec_emp_phone});
                employees.setAdapter(adapter);
                //item 单击事件
                employees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //人员修改页面
                        ListView lv = (ListView) parent;
                        Map<String, String> rec = (Map<String, String>) lv.getItemAtPosition(position);

                        Fragment fragment = FragmentFactory.getEmpFrag();
                        Bundle bundle = new Bundle();
                        bundle.putString("id", rec.get("id"));
                        bundle.putString("phone", rec.get("phone"));
                        bundle.putString("name", rec.get("name"));
                        fragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //返回
    @Click
    public void btReturn(){
        getFragmentManager().beginTransaction().replace(R.id.container, FragmentFactory.getMyFrag()).commit();
    }

}
