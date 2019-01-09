package com.example.heady.headyassignment.main;

import com.example.heady.headyassignment.baseactivity.MvpPresenter;

public interface MainActivityMvpPresenter <V extends MainActivityMvpView> extends MvpPresenter<V> {
    void hitServerToGetData();
}
