package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.utils.actionPanel.ActionUtils.getAction;
import static com.pasich.mynotes.utils.constants.TagSettings.MAX_TAG_COUNT;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.data.model.Note;
import com.pasich.mynotes.data.model.Tag;
import com.pasich.mynotes.databinding.ActivityMainBinding;
import com.pasich.mynotes.databinding.ItemNoteBinding;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.presenter.MainPresenter;
import com.pasich.mynotes.ui.view.dialogs.MoreNoteDialog;
import com.pasich.mynotes.ui.view.dialogs.about.AboutDialog;
import com.pasich.mynotes.ui.view.dialogs.about.AboutOpensActivity;
import com.pasich.mynotes.ui.view.dialogs.main.DeleteTagDialog;
import com.pasich.mynotes.ui.view.dialogs.main.NameTagDialog;
import com.pasich.mynotes.ui.view.dialogs.main.SearchDialog;
import com.pasich.mynotes.ui.view.dialogs.main.SortDialog;
import com.pasich.mynotes.ui.view.dialogs.main.popupWindowsTag.PopupWindowsTag;
import com.pasich.mynotes.ui.view.dialogs.main.popupWindowsTag.PopupWindowsTagOnClickListener;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.actionPanel.ActionUtils;
import com.pasich.mynotes.utils.actionPanel.interfaces.ManagerViewAction;
import com.pasich.mynotes.utils.actionPanel.tool.NoteActionTool;
import com.pasich.mynotes.utils.adapters.NoteAdapter;
import com.pasich.mynotes.utils.adapters.baseGenericAdapter.OnItemClickListener;
import com.pasich.mynotes.utils.adapters.tagAdapter.OnItemClickListenerTag;
import com.pasich.mynotes.utils.adapters.tagAdapter.TagsAdapter;
import com.pasich.mynotes.utils.constants.SnackBarInfo;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;
import com.pasich.mynotes.utils.recycler.SwipeToListNotesCallback;
import com.pasich.mynotes.utils.tool.FormatListTool;
import com.pasich.mynotes.utils.transition.ConstTransition;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity implements MainContract.view, ManagerViewAction<Note> {


    final private ActivityResultLauncher<Intent> startThemeActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        Intent data = result.getData();
        if (result.getResultCode() == 11) {
            assert data != null;
            this.redrawActivity(data.getIntExtra("updateThemeStyle", 0));
        }

    });
    public ActivityMainBinding mActivityBinding;
    @Inject
    public MainContract.presenter mainPresenter;
    @Inject
    public FormatListTool formatList;
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
    @Named("TagsItemSpaceDecoration")
    @Inject
    public SpacesItemDecoration itemDecorationTags;
    @Named("NotesItemSpaceDecoration")
    @Inject
    public SpacesItemDecoration itemDecorationNotes;
    @Inject
    public LinearLayoutManager mLinearLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        selectTheme();
        setExitSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        getWindow().setSharedElementsUseOverlay(false);

        super.onCreate(savedInstanceState);
        mActivityBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mActivityBinding.getRoot());
        mainPresenter.attachView(this);
        mainPresenter.viewIsReady();
        mActivityBinding.setPresenter((MainPresenter) mainPresenter);
        actionUtils.setMangerView(mActivityBinding.getRoot());

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
        if (isDestroyed()) {
            mainPresenter.detachView();
            variablesNull();
        }
    }


    @Override
    public void sortButton() {
        if (!getAction()) new SortDialog().show(getSupportFragmentManager(), "sortDialog");
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
    public void exitWhat() {
        onInfoSnack(R.string.exitWhat, mActivityBinding.newNotesButton, SnackBarInfo.Info, Snackbar.LENGTH_LONG);
    }

    @Override
    public void finishActivityOtPresenter() {
        finish();
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
            public void onClick(int position, Note model, MaterialCardView materialCardView) {
                if (!getAction()) openNoteEdit(model.id, materialCardView);
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
        mActivityBinding.listTags.setLayoutManager(mLinearLayoutManager);
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
                    mainPresenter.setBackupDeleteNote(sNote);
                    mainPresenter.deleteNote(sNote);
                    snackBarRestoreNote();
                }
            }
        };


        new ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(mActivityBinding.listNotes);


    }


    public void snackBarRestoreNote() {
        Snackbar snackbar = Snackbar.make(mActivityBinding.newNotesButton, getString(R.string.noteMoveTrashSnackbar), Snackbar.LENGTH_LONG);
        snackbar.setAction(getString(R.string.restore), view -> mainPresenter.restoreNote(mainPresenter.getBackupDeleteNote()));
        if (mActivityBinding.newNotesButton.getY() >= mActivityBinding.activityMain.getHeight()) {
            snackbar.setAnchorView(mActivityBinding.newNotesButton);
        }
        snackbar.show();
    }

    @Override
    public void loadingNotes(List<Note> noteList) {
        int countNotes = mNoteAdapter.sortList(noteList, mainPresenter.getSortParam(), tagsAdapter.getTagSelected() == null ? "allNotes" : tagsAdapter.getTagSelected().getNameTag());
        showEmptyNotes(!(countNotes >= 1));
    }

    @Override
    public void loadingTags(List<Tag> tagList) {
        tagsAdapter.submitList(tagList);
        int countNotes = mNoteAdapter.setNameTagsHidden(tagList, tagsAdapter.getTagSelected() == null ? "allNotes" : tagsAdapter.getTagSelected().getNameTag());
        showEmptyNotes(!(countNotes >= 1));
    }


    private void showEmptyNotes(boolean flag) {
        mActivityBinding.setEmptyNotes(flag);
        if (getResources().getDisplayMetrics().density < 2.2)
            mActivityBinding.includeEmpty.imageEmpty.setVisibility(View.GONE);
        mActivityBinding.includeEmpty.emptyViewNote.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    @Override
    public void actionStartNote(Note note, int position) {
        selectItemAction(note, position, true);
    }


    // TODO: 05.02.2023 Здесь будет ошибка
    @Override
    public void openCopyNote(int idNote) {
        startActivity(new Intent(this, NoteActivity.class)
                        .putExtra("NewNote", false)
                        .putExtra("idNote", idNote)
                        .putExtra("shareText", "")
                        .putExtra("tagNote", ""),
                ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());

    }

    @Override
    public void callbackDeleteNote(Note mNote) {
        mainPresenter.setBackupDeleteNote(mNote);
        snackBarRestoreNote();
    }


    public void openNoteEdit(long idNote, MaterialCardView materialCardView) {
        startActivity(new Intent(this, NoteActivity.class).putExtra("NewNote", false).putExtra("idNote", idNote).putExtra("shareText", "").putExtra("tagNote", ""), ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, materialCardView, String.valueOf(idNote)).toBundle());
    }

    @Override
    public void startToastCheckCountTags() {
        onInfoSnack(Integer.parseInt(getString(R.string.countTagsError, String.valueOf(MAX_TAG_COUNT))), mActivityBinding.newNotesButton, SnackBarInfo.Info, Snackbar.LENGTH_LONG);
    }


    @Override
    public void newNotesButton() {
        Tag tagSelected = tagsAdapter.getTagSelected();
        String tagName = tagSelected == null ? "" : tagSelected.getSystemAction() == 2 ? "" : tagSelected.getNameTag();
        startActivity(new Intent(this, NoteActivity.class).putExtra("NewNote", true).putExtra("tagNote", tagName), ActivityOptionsCompat.makeSceneTransitionAnimation(this, mActivityBinding.newNotesButton, ConstTransition.fabTransaction).toBundle());
    }

    @Override
    public void moreActivity() {
        if (getAction()) actionUtils.closeActionPanel();
        new AboutDialog(new AboutOpensActivity() {
            @Override
            protected void openThemeActivity() {
                startThemeActivity.launch(new Intent(MainActivity.this, ThemeActivity.class),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, (Pair<View, String>[]) null));
            }

            @Override
            protected void openTrash() {
                startActivity(new Intent(MainActivity.this, TrashActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());

            }

            @Override
            protected void openAboutActivity() {
                startActivity(new Intent(MainActivity.this, AboutActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }

            @Override
            protected void openBackupActivity() {
                startActivity(new Intent(MainActivity.this, BackupActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        }).show(getSupportFragmentManager(), "MoreActivity");
    }

    @Override
    public void startCreateTagDialog() {
        new NameTagDialog().show(getSupportFragmentManager(), "New Tag");
    }

    @Override
    public void choiceTagDialog(Tag tag, View mView) {
        new PopupWindowsTag(getLayoutInflater(), mView, tag, new PopupWindowsTagOnClickListener() {
            @Override
            public void deleteTag() {
                if (tagsAdapter.getTagSelected() == tag)
                    selectTagUser(tagsAdapter.getTagForName("allNotes"));
                mainPresenter.deleteTag(tag);
            }

            @Override
            public void renameTag() {
                new NameTagDialog(tag).show(getSupportFragmentManager(), "RenameTag");
            }

            @Override
            public void visibleEditTag() {
                mainPresenter.editVisibleTag(tag.setVisibilityReturn(tag.getVisibility() == 1 ? 0 : 1));
            }
        });


    }

    @Override
    public void choiceNoteDialog(Note note, int position) {
        new MoreNoteDialog(note, false, false, position).show(getSupportFragmentManager(), "ChoiceDialog");
    }


    @Override
    public void onBackPressed() {
        if (getAction()) actionUtils.closeActionPanel();
        else mainPresenter.closeApp();
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
        ShareUtils.shareNotes(this, valueShare.toString());
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
        showEmptyNotes(!(mNoteAdapter.filter(tagsAdapter.getTagSelected() == null ? "allNotes" : tagsAdapter.getTagSelected().getNameTag()) >= 1));
    }


    private void variablesNull() {
        mNoteAdapter = null;
        tagsAdapter = null;
    }

    @Override
    public void createShortCut() {
        onInfoSnack(R.string.addShortCutSuccessfully, mActivityBinding.newNotesButton, SnackBarInfo.Info, Snackbar.LENGTH_LONG);
    }

    @Override
    public void shortCutDouble() {
        onInfoSnack(R.string.shortCutCreateFallDouble, mActivityBinding.newNotesButton, SnackBarInfo.Info, Snackbar.LENGTH_LONG);
    }

    @Override
    public void redrawActivity(int themeStyle) {
        super.redrawActivity(themeStyle);
        setTheme(themeStyle);
        recreate();
    }
}
