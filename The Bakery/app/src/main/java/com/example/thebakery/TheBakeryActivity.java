package com.example.thebakery;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.thebakery.EmptyingBakeryResource.SimpleBakeryEmptingResource;
import com.example.thebakery.BakeryUtilities.BakeryNetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TheBakeryActivity extends AppCompatActivity {

    @BindView(R.id.bakery_progress) ProgressBar my_progress;
    @BindView(R.id.bakerygridRecyclerView) RecyclerView recipe_grid_recycler_view;

    public static boolean bakery_tabletSize =false;

    public static String [][] shortDescription;
    public static String [][] description;
    public static String [][] videoURL;
    public static String [][] measure;
    public static String [][] ingredient;
    public static String [][] step_id;
    public static String [] servings;
    public static String [] dishImage;
    public static String [][] quantity;
    public static String [][] thumbnailURL;
    public static int[] baking_numbers;
    public static int[] bakery_step_numbers;
    public static String [] bakery_dishNames;
    private URL my_url;
    private static int BAKERY_NUMBER_COLUMNS;

    @Nullable
    private SimpleBakeryEmptingResource emptingResource;
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (emptingResource == null) {
            emptingResource = new SimpleBakeryEmptingResource();
        }
        return emptingResource;
    }


    public class fetch_recipies extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            BakeryHomeAdapter homeAdapter =
                    new BakeryHomeAdapter(TheBakeryActivity.this);
            StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(BAKERY_NUMBER_COLUMNS, LinearLayoutManager.VERTICAL);
            my_progress.setVisibility(View.INVISIBLE);
            recipe_grid_recycler_view.setLayoutManager(gridLayoutManager);
            recipe_grid_recycler_view.setAdapter(homeAdapter);
            recipe_grid_recycler_view.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL my_url = urls[0];
            String res = null;
            try{
                res= BakeryNetworkUtils.getResponseFromHttpUrl(my_url);
                try {
                    final JSONArray jsonArray = new JSONArray(res);
                    bakery_step_numbers =new int[jsonArray.length()];
                    servings=new String[jsonArray.length()];
                    bakery_dishNames =new String[jsonArray.length()];
                    baking_numbers =new int[jsonArray.length()];
                    dishImage=new String[jsonArray.length()];
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jo=jsonArray.getJSONObject(i);
                        JSONArray jsonArray1=jo.getJSONArray("ingredients");
                        baking_numbers[i]=jsonArray1.length();
                        bakery_dishNames[i]=jo.getString("name");
                        servings[i]=jo.getString("servings");
                        dishImage[i]=jo.getString("image");
                        JSONArray jsonArray2=jo.getJSONArray("steps");
                        bakery_step_numbers[i]=jsonArray2.length();
                    }
                    int no_of_steps= baking_numbers[0];
                    for(int i = 0; i < baking_numbers.length; i++)
                        if(no_of_steps < baking_numbers[i])
                            no_of_steps = baking_numbers[i];
                    ingredient=new String[jsonArray.length()][no_of_steps];
                    measure=new String[jsonArray.length()][no_of_steps];
                    quantity=new String[jsonArray.length()][no_of_steps];
                    no_of_steps= bakery_step_numbers[0];
                    for(int i = 0; i < bakery_step_numbers.length; i++)
                        if(no_of_steps < bakery_step_numbers[i])
                            no_of_steps = bakery_step_numbers[i];
                    description=new String[jsonArray.length()][no_of_steps];
                    step_id=new String[jsonArray.length()][no_of_steps];
                    shortDescription=new String[jsonArray.length()][no_of_steps];
                    videoURL=new String[jsonArray.length()][no_of_steps];
                    thumbnailURL=new String[jsonArray.length()][no_of_steps];
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jo=jsonArray.getJSONObject(i);
                        JSONArray jsonArray1=jo.getJSONArray("ingredients");
                        for(int j=0;j<jsonArray1.length();j++){
                            JSONObject je=jsonArray1.getJSONObject(j);
                            ingredient[i][j]=je.getString("ingredient");
                            measure[i][j]=je.getString("measure");
                            quantity[i][j]=je.getString("quantity");

                        }
                        JSONArray jsonArray2=jo.getJSONArray("steps");
                        for (int j=0;j<jsonArray2.length();j++){
                            JSONObject jw=jsonArray2.getJSONObject(j);
                            thumbnailURL[i][j]=jw.getString("thumbnailURL");
                            videoURL[i][j]=jw.getString("videoURL");
                            step_id[i][j]=jw.getString("id");
                            description[i][j]=jw.getString("description");
                            shortDescription[i][j]=jw.getString("shortDescription");

                        }
                    }
                }catch (JSONException e){
                    Log.d("Message","JSON Parsing Exception");
                }
            }catch (IOException e){
                Log.d("Message","IO Network Exception");
            }
            return res;
        }


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager manager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo managerActiveNetworkInfo = manager.getActiveNetworkInfo();
        return managerActiveNetworkInfo != null && managerActiveNetworkInfo.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakery);
        ButterKnife.bind(this);
        bakery_tabletSize = getResources().getBoolean(R.bool.isTabletOrNotBool);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE || bakery_tabletSize)
            BAKERY_NUMBER_COLUMNS =2;
        if(bakery_tabletSize && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            BAKERY_NUMBER_COLUMNS =3;
        if(!bakery_tabletSize && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            BAKERY_NUMBER_COLUMNS =1;
        if(!isNetworkAvailable()) {
            my_progress.setVisibility(View.INVISIBLE);
            Toast.makeText(TheBakeryActivity.this,"No Network Available..",Toast.LENGTH_LONG).show();
        }else{
            try {
                my_url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
            } catch (Exception e) {
                Toast.makeText(TheBakeryActivity.this, "URL not Recognized..", Toast.LENGTH_LONG).show();
            }
            new fetch_recipies().execute(my_url);

        }
    }
}
