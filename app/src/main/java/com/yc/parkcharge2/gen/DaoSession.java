package com.yc.parkcharge2.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.yc.parkcharge2.entity.ChargeRec;
import com.yc.parkcharge2.entity.ParkRec;
import com.yc.parkcharge2.entity.Strate;
import com.yc.parkcharge2.entity.StrateTimes;
import com.yc.parkcharge2.entity.StrateDays;
import com.yc.parkcharge2.entity.StrateLong;
import com.yc.parkcharge2.entity.StrateType;

import com.yc.parkcharge2.gen.ChargeRecDao;
import com.yc.parkcharge2.gen.ParkRecDao;
import com.yc.parkcharge2.gen.StrateDao;
import com.yc.parkcharge2.gen.StrateTimesDao;
import com.yc.parkcharge2.gen.StrateDaysDao;
import com.yc.parkcharge2.gen.StrateLongDao;
import com.yc.parkcharge2.gen.StrateTypeDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig chargeRecDaoConfig;
    private final DaoConfig parkRecDaoConfig;
    private final DaoConfig strateDaoConfig;
    private final DaoConfig strateTimesDaoConfig;
    private final DaoConfig strateDaysDaoConfig;
    private final DaoConfig strateLongDaoConfig;
    private final DaoConfig strateTypeDaoConfig;

    private final ChargeRecDao chargeRecDao;
    private final ParkRecDao parkRecDao;
    private final StrateDao strateDao;
    private final StrateTimesDao strateTimesDao;
    private final StrateDaysDao strateDaysDao;
    private final StrateLongDao strateLongDao;
    private final StrateTypeDao strateTypeDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        chargeRecDaoConfig = daoConfigMap.get(ChargeRecDao.class).clone();
        chargeRecDaoConfig.initIdentityScope(type);

        parkRecDaoConfig = daoConfigMap.get(ParkRecDao.class).clone();
        parkRecDaoConfig.initIdentityScope(type);

        strateDaoConfig = daoConfigMap.get(StrateDao.class).clone();
        strateDaoConfig.initIdentityScope(type);

        strateTimesDaoConfig = daoConfigMap.get(StrateTimesDao.class).clone();
        strateTimesDaoConfig.initIdentityScope(type);

        strateDaysDaoConfig = daoConfigMap.get(StrateDaysDao.class).clone();
        strateDaysDaoConfig.initIdentityScope(type);

        strateLongDaoConfig = daoConfigMap.get(StrateLongDao.class).clone();
        strateLongDaoConfig.initIdentityScope(type);

        strateTypeDaoConfig = daoConfigMap.get(StrateTypeDao.class).clone();
        strateTypeDaoConfig.initIdentityScope(type);

        chargeRecDao = new ChargeRecDao(chargeRecDaoConfig, this);
        parkRecDao = new ParkRecDao(parkRecDaoConfig, this);
        strateDao = new StrateDao(strateDaoConfig, this);
        strateTimesDao = new StrateTimesDao(strateTimesDaoConfig, this);
        strateDaysDao = new StrateDaysDao(strateDaysDaoConfig, this);
        strateLongDao = new StrateLongDao(strateLongDaoConfig, this);
        strateTypeDao = new StrateTypeDao(strateTypeDaoConfig, this);

        registerDao(ChargeRec.class, chargeRecDao);
        registerDao(ParkRec.class, parkRecDao);
        registerDao(Strate.class, strateDao);
        registerDao(StrateTimes.class, strateTimesDao);
        registerDao(StrateDays.class, strateDaysDao);
        registerDao(StrateLong.class, strateLongDao);
        registerDao(StrateType.class, strateTypeDao);
    }
    
    public void clear() {
        chargeRecDaoConfig.getIdentityScope().clear();
        parkRecDaoConfig.getIdentityScope().clear();
        strateDaoConfig.getIdentityScope().clear();
        strateTimesDaoConfig.getIdentityScope().clear();
        strateDaysDaoConfig.getIdentityScope().clear();
        strateLongDaoConfig.getIdentityScope().clear();
        strateTypeDaoConfig.getIdentityScope().clear();
    }

    public ChargeRecDao getChargeRecDao() {
        return chargeRecDao;
    }

    public ParkRecDao getParkRecDao() {
        return parkRecDao;
    }

    public StrateDao getStrateDao() {
        return strateDao;
    }

    public StrateTimesDao getStrateTimesDao() {
        return strateTimesDao;
    }

    public StrateDaysDao getStrateDaysDao() {
        return strateDaysDao;
    }

    public StrateLongDao getStrateLongDao() {
        return strateLongDao;
    }

    public StrateTypeDao getStrateTypeDao() {
        return strateTypeDao;
    }

}
