package com.example.heady.headyassignment.displayproducts;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import com.example.heady.headyassignment.HeadyApp;
import com.example.heady.headyassignment.R;
import com.example.heady.headyassignment.baseactivity.BaseActivity;
import com.example.heady.headyassignment.dao.DaoSessionSingleton;
import com.example.heady.headyassignment.logactivity.LogActivity;
import com.example.heady.headyassignment.model.CategoryDbParams;
import com.example.heady.headyassignment.model.CategoryDbParamsDao;
import com.example.heady.headyassignment.model.ProductsDbParams;
import com.example.heady.headyassignment.model.ProductsDbParamsDao;
import com.example.heady.headyassignment.model.ViewModel;
import com.example.heady.headyassignment.utils.SpacesItemDecoration;

public class DisplayProductsActivity extends BaseActivity {

    private static String TAG = DisplayProductsActivity.class.getSimpleName();
    private ArrayList<ProductsDbParams> productsDbParamsArrayList;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private Long categoryId;
    private TextView products_title;
    private LinearLayout layout_not_found;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_products);
        HeadyApp application = (HeadyApp) getApplication();
        Intent intent = getIntent();
        if(intent != null){
            categoryId = getIntent().getLongExtra("category_id",0);
            LogActivity.log(TAG , "product id passed " + categoryId);
        }
        init();
    }


    @Override
    public void init() {
        productsDbParamsArrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(this , productsDbParamsArrayList);
        recyclerView = findViewById(R.id.recyclerView);
        layout_not_found = findViewById(R.id.layout_not_found);
        products_title = findViewById(R.id.products_title);
        RecyclerView.LayoutManager reLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(reLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(productAdapter);
        getProducts();
        getCategoryType();
    }

    private void getCategoryType(){
        try {
            CategoryDbParams categoryDbParams = DaoSessionSingleton.getDaoSession().getCategoryDbParamsDao().queryBuilder().where(CategoryDbParamsDao.Properties.CategoryId.eq(categoryId)).limit(1).unique();
            if(categoryDbParams != null){
                products_title.setText(categoryDbParams.getName());
            }
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception " + e.toString());
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void getProducts(){
        try {
            List<ProductsDbParams> productsDbParams = DaoSessionSingleton.getDaoSession().getProductsDbParamsDao().queryBuilder().where(ProductsDbParamsDao.Properties.CategoryId.eq(categoryId)).list();
            if(productsDbParams != null){
                LogActivity.log(TAG , "Size " + productsDbParams.size());
                productsDbParamsArrayList.addAll(productsDbParams);
                productAdapter.setNewProductData(productsDbParamsArrayList);
                for (int i = 0 ; i < productsDbParams.size() ; i++){
                    ProductsDbParams productsDbParams1 = productsDbParams.get(i);
                    LogActivity.log(TAG , "Category id " + productsDbParams1.getCategoryId() + " i " + i);
                }
                if(productsDbParamsArrayList.size() == 0){
                    if(layout_not_found.getVisibility() == View.GONE){
                        layout_not_found.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred " + e.toString());
        }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
