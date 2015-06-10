package com.smarter56.waxberry.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.smarter56.waxberry.dao.GpsInfoModel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table GPS_INFO_MODEL.
*/
public class GpsInfoModelDao extends AbstractDao<GpsInfoModel, Void> {

    public static final String TABLENAME = "GPS_INFO_MODEL";

    /**
     * Properties of entity GpsInfoModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Lat = new Property(0, Double.class, "lat", false, "LAT");
        public final static Property Lon = new Property(1, Double.class, "lon", false, "LON");
        public final static Property UpdateTime = new Property(2, String.class, "updateTime", false, "UPDATE_TIME");
        public final static Property VehicleNo = new Property(3, String.class, "vehicleNo", false, "VEHICLE_NO");
        public final static Property PlaceName = new Property(4, String.class, "placeName", false, "PLACE_NAME");
        public final static Property Speed = new Property(5, Float.class, "speed", false, "SPEED");
        public final static Property Direction = new Property(6, Float.class, "direction", false, "DIRECTION");
    };


    public GpsInfoModelDao(DaoConfig config) {
        super(config);
    }
    
    public GpsInfoModelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'GPS_INFO_MODEL' (" + //
                "'LAT' REAL," + // 0: lat
                "'LON' REAL," + // 1: lon
                "'UPDATE_TIME' TEXT," + // 2: updateTime
                "'VEHICLE_NO' TEXT," + // 3: vehicleNo
                "'PLACE_NAME' TEXT," + // 4: placeName
                "'SPEED' REAL," + // 5: speed
                "'DIRECTION' REAL);"); // 6: direction
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'GPS_INFO_MODEL'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, GpsInfoModel entity) {
        stmt.clearBindings();
 
        Double lat = entity.getLat();
        if (lat != null) {
            stmt.bindDouble(1, lat);
        }
 
        Double lon = entity.getLon();
        if (lon != null) {
            stmt.bindDouble(2, lon);
        }
 
        String updateTime = entity.getUpdateTime();
        if (updateTime != null) {
            stmt.bindString(3, updateTime);
        }
 
        String vehicleNo = entity.getVehicleNo();
        if (vehicleNo != null) {
            stmt.bindString(4, vehicleNo);
        }
 
        String placeName = entity.getPlaceName();
        if (placeName != null) {
            stmt.bindString(5, placeName);
        }
 
        Float speed = entity.getSpeed();
        if (speed != null) {
            stmt.bindDouble(6, speed);
        }
 
        Float direction = entity.getDirection();
        if (direction != null) {
            stmt.bindDouble(7, direction);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public GpsInfoModel readEntity(Cursor cursor, int offset) {
        GpsInfoModel entity = new GpsInfoModel( //
            cursor.isNull(offset + 0) ? null : cursor.getDouble(offset + 0), // lat
            cursor.isNull(offset + 1) ? null : cursor.getDouble(offset + 1), // lon
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // updateTime
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // vehicleNo
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // placeName
            cursor.isNull(offset + 5) ? null : cursor.getFloat(offset + 5), // speed
            cursor.isNull(offset + 6) ? null : cursor.getFloat(offset + 6) // direction
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, GpsInfoModel entity, int offset) {
        entity.setLat(cursor.isNull(offset + 0) ? null : cursor.getDouble(offset + 0));
        entity.setLon(cursor.isNull(offset + 1) ? null : cursor.getDouble(offset + 1));
        entity.setUpdateTime(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setVehicleNo(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPlaceName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSpeed(cursor.isNull(offset + 5) ? null : cursor.getFloat(offset + 5));
        entity.setDirection(cursor.isNull(offset + 6) ? null : cursor.getFloat(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(GpsInfoModel entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(GpsInfoModel entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}