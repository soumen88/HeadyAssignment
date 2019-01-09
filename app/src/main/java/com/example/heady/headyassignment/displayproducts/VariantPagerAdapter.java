package com.example.heady.headyassignment.displayproducts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.heady.headyassignment.R;
import com.example.heady.headyassignment.logactivity.LogActivity;
import com.example.heady.headyassignment.model.VariantDbParams;

import java.util.ArrayList;

public class VariantPagerAdapter extends PagerAdapter {

    private static String TAG = VariantPagerAdapter.class.getSimpleName();
    private ArrayList<VariantDbParams> variantDbParamsArrayList;
    private LayoutInflater layoutInflater;
    private Context context;

    public VariantPagerAdapter(Context context , ArrayList<VariantDbParams> variantDbParamsArrayList) {
        this.variantDbParamsArrayList = variantDbParamsArrayList;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return variantDbParamsArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.variant_item, container, false);
        container.addView(itemView);
        try {
            TextView txtVariantId = itemView.findViewById(R.id.txtVariantId);
            TextView txtVariantSize = itemView.findViewById(R.id.txtVariantSize);
            TextView txtVariantPrice = itemView.findViewById(R.id.txtVariantPrice);
            TextView txtVariantColor = itemView.findViewById(R.id.txtVariantColor);
            CardView cardView = itemView.findViewById(R.id.variant_card_view);
            VariantDbParams variantDbParams = variantDbParamsArrayList.get(position);

            if(variantDbParams != null){
                txtVariantColor.setText(variantDbParams.getColor());
                txtVariantPrice.setText("Price: " + variantDbParams.getPrice());
                txtVariantSize.setText("Size " + variantDbParams.getSize());
                txtVariantId.setText("ID " + variantDbParams.getVariantIdReceived());
            }
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception " + e.toString());
        }
        return itemView;
    }
}
