package com.smarter56.waxberry.util;

import java.util.List;

import com.smarter56.waxberry.activity.MainActivity;
import com.smarter56.waxberry.dao.DaoMaster;
import com.smarter56.waxberry.dao.DaoSession;
import com.smarter56.waxberry.dao.GpsInfoModel;
import com.smarter56.waxberry.dao.GpsInfoModelDao;

import de.greenrobot.dao.query.QueryBuilder;

import android.R.integer;
import android.content.Context;
import android.util.Log;

/**
 * @author Ricky
 */
public class DBService {
	private final static String TAG = DBService.class.getSimpleName();
	private static DBService instance;
	private static Context appContext;

	/* Dao Session and Entity Dao */
	private static DaoSession mDaoSession;
	private static DaoMaster mDaoMaster;
	private GpsInfoModelDao gpsInfoModelDao;

	public static DBService getInstance(Context context) {
		if (instance == null) {
			instance = new DBService();
			Log.i(TAG, "new DBServiceBak instance");
		}
		if (appContext == null) {
			appContext = context.getApplicationContext();
		}
		/* get the dao session entity session from services instance */
		instance.mDaoSession = getDaoSession(context);
		instance.gpsInfoModelDao = instance.mDaoSession.getGpsInfoModelDao();
		return instance;
	}

	/**
	 * insert or update note
	 * 
	 * @param entity
	 * @return insert or update note id
	 */
	public long saveGpsInfoModel(GpsInfoModel entity) {
		return gpsInfoModelDao.insertOrReplace(entity);
	}

	public void clearAndSaveGpsInfoModel(List<GpsInfoModel> entityList) {
		deletAllGpsInfoMode();
		saveGpsInfoModle(entityList);
	}

	/**
	 * insert or update EntityList use transaction
	 * 
	 * @param entityList
	 */
	public void saveGpsInfoModle(final List<GpsInfoModel> entityList) {
		if (entityList == null || entityList.isEmpty()) {
			return;
		}
		gpsInfoModelDao.getSession().runInTx(new Runnable() {
			@Override
			public void run() {
				for (GpsInfoModel entity : entityList) {
					gpsInfoModelDao.insertOrReplace(entity);
				}
			}
		});
	}

	/**
	 * delete
	 * 
	 * @param model
	 */
	public void deleteAllGpsInfoModel(GpsInfoModel model) {
		gpsInfoModelDao.delete(model);
	}

	public void deletAllGpsInfoMode() {
		gpsInfoModelDao.deleteAll();
	}

	/**
	 * query
	 * 
	 * @return
	 */
	public List<GpsInfoModel> loadAllGpsInfoModels() {
		return gpsInfoModelDao.loadAll();
	}

	/**
	 * query 
	 * 
	 * @return
	 */
	public long countInfoModels() {
		return gpsInfoModelDao.count();
	}
	


	/**
	 * load the entity with limit count
	 * 
	 * @param limit
	 * @return
	 */
	public List<GpsInfoModel> loadAllGpsInfoModels(int count) {
		QueryBuilder<GpsInfoModel> queryBuilder = gpsInfoModelDao
				.queryBuilder();
		queryBuilder.limit(count);
		return queryBuilder.list();

	}

	/**
	 * query list with where clause ex: begin_date_time >= ? AND end_date_time
	 * <= ?
	 * 
	 * @param where
	 *            where clause, include 'where' word
	 * @param params
	 *            query parameters
	 * @return
	 */

	public List<GpsInfoModel> queryRtePlan(String where, String... params) {
		return gpsInfoModelDao.queryRaw(where, params);
	}

	public List<GpsInfoModel> queryRtePlanByKey(Object key) {
		QueryBuilder<GpsInfoModel> queryBuilder = gpsInfoModelDao
				.queryBuilder();
		queryBuilder.where(GpsInfoModelDao.Properties.VehicleNo.eq(key));
		return queryBuilder.list();
	}

	/**
	 * get the DAO SESSION
	 * 
	 * @param context
	 * @return
	 */
	public static DaoSession getDaoSession(Context context) {
		if (mDaoSession == null) {
			if (mDaoMaster == null) {
				mDaoMaster = getDaoMaster(context);
			}
			mDaoSession = mDaoMaster.newSession();
		}
		return mDaoSession;
	}

	/**
	 * get the DAO MASTER
	 * 
	 * @param context
	 * @return
	 */
	public static DaoMaster getDaoMaster(Context context) {
		if (mDaoMaster == null) {
			DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,
					"waxberry_db", null);
			mDaoMaster = new DaoMaster(helper.getWritableDatabase());
		}
		return mDaoMaster;
	}
}
