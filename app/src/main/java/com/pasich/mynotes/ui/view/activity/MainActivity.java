package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.utils.actionPanel.ActionUtils.getAction;
import static com.pasich.mynotes.utils.constants.TagSettings.MAX_TAG_COUNT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.databinding.ActivityMainBinding;
import com.pasich.mynotes.databinding.ItemNoteBinding;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.presenter.MainPresenter;
import com.pasich.mynotes.ui.view.dialogs.MoreNoteDialog;
import com.pasich.mynotes.ui.view.dialogs.main.ChooseSortDialog;
import com.pasich.mynotes.ui.view.dialogs.main.DeleteTagDialog;
import com.pasich.mynotes.ui.view.dialogs.main.NameTagDialog;
import com.pasich.mynotes.ui.view.dialogs.main.SearchDialog;
import com.pasich.mynotes.ui.view.dialogs.settings.AboutDialog;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.actionPanel.ActionUtils;
import com.pasich.mynotes.utils.actionPanel.interfaces.ManagerViewAction;
import com.pasich.mynotes.utils.actionPanel.tool.NoteActionTool;
import com.pasich.mynotes.utils.activity.MainUtils;
import com.pasich.mynotes.utils.adapters.NoteAdapter;
import com.pasich.mynotes.utils.adapters.baseGenericAdapter.OnItemClickListener;
import com.pasich.mynotes.utils.adapters.tagAdapter.OnItemClickListenerTag;
import com.pasich.mynotes.utils.adapters.tagAdapter.TagsAdapter;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;
import com.pasich.mynotes.utils.recycler.SwipeToListNotesCallback;
import com.pasich.mynotes.utils.tool.FormatListTool;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


public class MainActivity extends BaseActivity implements MainContract.view, ManagerViewAction<Note> {

    @Inject
    public ActivityMainBinding mActivityBinding;
    @Inject
    public MainContract.presenter mainPresenter;
    @Inject
    public MainUtils utils;
    @Inject
    public FormatListTool formatList;
    @Inject
    public NoteActionTool noteActionTool;
    @Inject
    public TagsAdapter tagsAdapter;
    @Inject
    public StaggeredGridLayoutManager staggeredGridLayoutManager;
    @Named("ActionUtilsMain")
    @Inject
    public ActionUtils actionUtils;
    @Inject
    public NoteAdapter<ItemNoteBinding> mNoteAdapter;
    @Named("TagsItemSpaceDecoration")
    @Inject
    public SpacesItemDecoration itemDecorationTags;
    @Named("NotesItemSpaceDecoration")
    @Inject
    public SpacesItemDecoration itemDecorationNotes;

