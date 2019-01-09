package com.example.heady.headyassignment.categories;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.heady.headyassignment.R;
import com.example.heady.headyassignment.baseactivity.BaseFragment;
import com.example.heady.headyassignment.dao.DaoSessionSingleton;
import com.example.heady.headyassignment.displayproducts.DisplayProductsActivity;
import com.example.heady.headyassignment.logactivity.LogActivity;
import com.example.heady.headyassignment.model.CategoryDbParams;
import com.example.heady.headyassignment.model.CategoryDbParamsDao;
import com.example.heady.headyassignment.model.CategoryParameters;
import com.example.heady.headyassignment.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends BaseFragment implements CategoriesAdapter.SelectCategoryListener {
    private static String TAG = CategoriesFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private CategoriesAdapter categoriesAdapter;
    private ArrayList<CategoryDbParams> categoryDbParamsList;
    private CategoryDataViewModel categoryDataViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        return view;
    }

    @Override
    public void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(dpToPx(5), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(categoriesAdapter);
        /*List<CategoryDbParams> categoryDbParamsListpassed = getCategories();
        categoryDbParamsList.addAll(categoryDbParamsListpassed);
        categoriesAdapter.setnewCategorylist(categoryDbParamsList);*/
        categoryDataViewModel.getCategories().observe(getActivity(), new Observer<List<CategoryDbParams>>() {
            @Override
            public void onChanged(@Nullable List<CategoryDbParams> categoryDbParams) {
                if(categoryDbParams != null){
                    LogActivity.log(TAG , "Loading data inside observer");
                    categoryDbParamsList.addAll(categoryDbParams);
                    categoriesAdapter.setnewCategorylist(categoryDbParamsList);
                }
            }
        });

    }

    public static List<CategoryDbParams> getCategories(){
        try {
            List<CategoryDbParams> categoryParameters = DaoSessionSingleton.getDaoSession().getCategoryDbParamsDao().queryBuilder().list();
            if (categoryParameters != null){

                return categoryParameters;
            }

        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred " + e.toString());
        }
        return null;
    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void init() {
        categoryDbParamsList = new ArrayList<>();
        categoriesAdapter = new CategoriesAdapter(categoryDbParamsList, this, getActivity());
        categoryDataViewModel = ViewModelProviders.of(getActivity()).get(CategoryDataViewModel.class);
    }

    @Override
    public void selectedItem(CategoryDbParams categoryDbParams) {
        LogActivity.log(TAG , "Selected item " + categoryDbParams.getCategoryId());
        Intent i = new Intent(getActivity(), DisplayProductsActivity.class);
        i.putExtra("category_id" , categoryDbParams.getCategoryId());
        startActivity(i);
    }
}
