package com.example.heady.headyassignment.displayproducts;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heady.headyassignment.R;
import com.example.heady.headyassignment.logactivity.LogActivity;
import com.example.heady.headyassignment.model.CategoryDbParams;
import com.example.heady.headyassignment.model.CategoryParameters;
import com.example.heady.headyassignment.model.VariantDbParams;

import java.util.ArrayList;

public class VariantAdapter extends RecyclerView.Adapter<VariantAdapter.MyViewHolder>{
    private static String TAG = VariantAdapter.class.getSimpleName();
    private ArrayList<VariantDbParams> variantDbParamsArrayList;
    private SelectCategoryListener selectCategoryListener;

    public VariantAdapter(ArrayList<VariantDbParams> variantDbParamsArrayList) {
        this.variantDbParamsArrayList = new ArrayList<>();
        this.variantDbParamsArrayList = variantDbParamsArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.variant_item, parent, false);
        return new MyViewHolder(v);
    }
    public void setnewCategorylist(ArrayList<VariantDbParams> variantDbParamsArrayList) {
        this.variantDbParamsArrayList = new ArrayList<>();
        this.variantDbParamsArrayList = variantDbParamsArrayList;
        notifyDataSetChanged();
    }

    public interface SelectCategoryListener{
        void selectedItem(CategoryDbParams categoryDbParams);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        try {
            VariantDbParams variantDbParams = variantDbParamsArrayList.get(position);
            if(variantDbParams != null){
                holder.txtVariantColor.setText(variantDbParams.getColor());
                holder.txtVariantPrice.setText("Price: " + variantDbParams.getPrice());
                holder.txtVariantSize.setText("Size " + variantDbParams.getSize());
                holder.txtVariantId.setText("ID " + variantDbParams.getVariantIdReceived());
            }
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred in bind view " + e.toString());
        }
    }

    @Override
    public int getItemCount() {
        if(variantDbParamsArrayList.size() > 0){
            return variantDbParamsArrayList.size();
        }
        else {
            return 0;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtVariantId, txtVariantSize, txtVariantPrice, txtVariantColor;

        CardView cardView;
        MyViewHolder(View itemView) {
            super(itemView);
            txtVariantId = itemView.findViewById(R.id.txtVariantId);
            txtVariantSize = itemView.findViewById(R.id.txtVariantSize);
            txtVariantPrice = itemView.findViewById(R.id.txtVariantPrice);
            txtVariantColor = itemView.findViewById(R.id.txtVariantColor);
            cardView = itemView.findViewById(R.id.variant_card_view);
        }
    }
}
