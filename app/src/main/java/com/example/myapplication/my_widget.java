package com.example.myapplication;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.myapplication.Database.Storages.UserStorage;

public class my_widget extends AppWidgetProvider {

    final static String LOG_TAG = "myLogs";
    private static UserStorage userStorage;

    public static String REFRESH_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";
    static private RemoteViews views;

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(LOG_TAG, "onEnabled");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int id : appWidgetIds) {
            updateWidget(context, appWidgetManager, id);
        }
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(LOG_TAG, "onDisabled");
    }

    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int widgetID)
    {
        if (userStorage == null) {
            userStorage = new UserStorage(context);
        }
        CharSequence widgetText = "Количество пользователей в бд: " + userStorage.getElementCount();

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        views.setTextViewText(R.id.tv, widgetText);

        Intent intent = new Intent(context, my_widget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(new ComponentName(context, my_widget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

        PendingIntent pIntent = PendingIntent.getBroadcast(context, widgetID, intent, PendingIntent.FLAG_IMMUTABLE);
        views.setPendingIntentTemplate(R.id.tv, pIntent);

        context.sendBroadcast(intent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(widgetID, views);
    }
}