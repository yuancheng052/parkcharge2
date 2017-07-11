package com.yc.parkcharge2.calculate;

import com.yc.parkcharge2.entity.Strate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *类描述：费用计算核心类【按天收费】
 * Created by a on 2016/6/9.
 */
public class CaclulateDaysModel extends AbstractCalculateModel{



    @Override
    public ChargeCpuModel comput(Date startTime, Date endTime){

        double price = CaclulateModelFactory.get_price_days();    //单价
        long timeLong = endTime.getTime() - startTime.getTime();
        int days = (int) (timeLong/(1000*3600*24));

        BigDecimal charges = new BigDecimal(price).add(new BigDecimal(price).multiply(new BigDecimal(days)));

        model.addCharges(charges);
        return model;
    }

}
