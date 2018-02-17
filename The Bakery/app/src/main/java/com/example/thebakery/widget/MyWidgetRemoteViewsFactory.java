package com.example.thebakery.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.thebakery.R;
import com.example.thebakery.provider.BakeryContractClass;

public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
    private Context myContext;
    private Cursor myCursor;

    public MyWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        myContext = applicationContext;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {

        if (myCursor != null) {
            myCursor.close();
        }

        SharedPreferences my_bakery_shared_preferences= myContext.getApplicationContext().getSharedPreferences("ingrad_pref",0);
        String bakery_dummy_variable= my_bakery_shared_preferences.getInt("selection",4)+"";

        final long clearCallingIdentity = Binder.clearCallingIdentity();
        myCursor = myContext.getContentResolver().query(BakeryContractClass.nameClass.CONTENT_URI,
                null,
                BakeryContractClass.nameClass._COLUMN_INGRED_KEY + " =?",
                new String[]{bakery_dummy_variable},
                null
        );

        Binder.restoreCallingIdentity(clearCallingIdentity);

    }

    @Override
    public void onDestroy() {
        if (myCursor != null) {
            myCursor.close();
        }
    }

    @Override
    public int getCount() {
        return myCursor == null ? 0 : myCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                myCursor == null || !myCursor.moveToPosition(position)) {
            return null;
        }

        RemoteViews bakery_remote_views = new RemoteViews(myContext.getPackageName(), R.layout.collection_bakery_widget_list_item);
        bakery_remote_views.setTextViewText(R.id.widgetItemBakeryLabel, myCursor.getString(2));
        bakery_remote_views.setTextViewText(R.id.bakery_quantity, myCursor.getString(4));
        bakery_remote_views.setTextViewText(R.id.bakery_measurement, myCursor.getString(3));

        Intent bakery_intent = new Intent();
        bakery_intent.putExtra(WidgetList.BAKERY_XTRA_NAME, myCursor.getString(2));
        bakery_remote_views.setOnClickFillInIntent(R.id.widgetItemContainer, bakery_intent);

        return bakery_remote_views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return myCursor.moveToPosition(position) ? myCursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

