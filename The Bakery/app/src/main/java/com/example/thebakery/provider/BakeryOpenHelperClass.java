package com.example.thebakery.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.thebakery.TheBakeryActivity;

public class BakeryOpenHelperClass extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="recipe.db";

    private static final int DATABASE_VERSION = 1;

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ BakeryContractClass.nameClass._TABLE_NAME);
        onCreate(db);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_RECIPE="CREATE TABLE "+ BakeryContractClass.nameClass._TABLE_NAME +" (" +
                BakeryContractClass.nameClass._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                BakeryContractClass.nameClass._COLUMN_INGRED_KEY + " INTEGER,"+
                BakeryContractClass.nameClass._COLUMN_INGRED_VALUE +" VARCHAR,"+
                BakeryContractClass.nameClass._COLUMN_INGRED_QUANTITY +" VARCHAR,"+
                BakeryContractClass.nameClass._COLUMN_INGRED_MEASURE +" VARCHAR"+
                ");";

        String BAKERY_INSERT_PLACEHOLDER_VALUES;
        db.execSQL(CREATE_TABLE_RECIPE);
        for(int i = 0; i< TheBakeryActivity.bakery_dishNames.length; i++){
            for(int j = 0; j< TheBakeryActivity.ingredient[i].length; j++){
                if(TheBakeryActivity.ingredient[i][j]!=null) {
                    BAKERY_INSERT_PLACEHOLDER_VALUES="INSERT INTO "+ BakeryContractClass.nameClass._TABLE_NAME +
                            " ("+ BakeryContractClass.nameClass._COLUMN_INGRED_KEY +"," +
                            BakeryContractClass.nameClass._COLUMN_INGRED_VALUE +"," +
                            BakeryContractClass.nameClass._COLUMN_INGRED_QUANTITY +","+
                            BakeryContractClass.nameClass._COLUMN_INGRED_MEASURE +") VALUES("+i+", '"+ TheBakeryActivity.ingredient[i][j]+"','"+ TheBakeryActivity.measure[i][j]+"','"+ TheBakeryActivity.quantity[i][j]+"');";
                    db.execSQL(BAKERY_INSERT_PLACEHOLDER_VALUES);
                }
            }
        }
    }

    public BakeryOpenHelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
