package com.example.heady.headyassignment.logactivity;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.widget.Toast;

import com.example.heady.headyassignment.HeadyApp;
import com.example.heady.headyassignment.appConfig.Config;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Soumen on 01-06-2019.
 */

public class LogActivity {
    private static String TAG = LogActivity.class.getSimpleName();
    private Typeface typefaceRegular, typefaceMedium;

    public static void log(String activityName, String logStatement) {
        Log.e(TAG , activityName + " - " + logStatement);
    }

    public static void logToast(Context context, String logStatement) {
        String text = logStatement;
        Spannable centeredText = new SpannableString(text);
        centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                0, text.length() - 1,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        Toast.makeText(context, centeredText, Toast.LENGTH_SHORT).show();
    }



    public static void logException(String activityName , Throwable t){
        try {
            Log.e(TAG , activityName + " - " + t.toString());
        }
        catch (Exception e){
            Log.e(TAG , "Exception Occurred while throwing " + e.toString());
        }

    }

    public static void showAlert(Context context , String message){

        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(Config.APP_TITLE)
                .setContentText(message)
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                    }
                })
                .show();
    }


}
