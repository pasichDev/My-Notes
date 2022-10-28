package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.utils.actionPanel.ActionUtils.getAction;
import static com.pasich.mynotes.utils.constants.TagSettings.MAX_TAG_COUNT;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.data.old.notes.Note;
import com.pasich.mynotes.databinding.ActivityMainBinding;
import com.pasich.mynotes.databinding.ItemNoteBinding;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.presenter.MainPresenter;
import com.pasich.mynotes.ui.view.dialogs.main.ChoiceNoteDialog;
import com.pasich.mynotes.ui.view.dialogs.main.ChoiceTagDialog;
import com.pasich.mynotes.ui.view.dialogs.main.ChooseSortDialog;
import com.pasich.mynotes.ui.view.dialogs.main.SearchDialog;
import com.pasich.mynotes.ui.view.dialogs.main.TagDialog;
import com.pasich.mynotes.utils.FormatListUtils;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.actionPanel.ActionUtils;
import com.pasich.mynotes.utils.actionPanel.tool.NoteActionTool;
import com.pasich.mynotes.utils.activity.MainUtils;
import com.pasich.mynotes.utils.adapters.NoteAdapter;
import com.pasich.mynotes.utils.adapters.tagAdapter.OnItemClickListenerTag;
import com.pasich.mynotes.utils.adapters.tagAdapter.TagsAdapter;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends BaseActivity implements MainContract.view {


    @Inject
    public MainContract.presenter mainPresenter;
    @Inject
    public MainUtils utils;
    @Inject
    public FormatListUtils formatList;
    @Inject
    public NoteActionTool noteActionTool;
    @Inject
    public TagsAdapter tagsAdapter;
    @Inject
    public StaggeredGridLayoutManager staggeredGridLayoutManager;
    @Inject
    public ActionUtils actionUtils;
    @Inject
    public NoteAdapter<ItemNoteBinding> mNoteAdapter;

    private ActivityMainBinding mActivityBinding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        getActivityComponent().inject(this);
        mainPresenter.attachView(this);
        mainPresenter.viewIsReady();
        mActivityBinding.setPresenter((MainPresenter) mainPresenter);

    }


    @Override
    public void initActionUtils() {
        actionUtils.createObject(mActivityBinding.getRoot().findViewById(R.id.activity_main));

    }

    @Override
    public void sortButton() {
        if (!getAction()) new ChooseSortDialog().show(getSupportFragmentManager(), "sortDialog");
    }

    @Override
    public void formatButton(int countSpan) {
        if (!getAction()) {
            formatList.formatNote();
            staggeredGridLayoutManager.setSpanCount(countSpan);
        }
    }

    @Override
    public void startSearchDialog() {
        mActivityBinding.layoutSearch.startAnimation(AnimationUtils.loadAnimation(this, R.anim.click_scale));
        new SearchDialog().show(getSupportFragmentManager(), "SearchDialog");
    }


    @Override
    public void initListeners() {

        tagsAdapter.setOnItemClickListener(new OnItemClickListenerTag() {
            @Override
            public void onClick(int position) {
                if (!getAction())
                    mainPresenter.clickTag(tagsAdapter.getCurrentList().get(position), position);
            }

            @Override
            public void onLongClick(int position) {
                if (!getAction())
                    mainPresenter.clickLongTag(tagsAdapter.getCurrentList().get(position));
            }
        });

/*        mNoteAdapter.setOnItemClickListener(new OnItemClickListener<Note>() {

            @Override
            public void onClick(int position, Note model) {
                if (!getAction()) mainPresenter.clickNote(model.id);
                else selectItemAction(model, position);

            }

            @Override
            public void onLongClick(int position, Note model) {
                if (!getAction()) choiceNoteDialog(model, position);
            }


        });



 */
    }


    @Override
    public void settingsSearchView() {
        formatList.init(mActivityBinding.formatButton);

    }


    @Override
    public void settingsTagsList() {

        mActivityBinding.listTags.addItemDecoration(new SpacesItemDecoration(5));
        mActivityBinding.listTags.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        mActivityBinding.listTags.setAdapter(tagsAdapter);

    }

    @Override
    public void settingsNotesList() {
        mActivityBinding.listNotes.addItemDecoration(new SpacesItemDecoration(15));
        mActivityBinding.listNotes.setLayoutManager(staggeredGridLayoutManager);
        //   mNoteAdapter = new NoteAdapter<ItemNoteBinding>(new DiffUtilNote(), R.layout.item_note, (binder, model) -> {
        //       binder.setNote(model);
        //        mActivityBinding.executePendingBindings();
        //    });

        mActivityBinding.listNotes.setAdapter(mNoteAdapter);


    }


    @Override
    public void loadingData(Observable<List<Tag>> tagList, LiveData<List<Note>> noteList) {


        tagList.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(tags -> tagsAdapter.submitList(tags));


        //  noteList.observe(this, notes -> {
        //      mNoteAdapter.sortList(notes, dataManager.getDefaultPreference().getString(ARGUMENT_PREFERENCE_SORT, ARGUMENT_DEFAULT_SORT_PREF));
        //       showEmptyTrash(!(notes.size() >= 1));
        //    });
        //  tagList.observe(this, tags -> tagsAdapter.submitList(tags));
    }


    private void showEmptyTrash(boolean flag) {
        mActivityBinding.setEmptyNotes(flag);
        mActivityBinding.includeEmpty.emptyViewNote.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    @Override
    public void deleteNote(Note note) {
        mainPresenter.deleteNote(note);
    }

    @Override
    public void actionStartNote(Note note, int position) {
        selectItemAction(note, position);
    }

    @Override
    public void tagNoteSelected(Note note) {
        new TagDialog(note).show(getSupportFragmentManager(), "EditDIalog");
    }


    @Override
    public void openNoteEdit(int idNote) {
        startActivity(new Intent(this, NoteActivity.class).putExtra("NewNote", false).putExtra("idNote", idNote).putExtra("shareText", "").putExtra("tagNote", ""));
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void startToastCheckCountTags() {
        onError(getString(R.string.countTagsError, MAX_TAG_COUNT), mActivityBinding.newNotesButton);
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
        Tag tagSelected = tagsAdapter.getTagSelected();
        startActivity(new Intent(this, NoteActivity.class).putExtra("NewNote", true).putExtra("shareText", "").putExtra("tagNote", tagSelected.getSystemAction() == 2 ? "" : tagSelected.getNameTag()), ActivityOptions.makeSceneTransitionAnimation(this, mActivityBinding.newNotesButton, "robot").toBundle());
    }

    @Override
    public void moreActivity() {
        //   if (getAction()) actionUtils.closeActionPanel();
        //   new AboutDialog().show(getSupportFragmentManager(), "more activity");

        finish();
        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }

    @Override
    public void startCreateTagDialog() {
        //     new NewTagDialog(dataManager.getTagsRepository()).show(getSupportFragmentManager(), "New Tag");
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
        if (getAction()) actionUtils.closeActionPanel();
        else utils.CloseApp(MainActivity.this);
    }


    @Override
    public void sortList(String arg) {
        mNoteAdapter.sortList(arg);

    }

    @Override
    public void activateActionPanel() {
        mActivityBinding.newNotesButton.setVisibility(View.GONE);
    }

    @Override
    public void deactivationActionPanel() {
        mActivityBinding.newNotesButton.setVisibility(View.VISIBLE);
    }


    @Override
    public void deleteNotes() {
        mainPresenter.deleteNotesArray(noteActionTool.getArrayChecked());
        actionUtils.closeActionPanel();
    }


    @Override
    public void shareNotes() {
        StringBuilder valueShare = new StringBuilder();
        for (Note note : noteActionTool.getArrayChecked()) {
            valueShare.append(note.getTitle()).append(System.getProperty("line.separator")).append(System.getProperty("line.separator")).append(note.getValue()).append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));
        }
        new ShareUtils(valueShare.toString(), this).shareText();
        actionUtils.closeActionPanel();
    }

    @Override
    public void restoreNotes() {

    }


    @Override
    public void selectItemAction(Note note, int position) {
        if (note.getChecked()) {
            note.setChecked(false);
            if (!noteActionTool.isCheckedItemFalse(note)) actionUtils.closeActionPanel();
        } else {
            noteActionTool.isCheckedItem(note);
            note.setChecked(true);
        }

        actionUtils.manageActionPanel(noteActionTool.getCountCheckedItem());
        mNoteAdapter.notifyItemChanged(position, 22);
    }

    @Override
    public void toolCleanChecked() {
        noteActionTool.checkedClean();
    }

    @Override
    public void selectTagUser(int position) {
        tagsAdapter.chooseTag(position);
//        mNoteAdapter.showTagNotes(tagsAdapter.getTagSelected().getNameTag());

    }


    private void variablesNull() {
        mNoteAdapter = null;
        tagsAdapter = null;
    }

    @Override
    public void errorProcessRestore() {
        onError(R.string.errorEmptyNotesRestore, mActivityBinding.newNotesButton);
    }

    @Override
    public void successfullyProcessRestore(int countNotes) {
        onError(getString(R.string.successfullyRestoreNotes, countNotes), mActivityBinding.newNotesButton);
    }

    @Override
    public void saveNoteRestore(Note newNote) {
        mainPresenter.addNote(newNote);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
        if (isFinishing()) {
            variablesNull();
            mainPresenter.destroy();

        }
    }
}
