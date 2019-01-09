package com.example.heady.headyassignment.baseactivity;


import android.support.annotation.StringRes;

public interface MvpView {

    void init();

    void showError(@StringRes int resId);

    void showError(String message);

    void showSuccess(String message);

    void showSuccess(@StringRes int resId);

    void showToast(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();

    void showLoading();

    void hideLoading();

}