    private Note backupDeleteNote;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        mainPresenter.attachView(this);
        mainPresenter.viewIsReady();
        mActivityBinding.setPresenter((MainPresenter) mainPresenter);


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
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
        variablesNull();

    }

    @Override
    public void sortButton() {
        if (!getAction()) new ChooseSortDialog().show(getSupportFragmentManager(), "sortDialog");
    }

    @Override
    public void formatButton() {
        if (!getAction()) {
            formatList.formatNote();
            staggeredGridLayoutManager.setSpanCount(mainPresenter.getDataManager().getFormatCount());
        }
    }

    @Override
    public void startSearchDialog() {
        mActivityBinding.layoutSearch.startAnimation(AnimationUtils.loadAnimation(this, R.anim.click_scale));
        new SearchDialog().show(getSupportFragmentManager(), "SearchDialog");
    }

    @Override
    public void startDeleteTagDialog(Tag tag) {
        new DeleteTagDialog(tag).show(getSupportFragmentManager(), "deleteTag");
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
            public void onLongClick(int position, View mView) {
                if (!getAction())
                    mainPresenter.clickLongTag(tagsAdapter.getCurrentList().get(position), mView);
            }
        });

        mNoteAdapter.setOnItemClickListener(new OnItemClickListener<>() {

            @Override
            public void onClick(int position, Note model) {
                if (!getAction()) mainPresenter.clickNote(model.id);
                else selectItemAction(model, position, true);

            }

            @Override
            public void onLongClick(int position, Note model) {
                if (!getAction()) choiceNoteDialog(model, position);
            }

        });
    }


    @Override
    public void settingsSearchView() {
        formatList.init(mActivityBinding.formatButton);

    }


    @Override
    public void settingsTagsList() {
        mActivityBinding.listTags.addItemDecoration(itemDecorationTags);
        mActivityBinding.listTags.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mActivityBinding.listTags.setAdapter(tagsAdapter);

    }

    @Override
    public void settingsNotesList() {

        mActivityBinding.listNotes.addItemDecoration(itemDecorationNotes);
        mActivityBinding.listNotes.setLayoutManager(staggeredGridLayoutManager);
        mActivityBinding.listNotes.setAdapter(mNoteAdapter);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new SwipeToListNotesCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean isItemViewSwipeEnabled() {
                return !getAction() && mainPresenter.getDataManager().getFormatCount() == 1;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    selectItemAction(mNoteAdapter.getCurrentList().get(position), position, false);


                } else {

                    Note sNote = mNoteAdapter.getCurrentList().get(position);
                    backupDeleteNote = sNote;
                    mainPresenter.deleteNote(sNote);
                    snackBarRestoreNote();
                }
            }
        };


        new ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(mActivityBinding.listNotes);


    }


    public void snackBarRestoreNote() {
        Snackbar snackbar = Snackbar.make(mActivityBinding.newNotesButton, getString(R.string.noteMoveTrashSnackbar), Snackbar.LENGTH_LONG);
        snackbar.setAction(getString(R.string.restore), view -> mainPresenter.restoreNote(backupDeleteNote));
        if (mActivityBinding.newNotesButton.getY() >= mActivityBinding.activityMain.getHeight()) {
            snackbar.setAnchorView(mActivityBinding.newNotesButton);
        }
        snackbar.show();
    }

    @Override
    public void loadingNotes(List<Note> noteList) {
        int countNotes = mNoteAdapter.sortList(noteList, mainPresenter.getSortParam(), tagsAdapter.getTagSelected() == null ? "allNotes" : tagsAdapter.getTagSelected().getNameTag());
        showEmptyTrash(!(countNotes >= 1));
    }

    @Override
    public void loadingTags(List<Tag> tagList) {
        tagsAdapter.submitList(tagList);
        int countNotes = mNoteAdapter.setNameTagsHidden(tagList, tagsAdapter.getTagSelected() == null ? "allNotes" : tagsAdapter.getTagSelected().getNameTag());
        showEmptyTrash(!(countNotes >= 1));
    }


    private void showEmptyTrash(boolean flag) {
        mActivityBinding.setEmptyNotes(flag);
        if (getResources().getDisplayMetrics().density < 2.2)
            mActivityBinding.includeEmpty.imageEmpty.setVisibility(View.GONE);
        mActivityBinding.includeEmpty.emptyViewNote.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    @Override
    public void actionStartNote(Note note, int position) {
        selectItemAction(note, position, true);
    }

    @Override
    public void openCopyNote(int idNote) {
        openNoteEdit(idNote);
    }

    @Override
    public void callbackDeleteNote(Note mNote) {
        backupDeleteNote = mNote;
        snackBarRestoreNote();
    }

    @Override
    public void openNoteEdit(int idNote) {
        startActivity(new Intent(this, NoteActivity.class).putExtra("NewNote", false).putExtra("idNote", idNote).putExtra("shareText", "").putExtra("tagNote", ""));
    }

    @Override
    public void startToastCheckCountTags() {
        onError(getString(R.string.countTagsError, String.valueOf(MAX_TAG_COUNT)), mActivityBinding.newNotesButton);
    }


    @Override
    public void newNotesButton() {
        Tag tagSelected = tagsAdapter.getTagSelected();
        String tagName = tagSelected == null ? "" : tagSelected.getSystemAction() == 2 ? "" : tagSelected.getNameTag();
        startActivity(new Intent(this, NoteActivity.class).putExtra("NewNote", true).putExtra("tagNote", tagName));
    }

    @Override
    public void moreActivity() {
        if (getAction()) actionUtils.closeActionPanel();
        new AboutDialog().show(getSupportFragmentManager(), "more activity");
    }

    @Override
    public void startCreateTagDialog() {
        new NameTagDialog().show(getSupportFragmentManager(), "New Tag");
    }

    @Override
    public void choiceTagDialog(Tag tag, View mView) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.view_popup_tag, null);

        TextView textVisibility = view.findViewById(R.id.textVisibilityTag);
        textVisibility.setText(tag.getVisibility() == 1 ? R.string.visibleTag : R.string.hiddeTag);


        PopupWindow tagPopupMenu = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        tagPopupMenu.setElevation(20);
        tagPopupMenu.showAsDropDown(mView, 0, 40);


        view.findViewById(R.id.deleteTag).setOnClickListener(v -> {
            if (tagsAdapter.getTagSelected() == tag)
                selectTagUser(tagsAdapter.getTagForName("allNotes"));
            mainPresenter.deleteTag(tag);
            tagPopupMenu.dismiss();
        });
        view.findViewById(R.id.renameTag).setOnClickListener(v -> {
            new NameTagDialog(tag).show(getSupportFragmentManager(), "RenameTag");
            tagPopupMenu.dismiss();
        });
        view.findViewById(R.id.visibleTag).setOnClickListener(v -> {
            mainPresenter.editVisibleTag(tag.setVisibilityReturn(tag.getVisibility() == 1 ? 0 : 1));
            tagPopupMenu.dismiss();
        });


        tagPopupMenu.setOnDismissListener(() -> {
            view.findViewById(R.id.deleteTag).setOnClickListener(null);
            view.findViewById(R.id.renameTag).setOnClickListener(null);
            view.findViewById(R.id.visibleTag).setOnClickListener(null);

        });
    }


    @Override
    public void choiceNoteDialog(Note note, int position) {
        new MoreNoteDialog(note, false, false, position).show(getSupportFragmentManager(), "ChoiceDialog");
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
        if (noteActionTool.getArrayChecked().size() == mNoteAdapter.getItemCount()) {
            mActivityBinding.appBarMainActivity.setExpanded(true);

        }
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
    public void selectItemAction(Note note, int position, boolean payloads) {
        if (note.getChecked()) {
            note.setChecked(false);
            if (!noteActionTool.isCheckedItemFalse(note)) actionUtils.closeActionPanel();
        } else {
            noteActionTool.isCheckedItem(note);
            note.setChecked(true);
        }

        actionUtils.manageActionPanel(noteActionTool.getCountCheckedItem());
        if (payloads) mNoteAdapter.notifyItemChanged(position, 22);
        else mNoteAdapter.notifyItemChanged(position);
    }

    @Override
    public void toolCleanChecked() {
        noteActionTool.checkedClean();
    }

    @Override
    public void selectTagUser(int position) {
        tagsAdapter.chooseTag(position);
        showEmptyTrash(!(mNoteAdapter.filter(tagsAdapter.getTagSelected() == null ? "allNotes" : tagsAdapter.getTagSelected().getNameTag()) >= 1));
    }


    private void variablesNull() {
        mNoteAdapter = null;
        tagsAdapter = null;
        backupDeleteNote = null;
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


}
