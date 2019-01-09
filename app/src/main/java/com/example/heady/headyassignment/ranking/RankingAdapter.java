package com.example.heady.headyassignment.ranking;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heady.headyassignment.R;
import com.example.heady.headyassignment.categories.CategoriesAdapter;
import com.example.heady.headyassignment.logactivity.LogActivity;
import com.example.heady.headyassignment.model.CategoryDbParams;
import com.example.heady.headyassignment.model.RankingDbParams;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder>{
    private static String TAG = RankingAdapter.class.getSimpleName();
    private ArrayList<RankingDbParams> rankingDbParamsArrayList;
    private SelectRankingListener selectRankingListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item, parent, false);
        return new MyViewHolder(v);
    }

    public RankingAdapter(ArrayList<RankingDbParams> rankingDbParamsArrayList, SelectRankingListener selectRankingListener) {
        this.rankingDbParamsArrayList = rankingDbParamsArrayList;
        this.selectRankingListener = selectRankingListener;
    }

    public void setnewRankinglist(ArrayList<RankingDbParams> rankingDbParamsArrayList) {
        this.rankingDbParamsArrayList = new ArrayList<>();
        this.rankingDbParamsArrayList = rankingDbParamsArrayList;
        notifyDataSetChanged();
    }


    public interface SelectRankingListener{
        void selectedItem(RankingDbParams rankingDbParams);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        try {
            RankingDbParams rankingDbParams = rankingDbParamsArrayList.get(position);
            if(rankingDbParams!= null){
                holder.txtRankingType.setText(rankingDbParams.getRanking_type());
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(selectRankingListener != null){
                            selectRankingListener.selectedItem(rankingDbParamsArrayList.get(position));
                        }
                    }
                });
            }
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception Occurred in binding data");
        }
    }

    @Override
    public int getItemCount() {
        if(rankingDbParamsArrayList.size() > 0){
            return rankingDbParamsArrayList.size();
        }
        else {
            return 0;
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtRankingType;
        CardView cardView;
        MyViewHolder(View itemView) {
            super(itemView);
            txtRankingType = itemView.findViewById(R.id.txtRankingType);
            cardView = itemView.findViewById(R.id.ranking_card_view);
        }
    }
}
