package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.utils.actionPanel.ActionUtils.getAction;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_FORMAT_VALUE;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.databinding.ActivityTrashBinding;
import com.pasich.mynotes.databinding.ItemNoteTrashBinding;
import com.pasich.mynotes.ui.contract.TrashContract;
import com.pasich.mynotes.ui.presenter.TrashPresenter;
import com.pasich.mynotes.ui.view.dialogs.trash.CleanTrashDialog;
import com.pasich.mynotes.utils.actionPanel.ActionUtils;
import com.pasich.mynotes.utils.actionPanel.interfaces.ManagerViewAction;
import com.pasich.mynotes.utils.actionPanel.tool.TrashNoteActionTool;
import com.pasich.mynotes.utils.adapters.TrashAdapter;
import com.pasich.mynotes.utils.adapters.baseGenericAdapter.OnItemClickListener;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Flowable;


public class TrashActivity extends BaseActivity implements TrashContract.view, ManagerViewAction<TrashNote> {

    @Inject
    public TrashPresenter trashPresenter;
    @Inject
    public ActivityTrashBinding binding;

    @Inject
    public TrashNoteActionTool trashNoteActionTool;

    @Inject
    public TrashAdapter<ItemNoteTrashBinding> mNotesTrashAdapter;

    @Named("ActionUtilsTrash")
    @Inject
    public ActionUtils actionUtils;

    @Named("NotesItemSpaceDecoration")
    @Inject
    public SpacesItemDecoration itemDecorationNotes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        trashPresenter.attachView(this);
        trashPresenter.viewIsReady();
        binding.setPresenter((TrashPresenter) trashPresenter);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void initListeners() {
        mNotesTrashAdapter.setOnItemClickListener(new OnItemClickListener<TrashNote>() {
            @Override
            public void onClick(int position, TrashNote model) {
                selectItemAction(model, position);
            }

            @Override
            public void onLongClick(int position, TrashNote model) {

            }
        });

    }

    @Override
    public void settingsActionBar() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        trashPresenter.detachView();
        if (isFinishing()) {

            mNotesTrashAdapter.setOnItemClickListener(null);
            trashPresenter.destroy();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getAction()) actionUtils.closeActionPanel();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            if (getAction()) {
                actionUtils.closeActionPanel();
            } else finish();
        }

        return true;
    }


    @Override
    public void settingsNotesList(Flowable<List<TrashNote>> noteList) {
        binding.ListTrash.addItemDecoration(itemDecorationNotes);
        binding.ListTrash.setLayoutManager(new StaggeredGridLayoutManager(ARGUMENT_DEFAULT_FORMAT_VALUE, LinearLayoutManager.VERTICAL));
        binding.ListTrash.setAdapter(mNotesTrashAdapter);

        trashPresenter.getCompositeDisposable().add(noteList.subscribeOn(trashPresenter.getSchedulerProvider().io()).subscribe(trashNotes -> {
            mNotesTrashAdapter.sortListTrash(trashNotes);
            if (trashNotes.size() == 0) runOnUiThread(this::showEmptyTrash);
        }, throwable -> Log.wtf("MyNotes", "settingsNotesList", throwable)));
    }


    private void showEmptyTrash() {
        binding.setEmptyNotesTrash(true);
        binding.includeEmpty.emptyViewTrash.setVisibility(View.VISIBLE);
    }

    @Override
    public void cleanTrashDialogShow() {
        new CleanTrashDialog().show(getSupportFragmentManager(), "CLeanTrash");
    }

    @Override
    public void initActionUtils() {
        actionUtils.setTrash();
    }


    @Override
    public void activateActionPanel() {
        binding.cleanTrash.setVisibility(View.GONE);
    }

    @Override
    public void deactivationActionPanel() {
        binding.cleanTrash.setVisibility(View.VISIBLE);
    }

    @Override
    public void toolCleanChecked() {
        trashNoteActionTool.checkedClean();
    }


    @Override
    public void deleteNotes() {

    }

    @Override
    public void shareNotes() {

    }

    @Override
    public void restoreNotes() {
        trashPresenter.restoreNotesArray(trashNoteActionTool.getArrayChecked());
        actionUtils.closeActionPanel();
    }

    @Override
    public void selectItemAction(TrashNote note, int position) {
        if (note.getChecked()) {
            note.setChecked(false);
            if (!trashNoteActionTool.isCheckedItemFalse(note)) actionUtils.closeActionPanel();
        } else {
            trashNoteActionTool.isCheckedItem(note);
            note.setChecked(true);
        }

        actionUtils.manageActionPanel(trashNoteActionTool.getCountCheckedItem());
        mNotesTrashAdapter.notifyItemChanged(position, 22);
    }
}
