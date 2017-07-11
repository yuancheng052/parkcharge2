package com.yc.parkcharge2.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 类描述：按天收费
 */

@Entity
public class StrateDays {

    @Id
    private Long id;
    private double price;
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 144966626)
    public StrateDays(Long id, double price) {
        this.id = id;
        this.price = price;
    }
    @Generated(hash = 2032278576)
    public StrateDays() {
    }

}
