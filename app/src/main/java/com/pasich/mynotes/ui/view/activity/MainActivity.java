package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.di.App.getApp;
import static com.pasich.mynotes.utils.constants.TagSettings.MAX_TAG_COUNT;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.pasich.mynotes.di.main.MainActivityModule;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.presenter.MainPresenter;
import com.pasich.mynotes.ui.view.dialogs.main.ChoiceNoteDialog.ChoiceNoteDialog;
import com.pasich.mynotes.ui.view.dialogs.main.ChoiceTagDialog.ChoiceTagDialog;
import com.pasich.mynotes.ui.view.dialogs.main.ChooseSortDialog;
import com.pasich.mynotes.ui.view.dialogs.main.MoreActivityDialog;
import com.pasich.mynotes.ui.view.dialogs.main.NewTagDialog;
import com.pasich.mynotes.ui.view.dialogs.main.TagDialog.TagDialog;
import com.pasich.mynotes.utils.FormatListUtils;
import com.pasich.mynotes.utils.MainUtils;
import com.pasich.mynotes.utils.adapters.NotesAdapter;
import com.pasich.mynotes.utils.adapters.TagsAdapter;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.view {

    @Inject
    public MainContract.presenter mainPresenter;
    @Inject
    public MainUtils utils;
    @Inject
    public FormatListUtils formatList;
    @Inject
    public DataManager dataManager;

    private ActivityMainBinding binding;
    private TagsAdapter tagsAdapter;
    private NotesAdapter notesAdapter;
    private StaggeredGridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        init();


        mainPresenter.attachView(this);
        mainPresenter.setDataManager(dataManager);
        mainPresenter.viewIsReady();


        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            // doMySearch(query);
            Log.v("pasic", query);
        }


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


    private void initActionUtils() {
     /*   ActionUtils = new ActionUtils(binding.getRoot(), ListNotesAdapter, R.id.activity_main);
        ActionUtils.addButtonToActionPanel(R.drawable.ic_delete, R.id.deleteNotesArray);
        ActionUtils.getActionPanel().findViewById(R.id.deleteNotesArray).setOnClickListener(this);
*/
    }


    @Override
    public void initListeners() {

        tagsAdapter.setOnItemClickListener(
                new TagsAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        mainPresenter.clickTag(tagsAdapter.getCurrentList().get(position), position);
                    }

                    @Override
                    public void onLongClick(int position) {
                        mainPresenter.clickLongTag(tagsAdapter.getCurrentList().get(position));
                    }
                });

        notesAdapter.setOnItemClickListener(
                new NotesAdapter.OnItemClickListener() {

                    @Override
                    public void onClick(int position) {
                      mainPresenter.clickNote(notesAdapter.getCurrentList().get(position).getId());
                    }

                    @Override
                    public void onLongClick(int position) {
                        mainPresenter.clickLongNote(notesAdapter.getCurrentList().get(position));
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
            formatList.formatNote();
            gridLayoutManager.setSpanCount(dataManager.getDefaultPreference().getInt("formatParam", 1));
        });

        this.findViewById(R.id.sortButton).setOnClickListener(view -> new ChooseSortDialog().show(getSupportFragmentManager(), "sortDialog"));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
        if (isFinishing()) {
            notesAdapter = null;
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


        llSearchView.setOnClickListener(view -> onSearchRequested());
        binding.actionSearch.setEnabled(false);
        binding.actionSearch.setOnQueryTextFocusChangeListener(
                (v, hasFocus) -> {
                    onSearchRequested();
                    //   onSearchRequested();
                    //   Log.wtf("pasic", "focus  " + hasFocus);
                    //     binding.listTags.setVisibility(View.GONE);
                    //       findViewById(R.id.sortButton).setVisibility(View.GONE);
                    //       findViewById(R.id.formatButton).setVisibility(View.GONE);
                    //      onSearchRequested();
                    //      binding.actionSearch.setFocusable(false);
                });
        binding.actionSearch.setOnCloseListener(
                () -> {
                    //    Log.wtf("pasic", "close  ");
                    //     findViewById(R.id.sortButton).setVisibility(View.VISIBLE);
                    //      findViewById(R.id.formatButton).setVisibility(View.VISIBLE);
                    //     binding.actionSearch.setFocusable(false);
                    return false;
                });

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
             /*      int positionSelected = tagsAdapter.getCheckedPosition();
                    if (tagsAdapter.getCurrentList().size() >= 1)
                        tagsAdapter.removeOldCheck();
*/

                    tagsAdapter.submitList(tags);

                });
    }

    @Override
    public void settingsNotesList(LiveData<List<Note>> noteList) {
        binding.listNotes.addItemDecoration(new SpacesItemDecoration(15));
        notesAdapter = new NotesAdapter(new NotesAdapter.noteDiff());
        binding.listNotes.setLayoutManager(gridLayoutManager);
        binding.listNotes.setAdapter(notesAdapter);


        noteList.observe(
                this,
                notes -> {
                    Log.wtf("pasich", "update observer note ");
                    notesAdapter.sortList(notes, dataManager.getDefaultPreference().getString("sortPref", "DataReserve"));
                    binding.setEmptyNotes(!(notes.size() >= 1));

                });
    }

    @Override
    public void deleteNote(Note note) {
        mainPresenter.deleteNote(note);
    }

    @Override
    public void actionStartNote() {

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

        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(this, binding.newNotesButton, "robot");
        // start the new activity
        //  startActivity(intent, options.toBundle())

     /*   startActivity(new Intent(this, NoteActivity.class)
                .putExtra("NewNote", false)
                .putExtra("idNote", idNote)
                .putExtra("tagNote", ""), options.toBundle());
        */
        startActivity(new Intent(this, NoteActivity.class)
                .putExtra("NewNote", true)
                .putExtra("tagNote", ""), options.toBundle());
    }

    @Override
    public void moreActivity() {
        new MoreActivityDialog().show(getSupportFragmentManager(), "more activity");

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
    public void choiceNoteDialog(Note note) {
        new ChoiceNoteDialog(note).show(getSupportFragmentManager(), "ChoiceDialog");
    }


    @Override
    public void onBackPressed() {
        utils.CloseApp(MainActivity.this);
    }


    @Override
    public void sortList(String arg) {
        notesAdapter.sortList(arg);

    }
}
