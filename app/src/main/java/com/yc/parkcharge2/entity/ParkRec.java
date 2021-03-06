package com.yc.parkcharge2.entity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import java.util.Date;

/**
 * Entity mapped to table "PARK_REC".
 */
@Entity
public class ParkRec {

    @Id
    private Long id;
    private String carNo;
    private Integer carType;
    private String sign;
    private String recId;
    private java.util.Date createTime;
    public java.util.Date getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }
    public String getRecId() {
        return this.recId;
    }
    public void setRecId(String recId) {
        this.recId = recId;
    }
    public String getSign() {
        return this.sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
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
    @Generated(hash = 877819896)
    public ParkRec(Long id, String carNo, Integer carType, String sign, String recId,
            java.util.Date createTime) {
        this.id = id;
        this.carNo = carNo;
        this.carType = carType;
        this.sign = sign;
        this.recId = recId;
        this.createTime = createTime;
    }
    @Generated(hash = 1681100365)
    public ParkRec() {
    }
    
}
