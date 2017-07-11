package com.yc.parkcharge2.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.yc.parkcharge2.MyApplication;
import com.yc.parkcharge2.R;
import com.yc.parkcharge2.common.AbstractCallback;
import com.yc.parkcharge2.common.Constants;
import com.yc.parkcharge2.entity.ChargeRec;
import com.yc.parkcharge2.entity.ParkRec;
import com.yc.parkcharge2.entity.SysUser;
import com.yc.parkcharge2.fragment.MyFragment;
import com.yc.parkcharge2.gen.ChargeRecDao;
import com.yc.parkcharge2.util.DateTimeUtil;
import com.yc.parkcharge2.util.HttpRequestUtil;
import com.yc.parkcharge2.util.MsgUtil;
import com.yc.parkcharge2.util.UserStoreUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.entity.StringEntity;

import static com.yc.parkcharge2.MyApplication.getInstances;

/**
 * 类描述：停车数据上传，注：只上传（未上传）
 */
public class ChargesRecUploadService implements NetService{


    @Override
    public void execute(){

        try {
            List<ChargeRec> recs = getInstances().getDaoSession().getChargeRecDao()
                    .queryBuilder().where(ChargeRecDao.Properties.Sign.eq(Constants.UNUPLOAD_SING)).list();
            SysUser user = UserStoreUtil.getUserInfo(MyApplication.getInstances());
            JSONArray array = new JSONArray();
            if(recs !=null && recs.size()>0){
                for(int i=0; i<recs.size(); i++){
                    JSONObject json = new JSONObject();
                    ChargeRec rec = recs.get(i);
                    json.put("id",rec.getId());
                    json.put("carNo",rec.getCarNo());
                    json.put("carType", rec.getCarType());
                    json.put("startTime", DateTimeUtil.formatDateTime(rec.getStartTime()));
                    json.put("endTime", DateTimeUtil.formatDateTime(rec.getEndTime()));
                    json.put("charges", rec.getCharges());
                    json.put("userId", user.getId());
                    json.put("parkId", user.getParkId());
                    json.put("parkRecId", rec.getParkRecId());
                    array.put(json);
                }
                String url = MyApplication.getInstances().getString(R.string.server_url)+"chargeRec/upload";
                //this.callRemoteService(url, new StringEntity(array.toString(),"UTF-8"));
                HttpRequestUtil.request(url, new StringEntity(array.toString(),"UTF-8"), MyApplication.getInstances(), new AbstractCallback(MyApplication.getInstances()) {
                    @Override
                    public void doSuccess(JSONObject response) {
                        //已收费数据上传，上传成功后，更新收费记录为已上传
                        try {
                            JSONArray recs = response.getJSONArray(Constants.DATA);
                            if(recs !=null && recs.length()>0){
                                for(int i=0; i<recs.length(); i++){
                                    int id = recs.getInt(i);
                                    //更新数据为已上传
                                    ChargeRec chargeRec = getInstances().getDaoSession().getChargeRecDao().load(new Long(id));
                                    chargeRec.setSign("1");
                                    getInstances().getDaoSession().update(chargeRec);
                                }
                                MsgUtil.showMsg("数据上传成功");
                            }else{
                                MsgUtil.showMsg("数据上传失败，原因：服务端保存失败!");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }else{
                //Toast.makeText(MyApplication.getInstances(), "没有数据需要上传！", Toast.LENGTH_SHORT).show();
                MsgUtil.showMsg("没有数据需要上传!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
