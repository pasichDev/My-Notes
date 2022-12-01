package com.pasich.mynotes.ui.view.activity;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.databinding.ItemNoteBinding;
import com.pasich.mynotes.databinding.NoteWidgetConfigureBinding;
import com.pasich.mynotes.ui.contract.NoteWidgetConfigureContract;
import com.pasich.mynotes.ui.presenter.NoteWidgetConfigurePresenter;
import com.pasich.mynotes.utils.adapters.NoteAdapter;
import com.pasich.mynotes.utils.constants.WidgetConstants;
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
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private NoteWidgetConfigureBinding binding;


    public NoteWidgetConfigureActivity() {
        super();
    }

    static void saveWidgetPref(int appWidgetId, long noteId) {
        PowerPreference.getDefaultFile().setLong(WidgetConstants.PREF_PREFIX_KEY + appWidgetId, noteId);

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
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
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
        mNoteAdapter.setOnItemClickListener((position, model) -> createWidget(model));
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

    void createWidget(Note note) {
        final Context context = NoteWidgetConfigureActivity.this;
        final Intent resultValue = new Intent();
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);


        saveWidgetPref(mAppWidgetId, note.id);
        NoteWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }


    public static long getIdNoteWidget(Context context, int appWidgetId) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(WidgetConstants.PREF_PREFIX_KEY + appWidgetId, 0);
    }
}