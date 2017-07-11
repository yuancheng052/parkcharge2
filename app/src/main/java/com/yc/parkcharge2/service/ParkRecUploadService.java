package com.yc.parkcharge2.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.yc.parkcharge2.MyApplication;
import com.yc.parkcharge2.R;
import com.yc.parkcharge2.common.AbstractCallback;
import com.yc.parkcharge2.common.Constants;
import com.yc.parkcharge2.entity.ParkRec;
import com.yc.parkcharge2.entity.SysUser;
import com.yc.parkcharge2.fragment.MyFragment;
import com.yc.parkcharge2.gen.ChargeRecDao;
import com.yc.parkcharge2.gen.ParkRecDao;
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
public class ParkRecUploadService implements NetService{

   @Override
    public void execute(){

        try {
            final List<ParkRec> recs = getInstances().getDaoSession().getParkRecDao()
                    .queryBuilder().where(ParkRecDao.Properties.Sign.eq(Constants.UNUPLOAD_SING)).list();
            SysUser user = UserStoreUtil.getUserInfo(MyApplication.getInstances());
            JSONArray array = new JSONArray();
            if(recs !=null && recs.size()>0){
                for(int i=0; i<recs.size(); i++){
                    JSONObject json = new JSONObject();
                    ParkRec rec = recs.get(i);
                    json.put("carNo",rec.getCarNo());
                    json.put("carType", rec.getCarType());
                    json.put("startTime", DateTimeUtil.formatDateTime(rec.getCreateTime()));
                    json.put("recId", rec.getRecId());
                    json.put("parkId", user.getParkId());
                    json.put("userId", user.getId());
                    array.put(json);
                }
                String url = MyApplication.getInstances().getString(R.string.server_url)+"parkRec/upload";
                //this.callRemoteService(url, new StringEntity(array.toString(),"UTF-8"));
                HttpRequestUtil.request(url, new StringEntity(array.toString(),"UTF-8"), MyApplication.getInstances(), new AbstractCallback(MyApplication.getInstances()) {
                    @Override
                    public void doSuccess(JSONObject response) {
                        //未收费数据上传成功，置数据状为已上传
                        //List<ParkRec> recs = getInstances().getDaoSession().loadAll(ParkRec.class);
                        //getInstances().getDaoSession().getParkRecDao().deleteInTx(recs);
                        for(ParkRec rec : recs){
                            rec.setSign(Constants.UPLOAD_SIGN);
                            getInstances().getDaoSession().update(rec);
                        }
                        MsgUtil.showMsg("数据上传成功");
                    }
                });
            }else{
                MsgUtil.showMsg("没有数据需要上传！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
