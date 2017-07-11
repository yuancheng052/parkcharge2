package com.parkcharge2.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.parkcharge2.entity.ParkRec;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PARK_REC".
*/
public class ParkRecDao extends AbstractDao<ParkRec, Long> {

    public static final String TABLENAME = "PARK_REC";

    /**
     * Properties of entity ParkRec.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CarNo = new Property(1, String.class, "carNo", false, "CAR_NO");
        public final static Property CarType = new Property(2, Integer.class, "carType", false, "CAR_TYPE");
        public final static Property CreateTime = new Property(3, java.util.Date.class, "createTime", false, "CREATE_TIME");
    };


    public ParkRecDao(DaoConfig config) {
        super(config);
    }
    
    public ParkRecDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PARK_REC\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"CAR_NO\" TEXT," + // 1: carNo
                "\"CAR_TYPE\" INTEGER," + // 2: carType
                "\"CREATE_TIME\" INTEGER);"); // 3: createTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PARK_REC\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ParkRec entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String carNo = entity.getCarNo();
        if (carNo != null) {
            stmt.bindString(2, carNo);
        }
 
        Integer carType = entity.getCarType();
        if (carType != null) {
            stmt.bindLong(3, carType);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(4, createTime.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ParkRec readEntity(Cursor cursor, int offset) {
        ParkRec entity = new ParkRec( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // carNo
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // carType
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)) // createTime
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ParkRec entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCarNo(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCarType(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setCreateTime(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ParkRec entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ParkRec entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}