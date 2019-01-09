package com.example.heady.headyassignment.baseactivity;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.Toast;
import com.example.heady.headyassignment.connectionDetector.ConnectionDetector;
import com.example.heady.headyassignment.logactivity.LogActivity;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {
    private static String TAG = BaseActivity.class.getSimpleName();
    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showToast(int resId) {
        String message = getString(resId);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void showError(String message) {
        LogActivity.log(TAG ,"Error message " + message);

    }

    @Override
    public void showSuccess(String message) {
        LogActivity.log(TAG , "Success message " + message);

    }


    @Override
    public void showSuccess(int resId) {
        showSuccess(getString(resId));
    }

    @Override
    public void showError(@StringRes int resId) {
        showError(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return new ConnectionDetector(getApplicationContext()).isNetworkConnectionAvailable();
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}

