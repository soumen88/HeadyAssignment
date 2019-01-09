package com.example.heady.headyassignment.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.heady.headyassignment.model.DaoMaster;

public class DatabaseUpgradeHelper extends DaoMaster.OpenHelper {
    String TAG = "DatabaseUpgradeHelper";

    public DatabaseUpgradeHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            updateDatabase(newVersion, db);
        }
    }

    private void updateDatabase(int newVersion, SQLiteDatabase db) {
        switch (newVersion) {
            case 1:
                //updateDatabaseVersion1(db);
                break;
            case 2:
                // updateDatabaseVersion2(db);
                break;
            case 3:
                // updateDatabaseVersion3(db);
                break;
            default:
                break;
        }
    }



}
