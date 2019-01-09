package com.example.heady.headyassignment.main;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.heady.headyassignment.HeadyApp;
import com.example.heady.headyassignment.R;
import com.example.heady.headyassignment.appConfig.Config;
import com.example.heady.headyassignment.baseactivity.BaseActivity;
import com.example.heady.headyassignment.categories.CategoriesFragment;
import com.example.heady.headyassignment.connectionDetector.ConnectionDetector;
import com.example.heady.headyassignment.dao.DaoSessionSingleton;
import com.example.heady.headyassignment.logactivity.LogActivity;
import com.example.heady.headyassignment.model.CategoryDbParams;
import com.example.heady.headyassignment.model.CategoryDbParamsDao;
import com.example.heady.headyassignment.model.CategoryParameters;
import com.example.heady.headyassignment.model.DaoSession;
import com.example.heady.headyassignment.model.FinalParameters;
import com.example.heady.headyassignment.model.ProductParameters;
import com.example.heady.headyassignment.model.ProductRankingDbParams;
import com.example.heady.headyassignment.model.ProductRankingDbParamsDao;
import com.example.heady.headyassignment.model.ProductRankingParameters;
import com.example.heady.headyassignment.model.ProductsDbParams;
import com.example.heady.headyassignment.model.ProductsDbParamsDao;
import com.example.heady.headyassignment.model.RankingDbParams;
import com.example.heady.headyassignment.model.RankingDbParamsDao;
import com.example.heady.headyassignment.model.RankingParameters;
import com.example.heady.headyassignment.model.VariantDbParams;
import com.example.heady.headyassignment.model.VariantDbParamsDao;
import com.example.heady.headyassignment.model.VariantParameters;
import com.example.heady.headyassignment.ranking.RankingFragment;

