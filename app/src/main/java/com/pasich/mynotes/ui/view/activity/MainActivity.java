package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.di.App.getApp;
import static com.pasich.mynotes.utils.constants.TagSettings.MAX_TAG_COUNT;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.databinding.ActivityMainBinding;
import com.pasich.mynotes.databinding.ItemNoteBinding;
import com.pasich.mynotes.di.main.MainActivityModule;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.presenter.MainPresenter;
import com.pasich.mynotes.ui.view.dialogs.main.ChoiceNoteDialog;
import com.pasich.mynotes.ui.view.dialogs.main.ChoiceTagDialog;
import com.pasich.mynotes.ui.view.dialogs.main.ChooseSortDialog;
import com.pasich.mynotes.ui.view.dialogs.main.NewTagDialog;
import com.pasich.mynotes.ui.view.dialogs.main.OtherActivityDialog;
import com.pasich.mynotes.ui.view.dialogs.main.TagDialog;
import com.pasich.mynotes.utils.FormatListUtils;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.actionPanel.ActionUtils;
import com.pasich.mynotes.utils.actionPanel.ManagerViewAction;
import com.pasich.mynotes.utils.activity.MainUtils;
import com.pasich.mynotes.utils.adapters.TagsAdapter;
import com.pasich.mynotes.utils.adapters.genericAdapterNote.GenericNoteAdapter;
import com.pasich.mynotes.utils.adapters.genericAdapterNote.OnItemClickListener;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtilNote;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.view, ManagerViewAction {

    @Inject
    public MainContract.presenter mainPresenter;
    @Inject
    public MainUtils utils;
    @Inject
    public FormatListUtils formatList;
    @Inject
    public DataManager dataManager;
    @Inject
    public ActionUtils actionUtils;

    private ActivityMainBinding binding;
    private TagsAdapter tagsAdapter;
    private StaggeredGridLayoutManager gridLayoutManager;

    private GenericNoteAdapter<Note, ItemNoteBinding> mNoteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        init();

        mainPresenter.attachView(this);
        mainPresenter.setDataManager(dataManager);
        mainPresenter.viewIsReady();

    }


    @Override
    public void init() {
        getApp()
                .getComponentsHolder()
                .getActivityComponent(getClass(), new MainActivityModule())
                .inject(MainActivity.this);
        binding.setPresenter((MainPresenter) mainPresenter);
        gridLayoutManager = new StaggeredGridLayoutManager(
                dataManager.getDefaultPreference().getInt("formatParam", 1),
                LinearLayoutManager.VERTICAL);

    }


    @Override
    public void initActionUtils() {
        actionUtils.createObject(getLayoutInflater(), mNoteAdapter, binding.getRoot().findViewById(R.id.activity_main));
    }


    @Override
    public void initListeners() {

        tagsAdapter.setOnItemClickListener(
                new TagsAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        if (!actionUtils.getAction())
                            mainPresenter.clickTag(tagsAdapter.getCurrentList().get(position), position);
                    }

                    @Override
                    public void onLongClick(int position) {
                        if (!actionUtils.getAction())
                            mainPresenter.clickLongTag(tagsAdapter.getCurrentList().get(position));
                    }
                });

        mNoteAdapter.setOnItemClickListener(
                new OnItemClickListener<Note>() {

                    @Override
                    public void onClick(int position, Note model) {
                        if (!actionUtils.getAction())
                            mainPresenter.clickNote(model.id);
                        else actionUtils.selectItemAction(position);

                    }

                    @Override
                    public void onLongClick(int position, Note model) {
                        if (!actionUtils.getAction())
                            choiceNoteDialog(model, position);
                    }


                });

        binding.actionSearch.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {

                        return false;
                    }
                });


        this.findViewById(R.id.formatButton).setOnClickListener(view -> {
            if (!actionUtils.getAction()) {
                formatList.formatNote();
                gridLayoutManager.setSpanCount(dataManager.getDefaultPreference().getInt("formatParam", 1));
            }
        });

        this.findViewById(R.id.sortButton).setOnClickListener(view -> {
            if (!actionUtils.getAction())
                new ChooseSortDialog().show(getSupportFragmentManager(), "sortDialog");
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
        if (isFinishing()) {
            mNoteAdapter = null;
            tagsAdapter = null;
            mainPresenter.destroy();
            getApp().getComponentsHolder().releaseActivityComponent(getClass());
        }
    }

    @Override
    public void settingsSearchView() {
        LinearLayout llSearchView = (LinearLayout) binding.actionSearch.getChildAt(0);
        llSearchView.addView(utils.addButtonSearchView(this, R.drawable.ic_sort, R.id.sortButton));
        llSearchView.addView(
                utils.addButtonSearchView(this, R.drawable.ic_edit_format_list, R.id.formatButton));
        formatList.init(findViewById(R.id.formatButton));
        binding.actionSearch.setEnabled(false);
    }


    @Override
    public void settingsTagsList(LiveData<List<Tag>> tagList) {
        binding.listTags.addItemDecoration(new SpacesItemDecoration(5));
        binding.listTags.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        tagsAdapter = new TagsAdapter(new TagsAdapter.tagDiff());
        binding.listTags.setAdapter(tagsAdapter);
        tagList.observe(
                this,
                tags -> {
                    tagsAdapter.submitList(tags);

                });
    }

    @Override
    public void settingsNotesList(LiveData<List<Note>> noteList) {
        binding.listNotes.addItemDecoration(new SpacesItemDecoration(15));
        binding.listNotes.setLayoutManager(gridLayoutManager);

        mNoteAdapter = new GenericNoteAdapter<>(new DiffUtilNote(),
                R.layout.item_note,
                (binder, model) -> {
                    binder.setNote(model);
                    binding.executePendingBindings();
                });

        binding.listNotes.setAdapter(mNoteAdapter);
        final int[] start = {0};

        noteList.observe(this, notes -> {
            mNoteAdapter.sortList(notes, dataManager.getDefaultPreference().getString("sortPref", "DataReserve"));
            binding.setEmptyNotes(!(notes.size() >= 1));
            if (start[0] == 0) binding.listNotes.scheduleLayoutAnimation();
            start[0] = 1;
            mNoteAdapter.submitList(notes);
        });
    }

    @Override
    public void deleteNote(Note note) {
        mainPresenter.deleteNote(note);
    }

    @Override
    public void actionStartNote(int position) {
        actionUtils.selectItemAction(position);
    }

    @Override
    public void tagNoteSelected(Note note) {
        new TagDialog(note).show(getSupportFragmentManager(), "EditDIalog");
    }

    @Override
    public void selectTagUser(int position) {
        tagsAdapter.chooseTag(position);
    }

    @Override
    public void openNoteEdit(int idNote) {
        startActivity(new Intent(this, NoteActivity.class)
                .putExtra("NewNote", false)
                .putExtra("idNote", idNote)
                .putExtra("tagNote", ""));
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void startToastCheckCountTags() {
        Toast.makeText(this, getString(R.string.countTagsError, MAX_TAG_COUNT), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void deleteTag(Tag tag, boolean deleteNotes) {
        try {
            mainPresenter.deleteTag(tag, deleteNotes);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editVisibility(Tag tag) {
        mainPresenter.editVisibility(tag);
    }

    @Override
    public void newNotesButton() {

        startActivity(new Intent(this, NoteActivity.class)
                .putExtra("NewNote", true)
                .putExtra("tagNote", ""), ActivityOptions
                .makeSceneTransitionAnimation(this, binding.newNotesButton, "robot")
                .toBundle());
    }

    @Override
    public void moreActivity() {
        if (actionUtils.getAction()) actionUtils.closeActionPanel();
        new OtherActivityDialog().show(getSupportFragmentManager(), "more activity");

    }

    @Override
    public void startCreateTagDialog() {
        new NewTagDialog(dataManager.getTagsRepository()).show(getSupportFragmentManager(), "New Tag");
    }

    @Override
    public void choiceTagDialog(Tag tag, Integer[] arg) {
        new ChoiceTagDialog(tag, arg).show(getSupportFragmentManager(), "ChoiceDialog");
    }

    @Override
    public void choiceNoteDialog(Note note, int position) {
        new ChoiceNoteDialog(note, position).show(getSupportFragmentManager(), "ChoiceDialog");
    }


    @Override
    public void onBackPressed() {
        if (actionUtils.getAction()) actionUtils.closeActionPanel();
        else
            utils.CloseApp(MainActivity.this);
    }


    @Override
    public void sortList(String arg) {
        mNoteAdapter.sortList(arg);

    }

    @Override
    public void activateActionPanel() {
        binding.newNotesButton.setVisibility(View.GONE);
    }

    @Override
    public void deactivationActionPanel() {
        binding.newNotesButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void deleteNotes(ArrayList<Note> notes) {
        mainPresenter.deleteNotesArray(notes);
        actionUtils.closeActionPanel();
    }

    @Override
    public void shareNotes(ArrayList<Note> notes) {
        String valueShare = "";
        for (Note note : notes) {
            valueShare = valueShare + note.getTitle() +
                    System.getProperty("line.separator") +
                    System.getProperty("line.separator") +
                    note.getValue() +
                    System.getProperty("line.separator") +
                    System.getProperty("line.separator");
        }
        new ShareUtils(valueShare, this).shareText();
        actionUtils.closeActionPanel();
    }
}
