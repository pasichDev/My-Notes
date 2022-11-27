package com.pasich.mynotes.ui.view.activity;


import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.databinding.ItemNoteBinding;
import com.pasich.mynotes.databinding.NoteWidgetConfigureBinding;
import com.pasich.mynotes.ui.contract.NoteWidgetConfigureContract;
import com.pasich.mynotes.ui.presenter.NoteWidgetConfigurePresenter;
import com.pasich.mynotes.utils.adapters.NoteAdapter;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;
import com.pasich.mynotes.widgets.noteWidget.NoteWidget;
import com.preference.PowerPreference;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


public class NoteWidgetConfigureActivity extends BaseActivity implements NoteWidgetConfigureContract.view {

    @Named("NotesItemSpaceDecoration")
    @Inject
    public SpacesItemDecoration itemDecorationNotes;
    @Inject
    public NoteWidgetConfigurePresenter mPresenter;
    @Inject
    public NoteAdapter<ItemNoteBinding> mNoteAdapter;
    @Inject
    public StaggeredGridLayoutManager staggeredGridLayoutManager;

    private NoteWidgetConfigureBinding binding;
    private static final String PREF_PREFIX_KEY = "noteWidgetId_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;


    public NoteWidgetConfigureActivity() {
        super();
    }


    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    public static String loadTitlePref(Context context, int appWidgetId) {
        // SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        //   String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        //   if (titleValue != null) {
        //       return titleValue;
        //   } else {
        //       return context.getString(R.string.appwidget_text);
        //
        return "";
    }

    public static void deleteTitlePref(Context context, int appWidgetId) {
        //   SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        //  prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        //    prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setResult(RESULT_CANCELED);
        binding = NoteWidgetConfigureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mPresenter.viewIsReady();



        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void initListeners() {
        mNoteAdapter.setOnItemClickListener((position, model) -> {
            createWidget(model.id);
        });
    }

    @Override
    public void initListNotes() {
        binding.listNotes.addItemDecoration(itemDecorationNotes);
        binding.listNotes.setLayoutManager(staggeredGridLayoutManager);
        binding.listNotes.setAdapter(mNoteAdapter);


    }

    @Override
    public void loadingNotes(List<Note> noteList) {
        mNoteAdapter.sortList(noteList, "", "allNotes");
    }

    void createWidget(long noteId) {
        final Context context = NoteWidgetConfigureActivity.this;

        saveWidgetPref(mAppWidgetId, noteId);
        // It is the responsibility of the configuration activity to update the app widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        NoteWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    static void saveWidgetPref(int appWidgetId, long noteId) {
        PowerPreference.getDefaultFile().setLong(PREF_PREFIX_KEY + appWidgetId, noteId);

    }


}