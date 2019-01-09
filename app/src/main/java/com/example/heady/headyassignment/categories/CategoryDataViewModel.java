package com.example.heady.headyassignment.categories;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.heady.headyassignment.logactivity.LogActivity;
import com.example.heady.headyassignment.model.CategoryDbParams;

import java.util.List;

public class CategoryDataViewModel extends AndroidViewModel {
    private static String TAG = CategoryDataViewModel.class.getSimpleName();
    private final MutableLiveData<List<CategoryDbParams>> mutableLiveData = new MutableLiveData<>();
    public CategoryDataViewModel(@NonNull Application application) {
        super(application);
        loadCategories();
    }

    @SuppressLint("StaticFieldLeak")
    public void loadCategories() {
        LogActivity.log(TAG , "Load categories");
        new AsyncTask<Void, Void, List<CategoryDbParams>>(){

            @Override
            protected List<CategoryDbParams> doInBackground(Void... voids) {
                List<CategoryDbParams> categoryDbParamsList = CategoriesFragment.getCategories();
                return categoryDbParamsList;
            }

            @Override
            protected void onPostExecute(List<CategoryDbParams> categoryDbParams) {
                super.onPostExecute(categoryDbParams);
                mutableLiveData.setValue(categoryDbParams);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public MutableLiveData<List<CategoryDbParams>> getCategories() {
        return mutableLiveData;
    }
}
