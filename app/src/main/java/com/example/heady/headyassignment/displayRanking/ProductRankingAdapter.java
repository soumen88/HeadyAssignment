package com.example.heady.headyassignment.displayRanking;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.heady.headyassignment.R;
import com.example.heady.headyassignment.logactivity.LogActivity;
import com.example.heady.headyassignment.model.ProductRankingDbParams;
import com.example.heady.headyassignment.model.ProductRankingItems;

import java.util.ArrayList;

public class ProductRankingAdapter extends RecyclerView.Adapter<ProductRankingAdapter.MyViewHolder> {

    private static String TAG = ProductRankingAdapter.class.getSimpleName();
    private ArrayList<ProductRankingItems> productRankingItemsArrayList;

    public ProductRankingAdapter(ArrayList<ProductRankingItems> productRankingItemsArrayList) {
        this.productRankingItemsArrayList = productRankingItemsArrayList;
    }

    public void setNewProductData(ArrayList<ProductRankingItems> productRankingItemsArrayList) {
        LogActivity.log(TAG , "Setting new product data ");
        this.productRankingItemsArrayList = new ArrayList<>();
        this.productRankingItemsArrayList = productRankingItemsArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_ranking_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            ProductRankingItems productRankingItems = productRankingItemsArrayList.get(position);
            if(productRankingItems != null){
                LogActivity.log(TAG , "Name passed " + productRankingItems.getProductName());
                holder.txtProductDate.setText( getDate(productRankingItems.getDate()) );
                holder.txtProductName.setText(productRankingItems.getProductName());
                holder.txtProductCategory.setText(productRankingItems.getCategoryName());
                holder.txtProductId.setText("ID "+ productRankingItems.getProductId());
                holder.txtProductCount.setText("Count " + productRankingItems.getCount());
            }
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred " + e.toString());
        }
    }

    private String getDate(String date){
        try {
            String[] arr = date.split("T", 0);
            return  arr[0];
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred in splitting date " + e.toString());
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if(productRankingItemsArrayList.size()>0){
            return productRankingItemsArrayList.size();
        }
        else {
            return 0;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductId, txtProductDate,txtProductName, txtProductCategory, txtProductCount ;
        CardView cardView;
        MyViewHolder(View itemView) {
            super(itemView);
            txtProductId =  itemView.findViewById(R.id.txtProductId);
            txtProductDate = itemView.findViewById(R.id.txtProductDate);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductCategory = itemView.findViewById(R.id.txtProductCategory);
            txtProductCount = itemView.findViewById(R.id.txtProductCount);
            cardView = itemView.findViewById(R.id.product_card_view);
        }
    }

}
