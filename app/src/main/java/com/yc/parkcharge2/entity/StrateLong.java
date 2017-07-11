package com.yc.parkcharge2.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 类描述：按时长收费，首小时xx元，次小时xx元
 */

@Entity
public class StrateLong {

    @Id
    private Long id;
    private double firstHHPrice;
    private double secondHHPrice;
    public double getSecondHHPrice() {
        return this.secondHHPrice;
    }
    public void setSecondHHPrice(double secondHHPrice) {
        this.secondHHPrice = secondHHPrice;
    }
    public double getFirstHHPrice() {
        return this.firstHHPrice;
    }
    public void setFirstHHPrice(double firstHHPrice) {
        this.firstHHPrice = firstHHPrice;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 896193329)
    public StrateLong(Long id, double firstHHPrice, double secondHHPrice) {
        this.id = id;
        this.firstHHPrice = firstHHPrice;
        this.secondHHPrice = secondHHPrice;
    }
    @Generated(hash = 1249695232)
    public StrateLong() {
    }

}
