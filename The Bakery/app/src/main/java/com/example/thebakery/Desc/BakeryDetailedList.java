package com.example.thebakery.Desc;

import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.thebakery.TheBakeryActivity;
import com.example.thebakery.R;
import com.example.thebakery.Bakeryservices.BakeryReplacingTitleService;
import com.example.thebakery.widget.WidgetList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BakeryDetailedList extends AppCompatActivity {

    private static final String SELECTED_BAKERY_FRAGMENT_STATE = "Selects";
    private SharedPreferences my_shared_preferences;
    public static int id;
    @BindView(R.id.bakery_fab) FloatingActionButton mybakeryfab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_list);
        id =getIntent().getExtras().getInt("id");
        ButterKnife.bind(this);

        if(savedInstanceState==null||true) {
            my_shared_preferences =getApplicationContext().getSharedPreferences("ingrad_pref",0);
            final SharedPreferences.Editor my_editor= my_shared_preferences.edit();

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(TheBakeryActivity.bakery_dishNames[id]);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            if (id == my_shared_preferences.getInt("selection", 4)) {
                mybakeryfab.setImageDrawable(ContextCompat.getDrawable(BakeryDetailedList.this, R.drawable.ic_favorite_icon_white_24px));
            } else {
                mybakeryfab.setImageDrawable(ContextCompat.getDrawable(BakeryDetailedList.this, R.drawable.ic_favorite_icon_border_white_24px));
            }

            if (TheBakeryActivity.bakery_tabletSize) {
                BakeryIngredFragmentContent ingred_fragment_contents_1 = new BakeryIngredFragmentContent();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.bakery_container_1, ingred_fragment_contents_1)
                        .commit();
                StepBakeryFragmentContent ingred_fragment_contents_2 = new StepBakeryFragmentContent();
                fragmentManager.beginTransaction()
                        .add(R.id.bakery_container_2, ingred_fragment_contents_2)
                        .commit();

            } else {

                BakeryIngredFragmentContent ingred_fragment_contents_1 = new BakeryIngredFragmentContent();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.bakery_container_1, ingred_fragment_contents_1)
                        .commit();
                StepBakeryFragmentContent ingred_fragment_contents_2 = new StepBakeryFragmentContent();
                fragmentManager.beginTransaction()
                        .add(R.id.bakery_container_2, ingred_fragment_contents_2)
                        .commit();
            }

            mybakeryfab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!(id == my_shared_preferences.getInt("selection", 4))) {
                        my_editor.putInt("selection", id);
                        my_editor.putString("dishName", TheBakeryActivity.bakery_dishNames[id]);
                        my_editor.commit();
                        BakeryReplacingTitleService.startChanging(BakeryDetailedList.this);
                        WidgetList.sendRefreshBroadcast(BakeryDetailedList.this);
                        mybakeryfab.setImageDrawable(ContextCompat.getDrawable(BakeryDetailedList.this, R.drawable.ic_favorite_icon_white_24px));
                    } else {
                        my_editor.putInt("selection", 4);
                        my_editor.putString("dishName", "NO ITEMS TO SHOW");
                        my_editor.commit();
                        BakeryReplacingTitleService.startChanging(BakeryDetailedList.this);
                        WidgetList.sendRefreshBroadcast(BakeryDetailedList.this);
                        mybakeryfab.setImageDrawable(ContextCompat.getDrawable(BakeryDetailedList.this, R.drawable.ic_favorite_icon_border_white_24px));
                    }
                }
            });
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SELECTED_BAKERY_FRAGMENT_STATE,true);
    }
}
