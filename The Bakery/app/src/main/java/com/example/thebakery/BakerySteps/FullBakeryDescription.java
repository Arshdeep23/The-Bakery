package com.example.thebakery.BakerySteps;

import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.thebakery.Desc.BakeryDetailedList;
import com.example.thebakery.TheBakeryActivity;
import com.example.thebakery.R;

public class FullBakeryDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_bakery_description);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle(TheBakeryActivity.bakery_dishNames[BakeryDetailedList.id]);
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            getSupportActionBar().hide();
        }else{
            getSupportActionBar().show();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FullBakeryDescriptionFragment fullBakeryDescriptionFragment =new FullBakeryDescriptionFragment();
        fragmentManager.beginTransaction().replace(R.id.bakery_main_desc, fullBakeryDescriptionFragment).commit();
    }

}
