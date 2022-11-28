package com.pasich.mynotes.widgets.noteWidget;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.utils.constants.WidgetConstants;
import com.preference.PowerPreference;

public class NoteWidget extends AppWidgetProvider {


    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId, Note note) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.note_widget);


        String title = note.getTitle();
        String value = note.getValue();

        views.setViewVisibility(R.id.widget_note_title, title.length() >= 2 ? View.VISIBLE : View.GONE);
        views.setTextViewText(R.id.widget_note_title, title != null && title.length() >= 2 ? title : "");
        views.setTextViewText(R.id.widget_note_value, value != null && value.length() >= 2 ? value : "");

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            PowerPreference.getDefaultFile().remove(WidgetConstants.PREF_PREFIX_KEY + appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {

    }
}