import java.util.ArrayList;
import java.util.Collections;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends BaseActivity implements MainActivityMvpView {
    private static String TAG = MainActivity.class.getSimpleName();
    private SweetAlertDialog progressDialog;
    private MainActivityPresenter mainActivityPresenter;
    private ActionBar toolbar;
    private FragmentTransaction fragmentTransaction;
    private CategoriesFragment categoriesFragment;
    private RankingFragment rankingFragment;
    private FragmentManager manager;
    private BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HeadyApp application = (HeadyApp) getApplication();
        setContentView(R.layout.activity_main);
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mainActivityPresenter = new MainActivityPresenter();
        mainActivityPresenter.onAttach(this);
        LogActivity.log(TAG , "On create started ");
        init();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.categories:
                    toolbar.setTitle("Categories");
                    startCategoriesFragment();
                    return true;
                case R.id.ranking:
                    toolbar.setTitle("Ranking");
                    startRankingFragment();
                    return true;
            }

            return false;
        }
    };

    @Override
    public void getCategories() {
        if(new ConnectionDetector(getApplicationContext()).isNetworkConnectionAvailable()){
            LogActivity.log(TAG , "Hit server for getting data");
            showLoading();
            mainActivityPresenter.hitServerToGetData();
        }
        else {
            LogActivity.logToast(this, "No internet connection detected");
            startCategoriesFragment();
        }
    }

    @Override
    public void loadCategories(FinalParameters finalParameters) {
        try {
            LogActivity.log(TAG , "Inside load categories ");

            if(finalParameters != null){
                LogActivity.log(TAG , "It is not null " + finalParameters.getCategoryParametersList().size());
                LogActivity.log(TAG , "Ranking " + finalParameters.getRankingParametersList().size());
                ArrayList<CategoryParameters> categoryParametersArrayList = new ArrayList<>();
                categoryParametersArrayList.addAll(finalParameters.getCategoryParametersList());
                Collections.sort(categoryParametersArrayList);
                //Categories
                for(CategoryParameters categoryParameters: categoryParametersArrayList ){
                    LogActivity.log(TAG , "Category Name " + categoryParameters.getName());
                    LogActivity.log(TAG , "Products size " + categoryParameters.getProductList().size());
                    Long categoryInsertId = insertintoCategories(categoryParameters.getName(), categoryParameters.getCategoryIdReceived());
                    LogActivity.log(TAG , "Catergory id inserted " + categoryInsertId);
                    if(categoryInsertId != 0L){
                        ArrayList<ProductParameters> productParametersList = categoryParameters.getProductList();
                        for(ProductParameters productParameters: productParametersList){
                            LogActivity.log(TAG , "Category ID " + categoryParameters.getCategoryIdReceived() + " Category insert id " + categoryInsertId);
                            /*ProductsDbParams productsDbParams = new ProductsDbParams();
                            productsDbParams.setProductIdReceived(productParameters.getProductIdReceived());
                            productsDbParams.setCategoryId(categoryInsertId);
                            productsDbParams.setName(productParameters.getProductname());
                            productsDbParams.setDate_added(productParameters.getDate_added());
                            productsDbParams.setDbtaxname(productParameters.getTaxParameters().getTaxname());
                            productsDbParams.setDbtaxvalue(productParameters.getTaxParameters().getTaxvalue());*/
                            Long insertedProductId = insertIntoProducts(productParameters.getProductIdReceived(),categoryInsertId,productParameters.getProductname(), productParameters.getDate_added(), productParameters.getTaxParameters().getTaxname(), productParameters.getTaxParameters().getTaxvalue());
                            LogActivity.log(TAG , "Insert id " + insertedProductId);
                            if(insertedProductId != 0L){
                                for(VariantParameters variantParameters: productParameters.getVariantList()){
                                    LogActivity.log(TAG , "Variant " + variantParameters.getPrice() + " Product id " + insertedProductId);
                                    /*VariantDbParams variantDbParams = new VariantDbParams();
                                    variantDbParams.setVariantIdReceived(variantParameters.getVariantIdReceived());
                                    variantDbParams.setColor(variantParameters.getColor());
                                    variantDbParams.setSize(variantParameters.getSize());
                                    variantDbParams.setPrice(variantParameters.getPrice());
                                    variantDbParams.setProductid(insertedProductId);*/
                                    Long variantsInsertedId = insertIntoVariants(variantParameters.getVariantIdReceived(), insertedProductId, variantParameters.getColor(), variantParameters.getSize(), variantParameters.getPrice());
                                    LogActivity.log(TAG , "Variants inserted id " + variantsInsertedId);
                                }
                            }

                        }
                    }

                }

                //Rankings
                ArrayList<RankingParameters> rankingParametersArrayList = new ArrayList<>();
                rankingParametersArrayList.addAll(finalParameters.getRankingParametersList());
                for(RankingParameters rankingParameters : rankingParametersArrayList){
                    /*RankingDbParams rankingDbParams = new RankingDbParams();
                    rankingDbParams.setRanking_type(rankingParameters.getRanking_type());*/
                    Long rankingInsertId = insertIntoRanking(rankingParameters.getRanking_type());
                    if(rankingInsertId != 0L){
                        LogActivity.log(TAG , "Ranking insert id " + rankingInsertId);
                        for(ProductRankingParameters productRankingParameters: rankingParameters.getProductRankingParametersList()){
                            LogActivity.log(TAG , "Product id " + productRankingParameters.getProductRankingIdReceived());
                            if(productRankingParameters.getProductRankingIdReceived() != null && productRankingParameters.getOrdercount() != null){
                                LogActivity.log(TAG , "Found order");
                                insertIntoProductRanking(rankingInsertId, productRankingParameters.getProductRankingIdReceived(), productRankingParameters.getOrdercount());
                            }
                            else if(productRankingParameters.getProductRankingIdReceived() != null && productRankingParameters.getViewcount() != null){
                                LogActivity.log(TAG , "Found view");
                                insertIntoProductRanking(rankingInsertId, productRankingParameters.getProductRankingIdReceived(), productRankingParameters.getViewcount());
                            }
                            else if(productRankingParameters.getProductRankingIdReceived() != null && productRankingParameters.getShares() != null){
                                LogActivity.log(TAG , "Found shares");
                                insertIntoProductRanking(rankingInsertId, productRankingParameters.getProductRankingIdReceived(), productRankingParameters.getShares());
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred " + e.toString());
        }
        LogActivity.log(TAG , "Loading data is now complete...");
        startCategoriesFragment();
    }

    private Long insertIntoRanking(String rankingType){
        try {
            RankingDbParams check = DaoSessionSingleton.getDaoSession().getRankingDbParamsDao().queryBuilder().where(RankingDbParamsDao.Properties.Ranking_type.eq(rankingType)).limit(1).unique();
            if(check != null){
                LogActivity.log(TAG , "Not inserting because same ranking type is already present");
            }
            else {
                RankingDbParams rankingDbParams = new RankingDbParams();
                rankingDbParams.setRanking_type(rankingType);
                Long rankingInsertId = DaoSessionSingleton.getDaoSession().getRankingDbParamsDao().insert(rankingDbParams);
                return rankingInsertId;
            }

        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred " + e.toString());
        }
        return 0L;
    }

    private void insertIntoProductRanking(Long rankingInsertId, Long idReceived, Long count){
        try {
            LogActivity.log(TAG, "Product id not found so insert");
            ProductRankingDbParams productRankingDbParams = new ProductRankingDbParams();
            productRankingDbParams.setRankingId(rankingInsertId);
            productRankingDbParams.setProductIdReceived(idReceived);
            productRankingDbParams.setViewCount(count);
            Long productRankingInsertId = DaoSessionSingleton.getDaoSession().getProductRankingDbParamsDao().insert(productRankingDbParams);
            LogActivity.log(TAG , "Production " + productRankingInsertId);
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred in insert into ranking " + e.toString());
        }

    }

    private Long insertintoCategories(String name, Long categoryIdReceived){
        try {
            CategoryDbParams check = DaoSessionSingleton.getDaoSession().getCategoryDbParamsDao().queryBuilder().where(CategoryDbParamsDao.Properties.CategoryIdReceived.eq(categoryIdReceived), CategoryDbParamsDao.Properties.Name.eq(name)).limit(1).unique();
            if(check != null){
                LogActivity.log(TAG , "Category is already present");
                return 0L;
            }
            else {
                LogActivity.log(TAG , "Inserting into categories");
                CategoryDbParams categoryDbParams = new CategoryDbParams();
                categoryDbParams.setName(name);
                categoryDbParams.setCategoryIdReceived(categoryIdReceived);
                Long categoryInsertId = DaoSessionSingleton.getDaoSession().getCategoryDbParamsDao().insert(categoryDbParams);
                return categoryInsertId;
            }
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred in insert into categories " + e.toString());
        }
        return 0L;
    }

    public Long insertIntoProducts(Long productIdReceived, Long categoryInsertId, String productName ,String date_added, String tax_name, Double taxvalue ){
        try{
            ProductsDbParams check = DaoSessionSingleton.getDaoSession().getProductsDbParamsDao().queryBuilder().where(ProductsDbParamsDao.Properties.ProductIdReceived.eq(productIdReceived)).limit(1).unique();
            if(check != null){
                LogActivity.log(TAG , "Not inserting because product with similar id is found already");
                return 0L;
            }
            else {
                ProductsDbParams productsDbParams = new ProductsDbParams();
                productsDbParams.setProductIdReceived(productIdReceived);
                productsDbParams.setCategoryId(categoryInsertId);
                productsDbParams.setName(productName);
                productsDbParams.setDate_added(date_added);
                productsDbParams.setDbtaxname(tax_name);
                productsDbParams.setDbtaxvalue(taxvalue);
                Long insertedProductId = DaoSessionSingleton.getDaoSession().getProductsDbParamsDao().insert(productsDbParams);
                return insertedProductId;
            }

        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred while inserting into products "  + e.toString());
        }
        return 0L;
    }


    private Long insertIntoVariants(Long variantIdReceived, Long insertedProductId, String color, Long size, Double price ) {
        try {
            VariantDbParams check = DaoSessionSingleton.getDaoSession().getVariantDbParamsDao().queryBuilder().where(VariantDbParamsDao.Properties.VariantIdReceived.eq(variantIdReceived)).limit(1).unique();
            if (check != null) {
                LogActivity.log(TAG, "Not inserting because found similar variant id already ");
                return 0L;
            } else {
                VariantDbParams variantDbParams = new VariantDbParams();
                variantDbParams.setVariantIdReceived(variantIdReceived);
                variantDbParams.setProductid(insertedProductId);
                variantDbParams.setColor(color);
                variantDbParams.setSize(size);
                variantDbParams.setPrice(price);

                Long variantsInsertedId = DaoSessionSingleton.getDaoSession().getVariantDbParamsDao().insert(variantDbParams);
                return variantsInsertedId;
            }

        }
        catch(Exception e){
                LogActivity.log(TAG, "Exception occurred in insert into variants " + e.toString());
        }
        return 0L;
    }

    private void startCategoriesFragment(){
        try {
            manager = getSupportFragmentManager();
            categoriesFragment = new CategoriesFragment();
            android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            LogActivity.log(TAG , "Replacing fragment");
            ft.replace(R.id.fragment_container, categoriesFragment).commit();
        }
        catch (Exception e){
            LogActivity.log(TAG ,"Exception " + e.toString());
        }

    }

    private void startRankingFragment(){
        try {
            manager = getSupportFragmentManager();
            rankingFragment = new RankingFragment();
            android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            LogActivity.log(TAG , "Replacing fragment");
            ft.replace(R.id.fragment_container, rankingFragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        catch (Exception e){
            LogActivity.log(TAG ,"Exception " + e.toString());
        }

    }


    @Override
    public void init() {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar = getSupportActionBar();
        getCategories();

    }

    @Override
    public void showLoading() {
        if(progressDialog!= null){
            progressDialog.setTitleText("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismissWithAnimation();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
