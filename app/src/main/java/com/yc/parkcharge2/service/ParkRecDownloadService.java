package com.yc.parkcharge2.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yc.parkcharge2.MyApplication;
import com.yc.parkcharge2.R;
import com.yc.parkcharge2.common.AbstractCallback;
import com.yc.parkcharge2.common.Constants;
import com.yc.parkcharge2.entity.ChargeRec;
import com.yc.parkcharge2.entity.ParkRec;
import com.yc.parkcharge2.entity.SysUser;
import com.yc.parkcharge2.gen.ChargeRecDao;
import com.yc.parkcharge2.gen.ParkRecDao;
import com.yc.parkcharge2.util.HttpRequestUtil;
import com.yc.parkcharge2.util.MsgUtil;
import com.yc.parkcharge2.util.UserStoreUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.yc.parkcharge2.MyApplication.getInstances;

/**
 * 类描述：停车数据下载，下载后，插入数据库里要判断数据是否有重复
 */
public class ParkRecDownloadService implements NetService{

    @Override
    public void execute(){
        try {
            RequestParams params = new RequestParams();
            SysUser user = UserStoreUtil.getUserInfo(MyApplication.getInstances());
            params.put("parkId", user.getParkId());
            String url = MyApplication.getInstances().getString(R.string.server_url) + "parkRec/downLoad";
            //this.request(url, params);
            HttpRequestUtil.request(url, params, MyApplication.getInstances(), new AbstractCallback(MyApplication.getInstances()) {
                @Override
                public void doSuccess(JSONObject response) {
                    //数据下载成功，插入数据到数据库中，插入前要判断数据库中是否已存在此条记录，如果已存在则忽略
                    //如果当前已上传的记录在 response中不存在，则删除此条记录
                    try {
                        JSONArray recs = response.getJSONArray(Constants.DATA);
                        if (recs != null && recs.length() > 0) {
                            //查询已存在的停车记录，放入map中，key=recId
                            //客户端停车数据
                            List<ParkRec> cRecs = getInstances().getDaoSession().getParkRecDao()
                                    .queryBuilder().where(ParkRecDao.Properties.Sign.eq(Constants.UPLOAD_SIGN)).list();
                            //客户端收费数据
                            List<ChargeRec> chargeRecs = getInstances().getDaoSession().getChargeRecDao().loadAll();
                            Map<String, ChargeRec> chargeMap = new HashMap<String, ChargeRec>();
                            if(chargeRecs !=null){
                                for(ChargeRec rec : chargeRecs){
                                    chargeMap.put(rec.getParkRecId(), rec);
                                }
                            }
                            Map<String, ParkRec> cmap = new HashMap<String, ParkRec>();
                            Map<String, ParkRec> smap = new HashMap<String, ParkRec>();
                            if (cRecs != null) {
                                for (ParkRec rec : cRecs) {
                                    cmap.put(rec.getRecId(), rec);
                                }
                            }
                            //将服务器端 数据插入本地数据库
                            for (int i = 0; i < recs.length(); i++) {
                                JSONObject rec = recs.getJSONObject(i);
                                String recId = rec.getString("recId");
                                smap.put(recId, new ParkRec());
                                if (cmap.get(recId) == null && chargeMap.get(recId) ==null) {
                                    ParkRec parkRec = new ParkRec(null, rec.getString("carNo"), rec.getInt("carType"),
                                            Constants.UPLOAD_SIGN, recId,
                                            new Date(rec.getLong("startTime")));
                                    getInstances().getDaoSession().insert(parkRec);
                                }
                            }
                            //删除冗余数据：如果已上传的数据在服务端不存在，刚删除本地数据
                            for (ParkRec rec : cRecs) {
                                if (smap.get(rec.getRecId()) == null) {
                                    getInstances().getDaoSession().delete(rec);
                                }
                            }
                            MsgUtil.showMsg("数据下载成功");
                        } else {
                            MsgUtil.showMsg("无下载数据，当前已经是最新");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
