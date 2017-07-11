package com.yc.parkcharge2.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yc.parkcharge2.entity.StrateTimes;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "STRATE_TIMES".
*/
public class StrateTimesDao extends AbstractDao<StrateTimes, Long> {

    public static final String TABLENAME = "STRATE_TIMES";

    /**
     * Properties of entity StrateTimes.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Price = new Property(1, double.class, "price", false, "PRICE");
    };


    public StrateTimesDao(DaoConfig config) {
        super(config);
    }
    
    public StrateTimesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"STRATE_TIMES\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"PRICE\" REAL NOT NULL );"); // 1: price
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"STRATE_TIMES\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, StrateTimes entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getPrice());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, StrateTimes entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getPrice());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public StrateTimes readEntity(Cursor cursor, int offset) {
        StrateTimes entity = new StrateTimes( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getDouble(offset + 1) // price
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, StrateTimes entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPrice(cursor.getDouble(offset + 1));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(StrateTimes entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(StrateTimes entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}