package com.yc.parkcharge2;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.yc.parkcharge2.entity.Strate;
import com.yc.parkcharge2.gen.DaoMaster;
import com.yc.parkcharge2.gen.DaoSession;

import org.greenrobot.greendao.identityscope.IdentityScopeType;

import java.util.List;

/**
 * Created by a on 2017/3/22.
 */

public class MyApplication extends Application {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static MyApplication instances;
    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        setDatabase();
        initData();
    }
    public static MyApplication getInstances(){

        return instances;
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "parkcharge", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession(IdentityScopeType.None);
    }

    private void initData(){
        List<Strate> strates = getDaoSession().getStrateDao().loadAll();
        if(strates ==null || strates.size() ==0){
            Strate strate0 = new Strate(null, 0, "00:00:00", "07:00:00", 2, 1);
            Strate strate1 = new Strate(null, 0, "07:00:00", "21:00:00", 1, 2);
            Strate strate2 = new Strate(null, 0, "21:00:00", "23:59:59", 2, 1);
            getDaoSession().getStrateDao().insert(strate0);
            getDaoSession().getStrateDao().insert(strate1);
            getDaoSession().getStrateDao().insert(strate2);
        }else{
            //getDaoSession().getStrateDao().deleteAll();
        }
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }

}
