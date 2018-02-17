package com.example.thebakery.Desc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thebakery.TheBakeryActivity;
import com.example.thebakery.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BakeryIngredFragmentContent extends Fragment{
    private Unbinder my_unbinder;
    @BindView(R.id.bakery_rview_ingredients) RecyclerView recipeIngredList;
    private BakeryIngredAdapter myAdapter;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        my_unbinder.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredient_bakery_item, container, false);
        my_unbinder = ButterKnife.bind(this,rootView);

        LinearLayoutManager bakery_layoutManager = new LinearLayoutManager(getContext());
        recipeIngredList.setLayoutManager(bakery_layoutManager);

        recipeIngredList.setHasFixedSize(true);

        int flag=0;
        for(int i = 0; i< TheBakeryActivity.ingredient[BakeryDetailedList.id].length; i++){
            if(TheBakeryActivity.ingredient[BakeryDetailedList.id][i]==null){
                break;
            }else{
                flag+=1;
            }
        }

        myAdapter = new BakeryIngredAdapter(BakeryDetailedList.id,flag);
        recipeIngredList.setAdapter(myAdapter);
        return rootView;
    }

    public BakeryIngredFragmentContent() {
    }
}
