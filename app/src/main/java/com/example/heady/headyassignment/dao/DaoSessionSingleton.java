package com.example.heady.headyassignment.dao;

import android.content.Context;

import com.example.heady.headyassignment.appConfig.Config;
import com.example.heady.headyassignment.model.DaoMaster;
import com.example.heady.headyassignment.model.DaoSession;

import org.greenrobot.greendao.database.Database;


public class DaoSessionSingleton {

    private static DaoSession daoSession;

    private DaoSessionSingleton(Context context) {
        DatabaseUpgradeHelper helper = new DatabaseUpgradeHelper(context, Config.DB_NAME);
       // DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static synchronized void initialize(Context context) {
        if (daoSession == null) {
            new DaoSessionSingleton(context);
        }
    }

    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            throw new RuntimeException("Dao session not initialized");
        }
        return daoSession;
    }

}
