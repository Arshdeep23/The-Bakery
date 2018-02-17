package com.example.thebakery.Bakeryservices;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.thebakery.widget.WidgetList;

public class BakeryReplacingTitleService extends IntentService {

    public static final String BAKERY_ACT_CHANGE_TITLE = "CHANGE TITLE";

    public void poplate_updated_widget() {
        AppWidgetManager bakery_app_widget_manager = AppWidgetManager.getInstance(this);
        int[] app_widget_ids = bakery_app_widget_manager.getAppWidgetIds(new ComponentName(this, WidgetList.class));
        WidgetList.updateWidgetTitle(this,bakery_app_widget_manager,app_widget_ids);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String bakery_action = intent.getAction();
            if (BAKERY_ACT_CHANGE_TITLE.equals(bakery_action)) {
                poplate_updated_widget();
            }
        }
    }

    public static void startChanging(Context context){
        Intent myIntent = new Intent(context, BakeryReplacingTitleService.class);
        myIntent.setAction(BAKERY_ACT_CHANGE_TITLE);
        context.startService(myIntent);
    }

    public BakeryReplacingTitleService() {
        super("BakeryReplacingTitleService");
    }

}
