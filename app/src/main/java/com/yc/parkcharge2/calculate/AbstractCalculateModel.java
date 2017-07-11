package com.yc.parkcharge2.calculate;

import com.yc.parkcharge2.entity.Strate;
import com.yc.parkcharge2.util.DateTimeUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *类描述：费用计算抽象类
 * Created by a on 2016/6/9.
 */
public abstract class AbstractCalculateModel {

    protected ChargeCpuModel model = new ChargeCpuModel();

    abstract public  ChargeCpuModel comput(Date startTime, Date endTime);

    public class ChargeCpuModel {

        private BigDecimal charges = new BigDecimal(0);
        private List<String> details = new ArrayList<String>();

        public void addCharges(double pCharges){
            charges.add(new BigDecimal(pCharges));
        }

        public double getCharges(){
            return charges.doubleValue();
        }


        public String getDetails(){
            String msg = "";
            for(String detail : details){
                msg+=(detail+"\n");
            }
            return msg;
        }

        public void addCharges(BigDecimal pCharges){
            charges = charges.add(pCharges);
        }

        public void addDetails(String detail){
            details.add(detail);
        }

    }


}
