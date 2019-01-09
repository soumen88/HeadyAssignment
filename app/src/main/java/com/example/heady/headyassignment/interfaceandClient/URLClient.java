package com.example.heady.headyassignment.interfaceandClient;


import com.example.heady.headyassignment.BuildConfig;
import com.example.heady.headyassignment.appConfig.Constants;
import com.example.heady.headyassignment.logactivity.LogActivity;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class URLClient {

    private static final String GENERAL_ERROR = "Some error occurred";
    private static final String SERVER_ERROR = "Some error occurred at server, Please try again later!!!";
    private static final String CONNECTION_ERROR = "Error while connecting to server. Please try again later";
    private static final String TIMEOUT_EXCEPTION = "Timeout exception";

    public static final String BASE_URL = "https://stark-spire-93433.herokuapp.com";

    private static Retrofit retrofit = null;

    private static String TAG = URLClient.class.getSimpleName();
    public static Retrofit getClient() {
        if (retrofit==null) {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }
            //loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.readTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS);
            client.connectTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS);
            client.writeTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS);
            //client.cache(null);
            client.addInterceptor(loggingInterceptor);


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static String parseError(Response<?> response) {
        try {
            Converter<ResponseBody, ErrorModel> converter = URLClient.getClient().responseBodyConverter(ErrorModel.class, new java.lang.annotation.Annotation[0]);
            ErrorModel error = converter.convert(response.errorBody());
            if (null != error.getHumanizedMessage()) {
                return error.getHumanizedMessage();
            }
            return checkErrorCode(response);
        } catch (Exception e) {
            LogActivity.logException(TAG, e);
            return checkErrorCode(response);
        }
    }

    private static String checkErrorCode(Response<?> response) {
        if (response.code() >= 500) {
            return SERVER_ERROR;
        }
        return GENERAL_ERROR;
    }

    /**
     * This method is used to get error message
     */
    public static String getErrorMsg(Throwable t) {
        if (t instanceof ConnectException) {
            return CONNECTION_ERROR;
        } else if (t instanceof IOException) {
            return TIMEOUT_EXCEPTION;
        } else {
            return GENERAL_ERROR;
        }
    }

}

