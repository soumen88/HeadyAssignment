package com.example.heady.headyassignment.categories;

import android.content.Context;
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

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>{
    private static String TAG = CategoriesAdapter.class.getSimpleName();
    private ArrayList<CategoryDbParams> categoryParametersArrayList;
    private SelectCategoryListener selectCategoryListener;
    private Context context;

    public CategoriesAdapter(ArrayList<CategoryDbParams> categoryParametersArrayList, SelectCategoryListener selectCategoryListener, Context context) {
        this.categoryParametersArrayList = new ArrayList<>();
        this.categoryParametersArrayList = categoryParametersArrayList;
        this.selectCategoryListener = selectCategoryListener;
        this.context  = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        View v = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new MyViewHolder(v);
    }
    public void setnewCategorylist(ArrayList<CategoryDbParams> categoryParametersArrayList) {
        this.categoryParametersArrayList = new ArrayList<>();
        this.categoryParametersArrayList = categoryParametersArrayList;
        notifyDataSetChanged();
    }

    public interface SelectCategoryListener{
        void selectedItem(CategoryDbParams categoryDbParams);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        try {
            CategoryDbParams categoryDbParams = categoryParametersArrayList.get(position);
            if(categoryDbParams != null){
                holder.txtId.setText(" ID " + categoryDbParams.getCategoryId());
                holder.txtName.setText(categoryDbParams.getName());
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(selectCategoryListener != null){
                            selectCategoryListener.selectedItem(categoryParametersArrayList.get(position));
                        }
                    }
                });

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(selectCategoryListener != null){
                            selectCategoryListener.selectedItem(categoryParametersArrayList.get(position));
                        }
                    }
                });
            }
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception occurred in bind view " + e.toString());
        }
    }

    @Override
    public int getItemCount() {
        if(categoryParametersArrayList.size() > 0){
            return categoryParametersArrayList.size();
        }
        else {
            return 0;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtId, txtName;
        ImageView imageView;
        CardView cardView;
        MyViewHolder(View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.thumbnail);
            txtId = itemView.findViewById(R.id.txtId);
            txtName = itemView.findViewById(R.id.txtName);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
