package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.utils.actionPanel.ActionUtils.getAction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.data.old.DataManager;
import com.pasich.mynotes.databinding.ActivityTrashBinding;
import com.pasich.mynotes.databinding.ItemNoteTrashBinding;
import com.pasich.mynotes.ui.contract.TrashContract;
import com.pasich.mynotes.ui.presenter.TrashPresenter;
import com.pasich.mynotes.utils.actionPanel.ActionUtils;
import com.pasich.mynotes.utils.actionPanel.interfaces.ManagerViewAction;
import com.pasich.mynotes.utils.actionPanel.tool.TrashNoteActionTool;
import com.pasich.mynotes.utils.adapters.TrashAdapter;
import com.pasich.mynotes.utils.adapters.baseGenericAdapter.OnItemClickListener;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtilTrash;

import java.util.List;
import java.util.Objects;


public class TrashActivity extends BaseActivity implements TrashContract.view, ManagerViewAction<TrashNote> {

    private ActivityTrashBinding binding;
    private TrashAdapter<ItemNoteTrashBinding> mNotesTrashAdapter;  // @Inject
    public TrashContract.presenter trashPresenter;  // @Inject
    public DataManager dataManager; // @Inject_GLOBAL
    public ActionUtils actionUtils; // @Inject_GLOBAL
    public TrashNoteActionTool trashNoteActionTool; // @Inject


    public TrashActivity() {
        trashPresenter = null;
        dataManager = new DataManager();
        actionUtils = new ActionUtils();
        trashNoteActionTool = new TrashNoteActionTool();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(TrashActivity.this, R.layout.activity_trash);
        binding.setPresenter((TrashPresenter) trashPresenter);
        trashPresenter.attachView(this);
        trashPresenter.viewIsReady();


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
            } else
                finish();
        }

        return true;
    }


    @Override
    public void settingsNotesList(int countColumn, LiveData<List<TrashNote>> noteList) {
        binding.ListTrash.addItemDecoration(new SpacesItemDecoration(15));
        binding.ListTrash.setLayoutManager(
                new StaggeredGridLayoutManager(countColumn, LinearLayoutManager.VERTICAL));
        mNotesTrashAdapter = new TrashAdapter<>(new DiffUtilTrash(),
                R.layout.item_note_trash,
                (binder, model) -> {
                    binder.setNote(model);
                    binding.executePendingBindings();
                });

        binding.ListTrash.setAdapter(mNotesTrashAdapter);
        noteList.observe(
                this,
                notes -> {
                    mNotesTrashAdapter.sortListTrash(notes);
                    if (!(notes.size() >= 1)) showEmptyTrash();
                });
    }


    private void showEmptyTrash() {
        binding.setEmptyNotesTrash(true);
        binding.includeEmpty.emptyViewTrash.setVisibility(View.VISIBLE);
    }

    @Override
    public void cleanTrashDialogShow() {
//        new CleanTrashDialog(null).show(getSupportFragmentManager(), "CLeanTrash");
    }

    @Override
    public void initActionUtils() {
        actionUtils.createObject(binding.getRoot().findViewById(R.id.activity_trash));
        trashNoteActionTool.createObject(mNotesTrashAdapter);
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
