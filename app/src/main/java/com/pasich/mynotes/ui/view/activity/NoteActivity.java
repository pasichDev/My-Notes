package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.utils.FormattedDataUtil.lastDayEditNote;
import static com.pasich.mynotes.utils.transition.TransitionUtil.buildContainerTransform;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.base.simplifications.TextWatcher;
import com.pasich.mynotes.data.model.ItemListNote;
import com.pasich.mynotes.data.model.Note;
import com.pasich.mynotes.databinding.ActivityNoteBinding;
import com.pasich.mynotes.ui.contract.NoteContract;
import com.pasich.mynotes.ui.presenter.NotePresenter;
import com.pasich.mynotes.ui.view.dialogs.MoreNoteDialog;
import com.pasich.mynotes.ui.view.dialogs.note.LinkInfoDialog;
import com.pasich.mynotes.ui.view.dialogs.note.popupWindowsTagNote.PopupWindowsTagNote;
import com.pasich.mynotes.ui.view.dialogs.popupWindowsCreateListBox.PopupWindowsCreateListBox;
import com.pasich.mynotes.ui.view.dialogs.popupWindowsCreateListBox.PopupWindowsCreateListBoxHelper;
import com.pasich.mynotes.utils.CustomLinkMovementMethod;
import com.pasich.mynotes.utils.adapters.itemListNote.ItemListNoteAdapter;
import com.pasich.mynotes.utils.adapters.itemListNote.ItemListSetOnClickListener;
import com.pasich.mynotes.utils.bottomPanelNote.BottomPanelNoteUtils;
import com.pasich.mynotes.utils.constants.LIST_STATUS;
import com.pasich.mynotes.utils.constants.NameTransition;
import com.pasich.mynotes.utils.constants.SnackBarInfo;
import com.pasich.mynotes.utils.recycler.ItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NoteActivity extends BaseActivity implements NoteContract.view {

    public ActivityNoteBinding binding;
    @Inject
    public NoteContract.presenter notePresenter;
    @Inject
    public BottomPanelNoteUtils bottomPanelNoteUtils;
    public ItemListNoteAdapter itemListNoteAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        selectTheme();
        settingsStatusBar(getWindow());
        long idNote = getIntent().getLongExtra("idNote", 0);
        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        binding.noteLayout.setTransitionName(idNote == 0 ? NameTransition.fabTransaction : String.valueOf(idNote));
        setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        getWindow().setSharedElementEnterTransition(buildContainerTransform(binding.noteLayout));
        getWindow().setSharedElementReturnTransition(buildContainerTransform(binding.noteLayout));
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.setPresenter((NotePresenter) notePresenter);
        notePresenter.attachView(this);
        notePresenter.getLoadIntentData(getIntent());
        notePresenter.viewIsReady();
        bottomPanelNoteUtils.setMangerView(binding.noteLayout);

    }

    @Override
    public void onBackPressed() {
        notePresenter.closeActivity();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!notePresenter.getExitNoteSave()) {
            saveNote(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notePresenter.detachView();
        binding.notesTitle.addTextChangedListener(null);
        binding.titleToolbarTag.setOnClickListener(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_toolbar_note, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            notePresenter.closeActivity();

        }
        if (item.getItemId() == R.id.moreBut) {
            if (!notePresenter.getNewNotesKey()) saveNote(true);
            new MoreNoteDialog(notePresenter.getNewNotesKey() ? new Note().create(binding.notesTitle.getText().toString(), binding.valueNote.getText().toString(), new Date().getTime()) : notePresenter.getNote(), notePresenter.getNewNotesKey(), true, 0).show(getSupportFragmentManager(), "MoreNote");

        }


        return true;
    }

    /**
     * Method that enables Motion Animation smooth transition support
     *
     * @param mWindow - activity window
     */
    private void settingsStatusBar(Window mWindow) {
        final int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mWindow.setStatusBarColor(Color.TRANSPARENT);
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        new WindowInsetsControllerCompat(mWindow, mWindow.getDecorView()).setAppearanceLightStatusBars(currentNightMode == Configuration.UI_MODE_NIGHT_NO);
    }


    /**
     * Данный метод настраивает активность на определенный тип работы
     */
    @Override
    public void initTypeActivity() {
        if (notePresenter.getNewNotesKey()) {
            activatedActivity();
        } else {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }

        if (notePresenter.getIdKey() >= 1) {
            notePresenter.loadingData(notePresenter.getIdKey());
        } else {
            onInfoSnack(R.string.errorNotesNotFound, null, SnackBarInfo.Error, Snackbar.LENGTH_LONG);
            finish();
        }
    }


    @Override
    public void initListeners() {
        binding.notesTitle.addTextChangedListener(new TextWatcher() {
            @Override
            protected void changeText(Editable s) {
                if (s.toString().contains("\n")) {
                    binding.notesTitle.setText(s.toString().replace('\n', ' ').trim());
                    binding.valueNote.requestFocus();
                }
            }
        });

        binding.titleToolbarTag.setOnClickListener(v -> openPopupWindowsTag());
    }

    @Override
    public void editIdNoteCreated(long idNote) {
        notePresenter.getNote().setId(Math.toIntExact(idNote));
    }


    @Override
    public void settingsActionBar() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    /**
     * В зависимости от типа редактирования, этот метод активирует клавиатуру и поля ввода
     */
    @Override
    public void activatedActivity() {
        binding.setActivateEdit(true);
        binding.valueNote.setEnabled(true);
        binding.valueNote.setFocusable(true);
        if (!notePresenter.getNewNotesKey())
            binding.valueNote.setSelection(binding.valueNote.getText().length());
        binding.valueNote.setFocusableInTouchMode(true);
        binding.valueNote.requestFocus();

        if (notePresenter.getNewNotesKey()) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInputFromWindow(binding.valueNote.getApplicationWindowToken(), InputMethodManager.SHOW_IMPLICIT, 0);

        } else {
            if (binding.valueNote.requestFocus()) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(binding.valueNote, InputMethodManager.SHOW_IMPLICIT);
            }
        }

    }


    @Override
    public void loadingNote(Note note) {
        binding.notesTitle.setText(note.getTitle().length() >= 1 ? note.getTitle() : "");
        binding.valueNote.setText(note.getValue() == null ? "" : note.getValue());
        binding.valueNote.setMovementMethod(new CustomLinkMovementMethod() {
            @Override
            protected void onClickLink(String link, int type) {
                link = link.replaceAll("mailto:", "").replaceAll("tel:", "");
                new LinkInfoDialog(link, type).show(getSupportFragmentManager(), "LinkInfoDialog");

            }

        });
        binding.titleToolbarData.setText(getString(R.string.lastDateEditNote, lastDayEditNote(note.getDate())));
        changeTag(note.getTag(), false);
    }

    @Override
    public void loadingListNote(List<ItemListNote> listItemsNote) {
        if (listItemsNote.size() >= 1) {
            creteListNoteItems(listItemsNote);
            notePresenter.setStatusList(LIST_STATUS.LOAD);
        } else {
            notePresenter.setStatusList(LIST_STATUS.NOT);
        }

    }

    @Override
    public void closeNoteActivity() {
        binding.getRoot().clearFocus();
        closeKeyboardActivity();
        notePresenter.setExitNoSave(true);
        saveNote(false);
        supportFinishAfterTransition();
    }

    @Override
    public void closeActivityNotSaved() {
        notePresenter.setExitNoSave(true);
        finish();
    }

    private void saveNote(boolean saveLocal) {
        long mThisDate = new Date().getTime();
        String mTitle = binding.notesTitle.getText().toString();
        String mValue = binding.valueNote.getText().toString();
        String mNoteValue = "";
        if (!notePresenter.getNewNotesKey())
            mNoteValue = notePresenter.getNote().getValue() == null ? "" : notePresenter.getNote().getValue();

        if (!saveLocal && notePresenter.getStatusList() != LIST_STATUS.NOT) {
            saveListItems();
        }
        if (checkEditionNote(mValue, mTitle, mNoteValue, mThisDate) && !saveLocal && checkValidText()) {
            if (notePresenter.getNewNotesKey()) {
                notePresenter.setNewNoteKey(false);
            }
            notePresenter.saveNote(notePresenter.getNote());
        } else if (notePresenter.getNewNotesKey() && notePresenter.getShareText() != null && notePresenter.getStatusList() != LIST_STATUS.NEW) {
            //Если в созданой заметке нет изменений то удалим
            notePresenter.deleteNote(notePresenter.getNote());
        }

    }

    /**
     * Метод который проверяеть валидность текста заметки в зависимости от состояния списка
     */
    private boolean checkValidText() {
        if (notePresenter.getStatusList() == LIST_STATUS.NOT || notePresenter.getStatusList() == LIST_STATUS.DELETE) {
            return binding.valueNote.getText().toString().trim().length() >= 2;
        } else {
            return true;
        }
    }

    /**
     * Метод который сохранянет список в зависимости от состояний
     */
    private void saveListItems() {
        List<ItemListNote> saveList = saveList();
        switch (notePresenter.getStatusList()) {

            case LIST_STATUS.NEW -> {
                if (itemListNoteAdapter.getItemCount() > 0) {
                    if (saveList.size() != 0) {
                        saveItemsAndPosition(saveList);
                    }

                }
            }
            case LIST_STATUS.LOAD -> {
                if (saveList.size() > 0) {
                    if (compareLists(notePresenter.getListNotesItems(), saveList) && saveList.size() != 0) {
                        saveItemsAndPosition(saveList);
                    }
                }
            }
            case LIST_STATUS.DELETE -> notePresenter.deleteList((int) notePresenter.getIdKey());
        }
    }

    /**
     * Метод который перед сохранением прикляпляет к елементам их позицию
     */
    private void saveItemsAndPosition(List<ItemListNote> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setDragPosition(i);
        }
        notePresenter.saveItemList(list, itemListNoteAdapter.getDeleteItems());
    }

    /**
     * Метод который сравнивает два списка на наличие изменений и возврщает false если списки не одинаковые
     *
     * @param list1 - список до изменений
     * @param list2 - список с возможними изменениями
     */
    public boolean compareLists(List<ItemListNote> list1, List<ItemListNote> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            ItemListNote item1 = list1.get(i);
            ItemListNote item2 = list2.get(i);

            if (!Objects.equals(item1.getValue(), item2.getValue()) ||
                    item1.getDragPosition() != item2.getDragPosition() ||
                    item1.isChecked() != item2.isChecked()) {
                return false;
            }
        }

        return true;
    }


    /**
     * Метод для сохранения списка, он удаляет со списка пустые строки, и системные строки
     * Если список не пустой отправляет его в базу данных
     */
    private List<ItemListNote> saveList() {
        final List<ItemListNote> listSave = itemListNoteAdapter.getItemsListNote();
        Iterator<ItemListNote> iterator = listSave.iterator();
        while (iterator.hasNext()) {
            ItemListNote itemListNote = iterator.next();
            if (itemListNote.isSystem() || itemListNote.getValue().trim().length() == 0) {
                iterator.remove();
            }
        }
        return listSave;
    }


    /**
     * Метод который проверяет текст заметки на изменения
     */
    private boolean checkEditionNote(String mValue, String mTitle, String mNoteValue, long mThisDate) {
        if (!mValue.equals(mNoteValue) || !mTitle.equals(notePresenter.getNote().getTitle())) {
            boolean x1 = false;
            if (!notePresenter.getNote().getTitle().contentEquals(mTitle)) {
                notePresenter.getNote().setTitle(mTitle);
                x1 = true;
            }
            if (!mNoteValue.contentEquals(mValue)) {
                notePresenter.getNote().setValue(mValue);
                x1 = true;
            }

            if (x1) {
                notePresenter.getNote().setDate(mThisDate);
                return true;
            }

        }
        return false;
    }

    @Override
    public void changeTag(String nameTag, boolean change) {
        if (change) {
            notePresenter.getNote().setTag(nameTag);
            notePresenter.setTagNote(nameTag);
        }
        if (nameTag.length() >= 1) {
            binding.titleToolbarTag.setText(getString(R.string.tagHastag, nameTag));
            binding.titleToolbarTag.setVisibility(View.VISIBLE);
        } else {
            binding.titleToolbarTag.setVisibility(View.GONE);
        }
    }

    private void openPopupWindowsTag() {
        String noteTag = notePresenter.getTagNote().length() == 0 ? notePresenter.getNote().getTag() : notePresenter.getTagNote();
        if (noteTag.length() != 0) {
            new PopupWindowsTagNote(getLayoutInflater(), binding.titleToolbarTag, () -> {
                finish();
                startActivity(new Intent(NoteActivity.this, NoteActivity.class).putExtra("NewNote", true).putExtra("tagNote", noteTag));
            });
        }
    }

    @Override
    public void openCopyNote(long idNote) {
        finish();
        startActivity(new Intent(NoteActivity.this, NoteActivity.class).putExtra("NewNote", false).putExtra("idNote", idNote).putExtra("shareText", "").putExtra("tagNote", ""));
    }


    @Override
    public void changeTextStyle() {
        binding.valueNote.setTypeface(null, notePresenter.getTypeFace(notePresenter.getDataManager().getTypeFaceNoteActivity()));
    }

    @Override
    public void changeTextSizeOnline(int sizeText) {
        binding.valueNote.setTextSize(sizeText == 0 ? 16 : sizeText);
        binding.notesTitle.setTextSize(sizeText == 0 ? 20 : sizeText + 4);
    }

    @Override
    public void changeTextSizeOffline() {
        changeTextSizeOnline(notePresenter.getDataManager().getSizeTextNoteActivity());
    }

    /**
     * Method that implements closing the keyboard programmatically
     */
    private void closeKeyboardActivity() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(binding.valueNote.getWindowToken(), 0);
    }


    @Override
    public void createShortCut() {
        onInfoSnack(R.string.addShortCutSuccessfully, binding.noteLayout, SnackBarInfo.Info, Snackbar.LENGTH_LONG);
    }

    @Override
    public void shortCutDouble() {
        onInfoSnack(R.string.shortCutCreateFallDouble, binding.noteLayout, SnackBarInfo.Info, Snackbar.LENGTH_LONG);
    }


    @Override
    public void createListBox() {
        if (notePresenter.getStatusList() != LIST_STATUS.NOT && !binding.getActivateEdit()) {
            return;
        }
        final boolean[] statusHelper = new boolean[]{notePresenter.getStatusList() == LIST_STATUS.NEW || notePresenter.getStatusList() == LIST_STATUS.LOAD, binding.valueNote.getText().toString().trim().length() >= 2};
        new PopupWindowsCreateListBox(getLayoutInflater(), binding.bottomPanel.addListCheckBox, statusHelper, new PopupWindowsCreateListBoxHelper() {
            @Override
            public void createListForData() {
                creteListNoteItems(generateItemListForDataNotes());
                binding.valueNote.setText("");
                notePresenter.setStatusList(LIST_STATUS.NEW);
                if (!binding.getActivateEdit()) activatedActivity();
            }

            @Override
            public void addListToNote() {
                creteListNoteItems(generateDefaultItemList());
                notePresenter.setStatusList(LIST_STATUS.NEW);
                if (!binding.getActivateEdit()) activatedActivity();
            }

            @Override
            public void deleteList() {
                deleteListValid();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void convertToNote() {
                StringBuilder newValueNote = new StringBuilder();
                if (notePresenter.getStatusList() != LIST_STATUS.NOT) {
                    for (ItemListNote itemListNote : itemListNoteAdapter.getItemsListNote()) {
                        if (!itemListNote.isSystem()) {
                            newValueNote.append(itemListNote.getValue()).append("\n");
                        }
                    }
                }
                deleteListValid();
                binding.valueNote.setText(newValueNote + "\n" + binding.valueNote.getText().toString());
            }
        });
    }

    @Override
    public void addPhotoFiles() {

    }


    private void deleteListValid() {
        if (notePresenter.getStatusList() != LIST_STATUS.NOT) {
            notePresenter.getListNotesItems().clear();
            itemListNoteAdapter.getItemsListNote().clear();
        }
        notePresenter.setStatusList(LIST_STATUS.DELETE);
        binding.listNote.setVisibility(View.GONE);
    }

    /**
     * A method that generates a list from the text of a note
     *
     * @return - list data
     */
    private List<ItemListNote> generateItemListForDataNotes() {
        final List<ItemListNote> itemList = new ArrayList<>();
        final String[] lines = binding.valueNote.getText().toString().split("\n");
        final int idNote = (int) notePresenter.getIdKey();
        int indexNote = 0;


        for (String line : lines) {
            if (!line.isEmpty()) {
                itemList.add(new ItemListNote(line, idNote, indexNote));
                indexNote = indexNote + 1;
            }
        }

        return itemList;
    }


    /**
     * Method that generates a template for a list of elements
     *
     * @return - list default elements
     */
    private List<ItemListNote> generateDefaultItemList() {
        int idNote = notePresenter.getNote().id;
        List<ItemListNote> itemList = new ArrayList<>();
        itemList.add(new ItemListNote("", idNote, 0));
        itemList.add(new ItemListNote("", idNote, 1));
        itemList.add(new ItemListNote("", idNote, 2));
        return itemList;
    }

    /**
     * Method that creates a list and fills it with data
     *
     * @param listItemsNote - list data
     */
    @SuppressLint("NotifyDataSetChanged")
    private void creteListNoteItems(List<ItemListNote> listItemsNote) {
        listItemsNote.add(getDefaultAddItem());
        if (notePresenter.getStatusList() == LIST_STATUS.NOT) {
            itemListNoteAdapter = new ItemListNoteAdapter(listItemsNote);
            ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(itemListNoteAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            itemListNoteAdapter.setItemListSetOnCLickListener(new ItemListSetOnClickListener() {
                @Override
                public void requestDrag(RecyclerView.ViewHolder viewHolder) {
                    touchHelper.startDrag(viewHolder);
                }

                @Override
                public void addItem(RecyclerView.ViewHolder viewHolder) {
                    if (binding.getActivateEdit()) {
                        itemListNoteAdapter.addNewItem(notePresenter.getNote().getId());
                    }
                }

                @Override
                public void refreshFocus(int position) {
                    if (itemListNoteAdapter.getItemCount() >= 2) {
                        View view = binding.listNote.getChildAt((itemListNoteAdapter.getItemCount() - 2));
                        EditText editText = view.findViewById(R.id.valueItem);
                        editText.setFocusable(true);
                        editText.isFocusableInTouchMode();
                        editText.requestFocus();
                        editText.setSelection(editText.getText().length());
                    } else {
                        binding.getRoot().clearFocus();
                        closeKeyboardActivity();
                    }

                }

                @Override
                public boolean isActivatedEdit() {
                    return binding.getActivateEdit();
                }

            });

            binding.listNote.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            binding.listNote.setAdapter(itemListNoteAdapter);
            touchHelper.attachToRecyclerView(binding.listNote);
            binding.listNote.setVisibility(View.VISIBLE);

            notePresenter.setStatusList(LIST_STATUS.NEW);
        } else if (notePresenter.getStatusList() == LIST_STATUS.DELETE) {
            itemListNoteAdapter.setItemsListNote(listItemsNote);
            itemListNoteAdapter.notifyDataSetChanged();
            binding.listNote.setVisibility(View.VISIBLE);

        }
    }



    private ItemListNote getDefaultAddItem() {
        return new ItemListNote(getString(R.string.addListItemButton), (int) notePresenter.getIdKey(), true);
    }
}
