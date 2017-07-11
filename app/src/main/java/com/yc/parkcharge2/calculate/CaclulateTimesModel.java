package com.yc.parkcharge2.calculate;

import com.yc.parkcharge2.MyApplication;
import com.yc.parkcharge2.entity.Strate;
import com.yc.parkcharge2.entity.StrateTimes;
import com.yc.parkcharge2.util.DateTimeUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *类描述：费用计算核心类【按次收费--每次费用固定】
 * Created by a on 2016/6/9.
 */
public class CaclulateTimesModel extends AbstractCalculateModel{



    @Override
    public ChargeCpuModel comput(Date startTime, Date endTime){

        double price = CaclulateModelFactory.get_price_times();
        BigDecimal charges = new BigDecimal(price);

        model.addCharges(charges);
        return model;
    }

}
