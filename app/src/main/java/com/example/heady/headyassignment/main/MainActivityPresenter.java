package com.example.heady.headyassignment.main;

import com.example.heady.headyassignment.baseactivity.BasePresenter;
import com.example.heady.headyassignment.interfaceandClient.URLClient;
import com.example.heady.headyassignment.logactivity.LogActivity;
import com.example.heady.headyassignment.model.CategoryParameters;
import com.example.heady.headyassignment.model.FinalParameters;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter  <V extends MainActivityMvpView> extends BasePresenter<V>
        implements MainActivityMvpPresenter<V>  {
    private static String TAG = MainActivityPresenter.class.getSimpleName();
    @Override
    public void hitServerToGetData() {
        try {
            getApiInterface().getAllCategories().enqueue(new Callback<FinalParameters>() {
                @Override
                public void onResponse(Call<FinalParameters> call, Response<FinalParameters> response) {
                    LogActivity.log(TAG , "Inside response");
                    if (response.isSuccessful()){
                        LogActivity.log(TAG , "Inside response success");
                        FinalParameters finalParameters = response.body();
                        getMvpView().hideLoading();
                        getMvpView().loadCategories(finalParameters);
                    }
                    else {
                        LogActivity.log(TAG , "Inside response failed");
                    }
                }

                @Override
                public void onFailure(Call<FinalParameters> call, Throwable t) {
                    LogActivity.log(TAG , "Inside Failure");
                    LogActivity.log(TAG , t.toString());
                    getMvpView().hideLoading();
                    getMvpView().showError(URLClient.getErrorMsg(t));
                }
            });
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred in loading data " + e.toString());
        }

    }
}
