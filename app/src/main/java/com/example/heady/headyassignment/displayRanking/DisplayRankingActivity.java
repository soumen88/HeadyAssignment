package com.example.heady.headyassignment.displayRanking;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.example.heady.headyassignment.HeadyApp;
import com.example.heady.headyassignment.R;
import com.example.heady.headyassignment.appConfig.Config;
import com.example.heady.headyassignment.baseactivity.BaseActivity;
import com.example.heady.headyassignment.dao.DaoSessionSingleton;
import com.example.heady.headyassignment.logactivity.LogActivity;
import com.example.heady.headyassignment.model.CategoryDbParams;
import com.example.heady.headyassignment.model.CategoryDbParamsDao;
import com.example.heady.headyassignment.model.ProductRankingDbParams;
import com.example.heady.headyassignment.model.ProductRankingDbParamsDao;
import com.example.heady.headyassignment.model.ProductRankingItems;
import com.example.heady.headyassignment.model.ProductsDbParams;
import com.example.heady.headyassignment.model.ProductsDbParamsDao;
import com.example.heady.headyassignment.model.RankingDbParams;
import com.example.heady.headyassignment.model.RankingDbParamsDao;
import com.example.heady.headyassignment.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DisplayRankingActivity extends BaseActivity implements View.OnClickListener {

    private static String TAG = DisplayRankingActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private TextView rankingType;
    private Long rankingId;
    private ProductRankingAdapter productRankingAdapter;
    private ArrayList<ProductRankingItems> productRankingItemsArrayList;
    private FloatingActionButton floatingActionButton;
    private SweetAlertDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        setContentView(R.layout.activity_display_ranking);
        HeadyApp application = (HeadyApp) getApplication();
        Intent intent = getIntent();
        if(intent != null){
            rankingId = getIntent().getLongExtra("ranking_id",0);
            LogActivity.log(TAG , "ranking id passed " + rankingId);
        }
        init();
    }

    @Override
    public void init() {
        productRankingItemsArrayList = new ArrayList<>();
        productRankingAdapter = new ProductRankingAdapter(productRankingItemsArrayList);
        recyclerView = findViewById(R.id.recyclerView);
        rankingType = findViewById(R.id.rankingType);
        floatingActionButton = findViewById(R.id.fab_sort);
        RecyclerView.LayoutManager reLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(reLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(productRankingAdapter);
        getRankingType();
        getProducts();

        floatingActionButton.setOnClickListener(this);
    }

    private void getRankingType(){
        try {
            RankingDbParams rankingDbParams = DaoSessionSingleton.getDaoSession().getRankingDbParamsDao().queryBuilder().where(RankingDbParamsDao.Properties.RankingId.eq(rankingId)).limit(1).unique();
            if(rankingDbParams != null){
                rankingType.setText(rankingDbParams.getRanking_type());
            }
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception " + e.toString());
        }

    }

    private void getProducts(){
        try{

            List<ProductRankingDbParams> productsDbParams = DaoSessionSingleton.getDaoSession().getProductRankingDbParamsDao().queryBuilder().where(ProductRankingDbParamsDao.Properties.RankingId.eq(rankingId)).list();
            if(productsDbParams != null){
                LogActivity.log(TAG , "Size " + productsDbParams.size());
                for (int i = 0 ; i < productsDbParams.size() ; i++){
                    ProductRankingDbParams productsDbParams1 = productsDbParams.get(i);
                    LogActivity.log(TAG , "Product id received " + productsDbParams1.getProductIdReceived() + " i " + i);
                    List<ProductsDbParams> productsDbParamsList = DaoSessionSingleton.getDaoSession().getProductsDbParamsDao().queryBuilder().where(ProductsDbParamsDao.Properties.ProductIdReceived.eq(productsDbParams1.getProductIdReceived())).list();
                    if(productsDbParamsList.size() > 0){
                        for (int j = 0 ; j < productsDbParamsList.size() ; j++){
                            LogActivity.log(TAG , "Found product name " + productsDbParamsList.get(j).getName());
                            CategoryDbParams categoryDbParams = DaoSessionSingleton.getDaoSession().getCategoryDbParamsDao().queryBuilder().where(CategoryDbParamsDao.Properties.CategoryId.eq(productsDbParamsList.get(j).getCategoryId())).limit(1).unique();
                            ProductRankingItems productRankingItems = new ProductRankingItems();
                            productRankingItems.setCategoryName(categoryDbParams.getName());
                            productRankingItems.setDate(productsDbParamsList.get(j).getDate_added());
                            productRankingItems.setProductId(productsDbParamsList.get(j).getProductId());
                            productRankingItems.setProductName(productsDbParamsList.get(j).getName());
                            productRankingItems.setCount(productsDbParams.get(i).getViewCount());
                            productRankingItemsArrayList.add(productRankingItems);
                        }
                        productRankingAdapter.setNewProductData(productRankingItemsArrayList);
                    }
                    else {
                        LogActivity.log(TAG , "Product not found");
                    }
                }
            }
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred " + e.toString());
        }

    }

    private void sortProductsById(){
        try{
            productRankingItemsArrayList = new ArrayList<>();
            List<ProductRankingDbParams> productsDbParams = DaoSessionSingleton.getDaoSession().getProductRankingDbParamsDao().queryBuilder().where(ProductRankingDbParamsDao.Properties.RankingId.eq(rankingId)).orderDesc(ProductRankingDbParamsDao.Properties.ProductIdReceived).list();
            if(productsDbParams != null){
                LogActivity.log(TAG , "Size " + productsDbParams.size());
                for (int i = 0 ; i < productsDbParams.size() ; i++){
                    ProductRankingDbParams productsDbParams1 = productsDbParams.get(i);
                    LogActivity.log(TAG , "Product id received " + productsDbParams1.getProductIdReceived() + " i " + i);
                    List<ProductsDbParams> productsDbParamsList = DaoSessionSingleton.getDaoSession().getProductsDbParamsDao().queryBuilder().where(ProductsDbParamsDao.Properties.ProductIdReceived.eq(productsDbParams1.getProductIdReceived())).list();
                    if(productsDbParamsList.size() > 0){
                        for (int j = 0 ; j < productsDbParamsList.size() ; j++){
                            LogActivity.log(TAG , "Found product name " + productsDbParamsList.get(j).getName());
                            CategoryDbParams categoryDbParams = DaoSessionSingleton.getDaoSession().getCategoryDbParamsDao().queryBuilder().where(CategoryDbParamsDao.Properties.CategoryId.eq(productsDbParamsList.get(j).getCategoryId())).limit(1).unique();
                            ProductRankingItems productRankingItems = new ProductRankingItems();
                            productRankingItems.setCategoryName(categoryDbParams.getName());
                            productRankingItems.setDate(productsDbParamsList.get(j).getDate_added());
                            productRankingItems.setProductId(productsDbParamsList.get(j).getProductId());
                            productRankingItems.setProductName(productsDbParamsList.get(j).getName());
                            productRankingItems.setCount(productsDbParams.get(i).getViewCount());
                            productRankingItemsArrayList.add(productRankingItems);
                        }
                        productRankingAdapter.setNewProductData(productRankingItemsArrayList);
                    }
                    else {
                        LogActivity.log(TAG , "Product not found");
                    }
                }
            }
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred " + e.toString());
        }

    }

    private void sortProductsByCount(){
        try{
            productRankingItemsArrayList = new ArrayList<>();
            List<ProductRankingDbParams> productsDbParams = DaoSessionSingleton.getDaoSession().getProductRankingDbParamsDao().queryBuilder().where(ProductRankingDbParamsDao.Properties.RankingId.eq(rankingId)).orderDesc(ProductRankingDbParamsDao.Properties.ViewCount).list();
            if(productsDbParams != null){
                LogActivity.log(TAG , "Size " + productsDbParams.size());
                for (int i = 0 ; i < productsDbParams.size() ; i++){
                    ProductRankingDbParams productsDbParams1 = productsDbParams.get(i);
                    LogActivity.log(TAG , "Product id received " + productsDbParams1.getProductIdReceived() + " i " + i);
                    List<ProductsDbParams> productsDbParamsList = DaoSessionSingleton.getDaoSession().getProductsDbParamsDao().queryBuilder().where(ProductsDbParamsDao.Properties.ProductIdReceived.eq(productsDbParams1.getProductIdReceived())).list();
                    if(productsDbParamsList.size() > 0){
                        for (int j = 0 ; j < productsDbParamsList.size() ; j++){
                            LogActivity.log(TAG , "Found product name " + productsDbParamsList.get(j).getName());
                            CategoryDbParams categoryDbParams = DaoSessionSingleton.getDaoSession().getCategoryDbParamsDao().queryBuilder().where(CategoryDbParamsDao.Properties.CategoryId.eq(productsDbParamsList.get(j).getCategoryId())).limit(1).unique();
                            ProductRankingItems productRankingItems = new ProductRankingItems();
                            productRankingItems.setCategoryName(categoryDbParams.getName());
                            productRankingItems.setDate(productsDbParamsList.get(j).getDate_added());
                            productRankingItems.setProductId(productsDbParamsList.get(j).getProductId());
                            productRankingItems.setProductName(productsDbParamsList.get(j).getName());
                            productRankingItems.setCount(productsDbParams.get(i).getViewCount());
                            productRankingItemsArrayList.add(productRankingItems);
                        }
                        productRankingAdapter.setNewProductData(productRankingItemsArrayList);
                    }
                    else {
                        LogActivity.log(TAG , "Product not found");
                    }
                }
            }
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred " + e.toString());
        }

    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }




    private void showFinishClicking(String message){
        new SweetAlertDialog(DisplayRankingActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(Config.APP_TITLE)
                .setContentText(message)

                .setConfirmText("Sort By ID")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        sortProductsById();
                    }
                })
                .setCancelText("Sort By Count")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        sortProductsByCount();
                    }
                })
                .show();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_sort:
                showFinishClicking("Sort this data according to Product ID or By Product Count");
                break;
        }
    }
}
