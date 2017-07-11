package com.yc.parkcharge2.util;

import com.yc.parkcharge2.entity.Strate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *类描述：费用计算核心类【按不同时间段进行收费】
 * Created by a on 2016/6/9.
 */
public class ChargeComputCore {

    private List<Strate> strates;
    private ChargeCpuModel model = new ChargeCpuModel();

    public ChargeComputCore(List<Strate> strates){
        this.strates = strates;
    }

    /**
     * 计算总费用
     * @param startTime
     * @param endTime
     * @param strates
     * @return
     */
    public static ChargeCpuModel computeCharges(Date startTime, Date endTime, List<Strate> strates){

        ChargeComputCore cpu = new ChargeComputCore(strates);
        cpu.comput(startTime, endTime);
        return cpu.model;
    }


    public void comput(Date startTime, Date endTime){

        String startStr = DateTimeUtil.formatDate(startTime);
        //第一天最大时间，如果endTime小于最大时间 则不跨天
        Date tempEnd = DateTimeUtil.parseDateTime(startStr + " 23:59:59");
        if(endTime.compareTo(tempEnd) !=1){
            //不跨天情形
            for(int i=0; i<strates.size(); i++){
                Strate strate = strates.get(i);
                Date date1 = DateTimeUtil.parseDateTime(startStr+" "+strate.getStartTime());
                Date date2 = DateTimeUtil.parseDateTime(startStr+" "+strate.getEndTime());

                long temp = 0L;
                if(startTime.compareTo(date1)!=-1 && startTime.compareTo(date2)!=1 && endTime.compareTo(date2)!=1){
                    //开始日期在当前收费段内，结束日期也在当前收费段
                    temp = endTime.getTime()-startTime.getTime();
                }else if(startTime.compareTo(date1)!=-1 && startTime.compareTo(date2)!=1 && endTime.after(date2)){
                    //开始日期在当前收费段内，结束日期不在当前收费段
                    temp = date2.getTime()-startTime.getTime();
                }else if(startTime.before(date1) && endTime.compareTo(date1)!=-1 && endTime.compareTo(date2)!=1){
                    //开始日期不在当前收费段内（在收费段前），结束日期在收费段中
                    temp = endTime.getTime() - date1.getTime();
                }else if(startTime.before(date1) && endTime.after(date2)){
                    //开始日期不在当前收费段内（在收费段前），结束日期也不在收费段内（在收费段后）
                    temp = date2.getTime()- date1.getTime();
                }
                if(temp !=0L){
                    BigDecimal temp_charges = computeCharges(strate, temp);
                    model.addCharges(temp_charges);
                    model.addDetails(temp_charges+"  "+ startStr+" "+strate.getStartTime()+"至"+strate.getEndTime()+"");
                }
            }
        }else {
            //跨天情形
            Date newStart = new Date(tempEnd.getTime()+1000L);
            //计算第一天费用
            comput(startTime, tempEnd);
            //计算第2-n天费用
            comput(newStart, endTime);
        }
    }



    private static BigDecimal computeCharges(Strate strate, long duration){

        //基数（分钟）
        long radix = (long)(strate.getRatio()*60D) ;
        BigDecimal b_radix = new BigDecimal(radix);
        BigDecimal b_duration = new BigDecimal(duration);
        //long num= (duration/(1000*60))/radix;
        BigDecimal num = b_duration
                .divide(new BigDecimal(1000 * 60), 2, RoundingMode.HALF_UP)
                .divide(b_radix, 2, RoundingMode.HALF_UP);

        //double charges = num * strate.getPrice();
        BigDecimal charges = num.multiply(new BigDecimal(strate.getCharges())).setScale(2, RoundingMode.HALF_UP);
        return charges;

    }

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
