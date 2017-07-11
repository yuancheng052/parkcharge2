package com.yc.parkcharge2.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import java.util.Date;

/**
 * Created by a on 2017/3/29.
 */
@Entity
public class ChargeRec {

    @Id
    private Long id;
    private String carNo;
    private Integer carType;
    private java.util.Date startTime;
    private java.util.Date endTime;
    private double charges;
    //上传标识，0：未上传，1：已上传
    private String sign;
    private String parkRecId;
    public String getParkRecId() {
        return this.parkRecId;
    }
    public void setParkRecId(String parkRecId) {
        this.parkRecId = parkRecId;
    }
    public String getSign() {
        return this.sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public double getCharges() {
        return this.charges;
    }
    public void setCharges(double charges) {
        this.charges = charges;
    }
    public java.util.Date getEndTime() {
        return this.endTime;
    }
    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }
    public java.util.Date getStartTime() {
        return this.startTime;
    }
    public void setStartTime(java.util.Date startTime) {
        this.startTime = startTime;
    }
    public Integer getCarType() {
        return this.carType;
    }
    public void setCarType(Integer carType) {
        this.carType = carType;
    }
    public String getCarNo() {
        return this.carNo;
    }
    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1385098399)
    public ChargeRec(Long id, String carNo, Integer carType,
            java.util.Date startTime, java.util.Date endTime, double charges,
            String sign, String parkRecId) {
        this.id = id;
        this.carNo = carNo;
        this.carType = carType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.charges = charges;
        this.sign = sign;
        this.parkRecId = parkRecId;
    }
    @Generated(hash = 1497849156)
    public ChargeRec() {
    }




}
