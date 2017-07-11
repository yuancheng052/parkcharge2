package com.yc.parkcharge2.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by a on 2017/3/29.
 */

@Entity
public class Strate {

    @Id
    private Long id;
    private Integer carType;
    private String startTime;
    private String endTime;
    private double ratio;
    private double charges;
    public double getCharges() {
        return this.charges;
    }
    public void setCharges(double charges) {
        this.charges = charges;
    }
    public double getRatio() {
        return this.ratio;
    }
    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
    public String getEndTime() {
        return this.endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getStartTime() {
        return this.startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public Integer getCarType() {
        return this.carType;
    }
    public void setCarType(Integer carType) {
        this.carType = carType;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1437286746)
    public Strate(Long id, Integer carType, String startTime, String endTime,
            double ratio, double charges) {
        this.id = id;
        this.carType = carType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ratio = ratio;
        this.charges = charges;
    }
    @Generated(hash = 304140772)
    public Strate() {
    }
}
