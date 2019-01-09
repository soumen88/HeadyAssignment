package com.example.heady.headyassignment.baseactivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment implements MvpView {

    private MvpView mvpView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        init();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    public abstract void initViews(View view);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MvpView) {
            this.mvpView = (MvpView) context;
        }
    }

    @Override
    public void showToast(int resId) {
        String message = getString(resId);
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        if (mvpView != null) {
            mvpView.showError(message);
        }
    }

    @Override
    public void showError(@StringRes int resId) {
        if (mvpView != null) {
            mvpView.showError(resId);
        }
    }
    @Override
    public void showSuccess(String message) {

    }

    @Override
    public void showSuccess(int resId) {
        showSuccess(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return mvpView != null && mvpView.isNetworkConnected();
    }

    @Override
    public void onDetach() {
        mvpView = null;
        super.onDetach();
    }

    @Override
    public void hideKeyboard() {
        if (mvpView != null) {
            mvpView.hideKeyboard();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


}
