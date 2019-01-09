package com.example.heady.headyassignment.displayproducts;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heady.headyassignment.R;
import com.example.heady.headyassignment.dao.DaoSessionSingleton;
import com.example.heady.headyassignment.logactivity.LogActivity;
import com.example.heady.headyassignment.model.ProductsDbParams;
import com.example.heady.headyassignment.model.VariantDbParams;
import com.example.heady.headyassignment.model.VariantDbParamsDao;
import com.example.heady.headyassignment.model.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private static String TAG = ProductAdapter.class.getSimpleName();
    private ArrayList<ProductsDbParams> productsDbParamsArrayList;
    private OnItemClickListener onItemClickListener;
    private Context context;
    public ProductAdapter(Context context , ArrayList<ProductsDbParams> productsDbParamsArrayList) {
        this.productsDbParamsArrayList = new ArrayList<>();
        this.context = context;
    }

    public void setNewProductData(ArrayList<ProductsDbParams> productsDbParamsArrayList){
        this.productsDbParamsArrayList = productsDbParamsArrayList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            ProductsDbParams productsDbParams = productsDbParamsArrayList.get(position);
            if(productsDbParams!= null){
                List<VariantDbParams> variantDbParams = getProductVariants(productsDbParams.getProductId());
                if(variantDbParams!= null){
                    //setRecyclerview(holder.recyclerView, variantDbParams);
                    setViewPager(holder.viewPager,variantDbParams);
                }
                holder.txtProductName.setText(productsDbParams.getName() + " Found " + variantDbParams.size() + " variants. ");
                holder.txtProductId.setText("ID " +productsDbParams.getProductIdReceived());
                holder.txtProductDate.setText(productsDbParams.getDate_added());
            }
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred " + e.toString());
        }
    }

    @Override public int getItemCount() {
        return productsDbParamsArrayList.size();
    }

    public List<VariantDbParams> getProductVariants(Long productId){
        List<VariantDbParams> variantDbParams = DaoSessionSingleton.getDaoSession().getVariantDbParamsDao().queryBuilder().where(VariantDbParamsDao.Properties.Productid.eq(productId)).list();
        return variantDbParams;
    }

    public void setViewPager(ViewPager viewPager, List<VariantDbParams> variantDbParams){
        ArrayList<VariantDbParams> variantDbParamsArrayList= new ArrayList<>();
        variantDbParamsArrayList.addAll(variantDbParams);
        VariantPagerAdapter variantPagerAdapter = new VariantPagerAdapter(context, variantDbParamsArrayList);

        viewPager.setAdapter(variantPagerAdapter);
    }

    public void setRecyclerview(RecyclerView recyclerview, List<VariantDbParams> variantDbParams){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerview.setLayoutManager(linearLayoutManager);
        ArrayList<VariantDbParams> variantDbParamsArrayList= new ArrayList<>();
        variantDbParamsArrayList.addAll(variantDbParams);
        VariantAdapter variantAdapter = new VariantAdapter(variantDbParamsArrayList);
        recyclerview.setAdapter(variantAdapter);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtProductId, txtProductDate;
        CardView cardView;
        ViewPager viewPager;
        //RecyclerView recyclerView;
        MyViewHolder(View itemView) {
            super(itemView);
            txtProductDate =  itemView.findViewById(R.id.txtDateAdded);
            txtProductId = itemView.findViewById(R.id.txtId);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            cardView = itemView.findViewById(R.id.card_view);
            viewPager = itemView.findViewById(R.id.viewPager);
            //recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }
    public interface OnItemClickListener {

        void onItemClick(View view, ViewModel viewModel);

    }
}
