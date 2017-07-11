package com.yc.parkcharge2.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 类描述：收费类型
 */

@Entity
public class StrateType {

    @Id
    private Long id;

    //1按次收费
    //2.按天计费
    //3.首小时2元，次小时1元
    //4.按时段进行收费

    private int strateType;
    public int getStrateType() {
        return this.strateType;
    }
    public void setStrateType(int strateType) {
        this.strateType = strateType;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 290387617)
    public StrateType(Long id, int strateType) {
        this.id = id;
        this.strateType = strateType;
    }
    @Generated(hash = 676322311)
    public StrateType() {
    }

}
