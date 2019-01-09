package com.example.heady.headyassignment.baseactivity;

import com.example.heady.headyassignment.interfaceandClient.APIInterface;


public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();

    APIInterface getApiInterface();

}
