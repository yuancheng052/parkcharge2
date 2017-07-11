package com.yc.parkcharge2.calculate;

import com.yc.parkcharge2.entity.Strate;
import com.yc.parkcharge2.entity.StrateLong;
import com.yc.parkcharge2.util.DateTimeUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *类描述：费用计算核心类【按时长进行收费】，首小时XX元，次小时XX元
 * Created by a on 2016/6/9.
 */
public class CaclulateTimeLongModel extends AbstractCalculateModel{

    @Override
    public AbstractCalculateModel.ChargeCpuModel comput(Date startTime, Date endTime){

        double firstHHPrice = 0;    //首小时单价
        double secondHHPrice = 0;   //次小时单价
        StrateLong along = CaclulateModelFactory.get_price_along();
        if(along !=null){
            firstHHPrice = along.getFirstHHPrice();
            secondHHPrice = along.getSecondHHPrice();
        }
        long timeLong = endTime.getTime() - startTime.getTime();
        int hours = (int) (timeLong/(1000*3600));

        BigDecimal charges = new BigDecimal(firstHHPrice).add(new BigDecimal(secondHHPrice).multiply(new BigDecimal(hours)));

        model.addCharges(charges);
        return model;
    }

}
