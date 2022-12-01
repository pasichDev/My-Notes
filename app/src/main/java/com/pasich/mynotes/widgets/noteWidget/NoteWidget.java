package com.pasich.mynotes.widgets.noteWidget;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.DatabaseWidget;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.ui.view.activity.NoteActivity;
import com.pasich.mynotes.ui.view.activity.NoteWidgetConfigureActivity;
import com.pasich.mynotes.utils.constants.WidgetConstants;
import com.preference.PowerPreference;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NoteWidget extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        final long idNote = NoteWidgetConfigureActivity.getIdNoteWidget(context, appWidgetId);
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.note_widget);
        final Intent intent = new Intent(context, NoteActivity.class).putExtra("NewNote", false).putExtra("idNote", idNote).putExtra("shareText", "").putExtra("tagNote", "");
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Note mNote = null;

        try {
            mNote = getNoteFromId(idNote, context);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if (idNote != 0 && mNote != null && mNote.id != 0) {


            String title = mNote.getTitle();
            String value = mNote.getValue();
            views.setViewVisibility(R.id.widget_note_title, title.length() >= 2 ? View.VISIBLE : View.GONE);
            views.setTextViewText(R.id.widget_note_title, title.length() >= 2 ? title : "");
            views.setTextViewText(R.id.widget_note_value, value != null && value.length() >= 2 ? value : "");
        }
        views.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static Note getNoteFromId(long idNote, Context context) throws ExecutionException, InterruptedException {
        Future<?> future = Executors.newSingleThreadExecutor().submit((Callable<?>) () -> DatabaseWidget.getInstance(context).noteDao().getNoteForId(idNote));
        return (Note) future.get();
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
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