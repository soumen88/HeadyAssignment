package com.example.heady.headyassignment.interfaceandClient;

import com.example.heady.headyassignment.model.CategoryParameters;
import com.example.heady.headyassignment.model.FinalParameters;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {

    @GET("/json")
    Call<FinalParameters> getAllCategories();

}
