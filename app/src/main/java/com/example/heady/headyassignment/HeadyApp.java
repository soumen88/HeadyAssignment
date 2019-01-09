package com.example.heady.headyassignment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.example.heady.headyassignment.dao.DaoSessionSingleton;
import com.example.heady.headyassignment.logactivity.LogActivity;

public class HeadyApp extends Application {


    public enum TrackerName {
        APP_TRACKER, // tracker used only in this app
        GLOBAL_TRACKER, // tracker used by all the apps from a company . eg: roll-up tracking.
        ECOMMERCE_TRACKER, // tracker used by all ecommerce transactions from a company .
    }

    private static final String TAG = HeadyApp.class.getSimpleName();
//    public synchronized Tracker getTracker(TrackerName trackerId) {
//        if (!mTrackers.containsKey(trackerId)) {
//            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
//            Tracker tracker = (trackerId == TrackerName.APP_TRACKER)?analytics.newTracker(Property_ID)
//                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
//                    : analytics.newTracker(R.xml.ecommerce_tracker);
//            mTrackers.put(trackerId , tracker);
//        }
//        return  mTrackers.get(trackerId);
//    }


    private Activity mCurrentActivity = null;

    public void setCurrentActivity(Activity mCurrectActivity) {
        this.mCurrentActivity = mCurrectActivity;
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    private static Context mAppContext;

    public static HeadyApp mInstance;

//    public static RefWatcher getRefWatcher(Context context) {
//        WondrxPatient application = (WondrxPatient) context.getApplicationContext();
//        return application.refWatcher;
//    }
//
//    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Log.e(TAG , "OnCreate of MyApplication");
            MultiDex.install(this);
            mInstance = this;
            this.setAppContext(getApplicationContext());
            DaoSessionSingleton.initialize(this);

        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred " + e.toString());
        }

    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static HeadyApp getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public void setAppContext(Context mAppContext) {
        this.mAppContext = mAppContext;
    }

}

