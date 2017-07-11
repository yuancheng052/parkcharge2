package com.yc.parkcharge2.calculate;

import android.widget.Toast;

import com.yc.parkcharge2.MyApplication;
import com.yc.parkcharge2.entity.StrateDays;
import com.yc.parkcharge2.entity.StrateLong;
import com.yc.parkcharge2.entity.StrateTimes;
import com.yc.parkcharge2.entity.StrateType;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by a on 2017/6/29.
 */

public class CaclulateModelFactory {

    private static Map<String, String> models = new HashMap<String, String>();
    static {
        models.put("1", CaclulateTimesModel.class.getName());
        models.put("2", CaclulateDaysModel.class.getName());
        models.put("3", CaclulateTimeLongModel.class.getName());
        models.put("4", CaclulateTimeSegmentModel.class.getName());
    }

    /**
     * 方法说明：计费入口
     * @param startTime
     * @param endTime
     * @return
     */
    public static AbstractCalculateModel.ChargeCpuModel computeCharges(Date startTime, Date endTime){

        String modelNo = "";
        try {
            List<StrateType> strateTypes = MyApplication.getInstances().getDaoSession().getStrateTypeDao().loadAll();
            if(strateTypes !=null &&  strateTypes.size()>0){
                modelNo = (""+strateTypes.get(0).getStrateType());
                AbstractCalculateModel model = (AbstractCalculateModel) Class.forName(models.get(modelNo)).newInstance();
                return model.comput(startTime, endTime);
            }else{
                throw new RuntimeException("请先设置收费方式");
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    //填充
    public static double get_price_times(){
        List<StrateTimes> strateTimesList = MyApplication.getInstances().getDaoSession().getStrateTimesDao().loadAll();
        if(strateTimesList !=null && strateTimesList.size()>0){
            StrateTimes strateTimes = strateTimesList.get(0);
            return strateTimes.getPrice();
        }
        return 0;
    }

    public static double get_price_days(){
        List<StrateDays> strateDaysList = MyApplication.getInstances().getDaoSession().getStrateDaysDao().loadAll();
        if(strateDaysList !=null && strateDaysList.size()>0){
            StrateDays strateDays = strateDaysList.get(0);
            return strateDays.getPrice();
        }
        return 0;
    }

    public static StrateLong get_price_along(){
        List<StrateLong> strateLongList = MyApplication.getInstances().getDaoSession().getStrateLongDao().loadAll();
        if(strateLongList !=null && strateLongList.size()>0){
            StrateLong strateLong = strateLongList.get(0);
            return strateLong;
        }
        return null;
    }
}
