package com.yc.code_gener;


import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

/**
 * Created by a on 2017/3/22.
 */

public class DaoMaker {

    public static void main(String[] args){

        Schema schema = new Schema(1, "com.parkcharge2.entity");
        addStudent(schema);
        schema.setDefaultJavaPackageDao("com.parkcharge2.dao");

        try {
            new DaoGenerator().generateAll(schema, "D:\\yuancheng\\code\\android\\ParkCharge2\\code-gener\\src\\main\\java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addStudent(Schema schema) {
        //创建数据库的表
        Entity entity = schema.addEntity("ParkRec");
        //主键 是int类型
        entity.addIdProperty();
        //名称
        entity.addStringProperty("carNo");
        //年龄
        entity.addIntProperty("carType");
        //地址
        entity.addDateProperty("createTime");
    }
}
