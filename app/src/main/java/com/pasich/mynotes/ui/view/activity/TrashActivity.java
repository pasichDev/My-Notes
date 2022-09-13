package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.di.App.getApp;
import static com.pasich.mynotes.utils.actionPanel.ActionUtils.getAction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.trash.TrashNote;
import com.pasich.mynotes.databinding.ActivityTrashBinding;
import com.pasich.mynotes.databinding.ItemNoteTrashBinding;
import com.pasich.mynotes.di.trash.TrashActivityModule;
import com.pasich.mynotes.ui.contract.TrashContract;
import com.pasich.mynotes.ui.presenter.TrashPresenter;
import com.pasich.mynotes.ui.view.dialogs.trash.CleanTrashDialog;
import com.pasich.mynotes.utils.actionPanel.ActionUtils;
import com.pasich.mynotes.utils.actionPanel.interfaces.ManagerViewAction;
import com.pasich.mynotes.utils.actionPanel.tool.TrashNoteActionTool;
import com.pasich.mynotes.utils.adapters.genericAdapterNote.GenericNoteAdapter;
import com.pasich.mynotes.utils.adapters.genericAdapterNote.OnItemClickListener;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtilTrash;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class TrashActivity extends AppCompatActivity implements TrashContract.view, ManagerViewAction<TrashNote> {

    private ActivityTrashBinding binding;

    private GenericNoteAdapter<TrashNote, ItemNoteTrashBinding> mNotesTrashAdapter;
    @Inject
    public TrashContract.presenter trashPresenter;
    @Inject
    public DataManager dataManager;
    @Inject
    public ActionUtils actionUtils;
    @Inject
    public TrashNoteActionTool trashNoteActionTool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(TrashActivity.this, R.layout.activity_trash);
        init();

        trashPresenter.attachView(this);
        trashPresenter.setDataManager(dataManager);
        trashPresenter.viewIsReady();

    }

    @Override
    public void init() {
        getApp()
                .getComponentsHolder()
                .getActivityComponent(getClass(), new TrashActivityModule())
                .inject(TrashActivity.this);
        binding.setPresenter((TrashPresenter) trashPresenter);
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
        setSupportActionBar(binding.toolbarActionbar.toolbarActionbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        trashPresenter.detachView();
        if (isFinishing()) {
            trashPresenter.destroy();
            getApp().getComponentsHolder().releaseActivityComponent(getClass());
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
            if (getAction()) actionUtils.closeActionPanel();
            finish();
        }
        return true;
    }


    @Override
    public void settingsNotesList(int countColumn, LiveData<List<TrashNote>> noteList) {
        binding.ListTrash.addItemDecoration(new SpacesItemDecoration(15));
        binding.ListTrash.setLayoutManager(
                new StaggeredGridLayoutManager(countColumn, LinearLayoutManager.VERTICAL));
        mNotesTrashAdapter = new GenericNoteAdapter<>(new DiffUtilTrash(),
                R.layout.item_note_trash,
                (binder, model) -> {
                    binder.setNote(model);
                    binding.executePendingBindings();
                });

        binding.ListTrash.setAdapter(mNotesTrashAdapter);
        noteList.observe(
                this,
                notes -> {
                    mNotesTrashAdapter.submitList(notes);
                    binding.setEmptyNotesTrash(!(notes.size() >= 1));
                    binding.ListTrash.scheduleLayoutAnimation();
                });
    }

    @Override
    public void cleanTrashDialogShow() {
        new CleanTrashDialog(dataManager.getTrashRepository()).show(getSupportFragmentManager(), "CLeanTrash");
    }

    @Override
    public void initActionUtils() {
        actionUtils.createObject(getLayoutInflater(), binding.getRoot().findViewById(R.id.activity_trash));
        trashNoteActionTool.createObject(mNotesTrashAdapter);
        actionUtils.setTrash();
    }


    @Override
    public void activateActionPanel() {
        binding.cleanTrash.setEnabled(false);
    }

    @Override
    public void deactivationActionPanel() {
        binding.cleanTrash.setEnabled(true);
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
