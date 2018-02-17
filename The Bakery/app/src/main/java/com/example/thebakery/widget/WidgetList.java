package com.example.thebakery.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.thebakery.R;
import com.example.thebakery.Bakeryservices.BakeryReplacingTitleService;

public class WidgetList extends AppWidgetProvider {

    public static final String BAKERY_XTRA_NAME = "TASK_TEXT";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,int appWidgetId) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_bakery_list);
        SharedPreferences ingrad_pref=context.getSharedPreferences("ingrad_pref",0);
        String dishName=ingrad_pref.getString("dishName","MR.BAKER");

        remoteViews.setTextViewText(R.id.button_widget_title,dishName+"");
        Intent intent = new Intent(context, MyWidgetRemoteViewsService.class);
        remoteViews.setRemoteAdapter(R.id.button_widget_list_view, intent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        BakeryReplacingTitleService.startChanging(context);
    }


    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, WidgetList.class));
        context.sendBroadcast(intent);
    }

    public static void updateWidgetTitle(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, WidgetList.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.button_widget_list_view);
        }
        super.onReceive(context, intent);
    }
}

