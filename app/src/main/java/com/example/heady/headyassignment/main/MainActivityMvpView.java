package com.example.heady.headyassignment.main;

import com.example.heady.headyassignment.baseactivity.MvpView;
import com.example.heady.headyassignment.model.CategoryParameters;
import com.example.heady.headyassignment.model.FinalParameters;

import java.util.ArrayList;

public interface MainActivityMvpView extends MvpView {
    void getCategories();
    void loadCategories(FinalParameters finalParameters);
}
