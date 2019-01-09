package com.example.heady.headyassignment.ranking;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.heady.headyassignment.logactivity.LogActivity;
import com.example.heady.headyassignment.model.RankingDbParams;

import java.util.List;

public class RankingDataViewModel  extends AndroidViewModel {
    private static String TAG = RankingDataViewModel.class.getSimpleName();
    private final MutableLiveData<List<RankingDbParams>> mutableLiveData = new MutableLiveData<>();
    public RankingDataViewModel(@NonNull Application application) {
        super(application);
        loadRanking();
    }

    @SuppressLint("StaticFieldLeak")
    public void loadRanking() {
        LogActivity.log(TAG , "Load categories");
        new AsyncTask<Void, Void, List<RankingDbParams>>(){

            @Override
            protected List<RankingDbParams> doInBackground(Void... voids) {
                List<RankingDbParams> rankingDbParamsList = RankingFragment.getRanking();
                return rankingDbParamsList;
            }

            @Override
            protected void onPostExecute(List<RankingDbParams> rankingDbParamsList) {
                super.onPostExecute(rankingDbParamsList);
                mutableLiveData.setValue(rankingDbParamsList);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public MutableLiveData<List<RankingDbParams>> getRankings() {
        return mutableLiveData;
    }
}

