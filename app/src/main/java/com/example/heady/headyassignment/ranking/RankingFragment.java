package com.example.heady.headyassignment.ranking;

import android.arch.lifecycle.Observer;
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
import com.example.heady.headyassignment.displayRanking.DisplayRankingActivity;
import com.example.heady.headyassignment.displayproducts.DisplayProductsActivity;
import com.example.heady.headyassignment.logactivity.LogActivity;
import com.example.heady.headyassignment.model.CategoryDbParams;
import com.example.heady.headyassignment.model.RankingDbParams;
import com.example.heady.headyassignment.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class RankingFragment extends BaseFragment implements RankingAdapter.SelectRankingListener{

    private static String TAG = RankingFragment.class.getSimpleName();
    private RankingAdapter rankingAdapter;
    private RecyclerView recyclerView;
    private ArrayList<RankingDbParams> rankingDbParamsArrayList;
    private RankingDataViewModel rankingDataViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ranking_category, container, false);
        return view;
    }

    @Override
    public void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(dpToPx(5), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rankingAdapter);
        rankingDataViewModel.getRankings().observe(getActivity(), new Observer<List<RankingDbParams>>() {
            @Override
            public void onChanged(@Nullable List<RankingDbParams> rankingDbParamsList) {
                if(rankingDbParamsList != null){
                    LogActivity.log(TAG , "Ranking db is not null");
                    rankingDbParamsArrayList.addAll(rankingDbParamsList);
                    rankingAdapter.setnewRankinglist(rankingDbParamsArrayList);
                }
            }
        });
    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static List<RankingDbParams> getRanking(){
        try {
            List<RankingDbParams> rankingDbParamsList = DaoSessionSingleton.getDaoSession().getRankingDbParamsDao().queryBuilder().list();
            if( rankingDbParamsList != null){
                return rankingDbParamsList;
            }
        }
        catch (Exception e){
            LogActivity.log(TAG , "Exception " + e.toString());
        }
        return null;
    }


    @Override
    public void init() {
        rankingDbParamsArrayList = new ArrayList<>();
        rankingAdapter = new RankingAdapter(rankingDbParamsArrayList, this);
        rankingDataViewModel = ViewModelProviders.of(getActivity()).get(RankingDataViewModel.class);
    }

    @Override
    public void selectedItem(RankingDbParams rankingDbParams) {
        LogActivity.log(TAG , "Selected item " + rankingDbParams.getRankingId());
        Intent i = new Intent(getActivity(), DisplayRankingActivity.class);
        i.putExtra("ranking_id" , rankingDbParams.getRankingId());
        startActivity(i);
    }
}
