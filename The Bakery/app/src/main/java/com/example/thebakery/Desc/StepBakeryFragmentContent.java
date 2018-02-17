package com.example.thebakery.Desc;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thebakery.TheBakeryActivity;
import com.example.thebakery.R;
import com.example.thebakery.BakerySteps.FullBakeryDescription;
import com.example.thebakery.BakerySteps.FullBakeryDescriptionFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepBakeryFragmentContent extends Fragment
        implements StepsBakeryAdapter.ListItemClickListener{

    @BindView(R.id.bakery_rview_ingredients) RecyclerView bakeryIngredList;
    private Unbinder my_unbinder;
    private StepsBakeryAdapter myAdapter;
    private int bakery_number_of_clicks;
    public static int bakery_current_selection;
    private static final String TAG = StepBakeryFragmentContent.class.getSimpleName();
    public static int my_bakery_ultimateFlag;

    public StepBakeryFragmentContent() {
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        bakery_number_of_clicks =1;
        bakery_current_selection =clickedItemIndex;
        if(TheBakeryActivity.bakery_tabletSize){
            FullBakeryDescriptionFragment.bakery_temp_selection = clickedItemIndex;
            FullBakeryDescriptionFragment.bakery_temp_flag = my_bakery_ultimateFlag;
            FullBakeryDescriptionFragment.video_playbackPosition = 0;
            FullBakeryDescriptionFragment fullBakeryDescriptionFragment = new FullBakeryDescriptionFragment();
            FragmentManager fragmentManager = getFragmentManager();
            if(bakery_number_of_clicks == 0) {
                fragmentManager.beginTransaction()
                        .add(R.id.bakery_main_desc, fullBakeryDescriptionFragment)
                        .commit();
            }else{
                fragmentManager.beginTransaction()
                        .replace(R.id.bakery_main_desc, fullBakeryDescriptionFragment)
                        .commit();
            }
        }else {
            Intent intent = new Intent(getActivity(), FullBakeryDescription.class);
            FullBakeryDescriptionFragment.bakery_temp_selection =clickedItemIndex;
            FullBakeryDescriptionFragment.bakery_temp_flag = my_bakery_ultimateFlag;
            startActivity(intent);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredient_bakery_item, container, false);
        my_unbinder = ButterKnife.bind(this,rootView);
        int my_flag=0;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        bakeryIngredList.setLayoutManager(layoutManager);
        bakery_number_of_clicks =0;
        bakeryIngredList.setHasFixedSize(true);
        for(int i = 0; i< TheBakeryActivity.shortDescription[BakeryDetailedList.id].length; i++){
            if(TheBakeryActivity.shortDescription[BakeryDetailedList.id][i]==null){
                break;
            }else{
                my_flag+=1;
                my_bakery_ultimateFlag =my_flag;
            }
        }

        myAdapter = new StepsBakeryAdapter(BakeryDetailedList.id,my_flag,this);
        bakeryIngredList.setAdapter(myAdapter);

        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        my_unbinder.unbind();
    }


}
