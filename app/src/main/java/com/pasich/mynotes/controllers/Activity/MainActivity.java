package com.pasich.mynotes.controllers.Activity;

import static java.util.Objects.requireNonNull;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.ActionUtils;
import com.pasich.mynotes.Utils.Adapters.ListNotesAdapter;
import com.pasich.mynotes.Utils.Adapters.TagListAdapter;
import com.pasich.mynotes.Utils.Interface.ChoiceNoteInterface;
import com.pasich.mynotes.Utils.Interface.ManageTag;
import com.pasich.mynotes.Utils.Interface.MoreActivInterface;
import com.pasich.mynotes.Utils.Interface.SortInterface;
import com.pasich.mynotes.Utils.MainUtils;
import com.pasich.mynotes.Utils.SwitchButtons.FormatSwitchUtils;
import com.pasich.mynotes.Utils.Utils.ShareNoteUtils;
import com.pasich.mynotes.View.MainView;
import com.pasich.mynotes.controllers.Dialogs.ChoiceNoteDialog;
import com.pasich.mynotes.controllers.Dialogs.ChoiceTagDialog;
import com.pasich.mynotes.controllers.Dialogs.ChooseMoreActivityDialog;
import com.pasich.mynotes.controllers.Dialogs.ChooseSortDialog;
import com.pasich.mynotes.controllers.Dialogs.NewTagDialog;
import com.pasich.mynotes.databinding.ActivityMainBinding;
import com.pasich.mynotes.models.MainModel;
import com.pasich.mynotes.models.adapter.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements ManageTag,
        View.OnClickListener,
        ChoiceNoteInterface,
        SortInterface,
        MoreActivInterface {

  private MainView MainView;
  private MainUtils MainUtils;
  private ListNotesAdapter ListNotesAdapter;
  private TagListAdapter TagListAdapter;
  private MainModel MainModel;
  private ActionUtils ActionUtils;
  private FormatSwitchUtils formatSwitch;
  private ActivityMainBinding binding;


  /** Processing the received response from running activities */
  protected ActivityResultLauncher<Intent> startActivity =
      registerForActivityResult(
          new ActivityResultContracts.StartActivityForResult(), this::onActivityResult);



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    MainView = new MainView(binding);
    MainUtils = new MainUtils();
    MainModel = new MainModel(this);
    formatSwitch = new FormatSwitchUtils(this, MainView.formatButton);

    setToolbar();
    createListNotes();
    createListTags();

    initListener();
    initActionSearch();
  }







  /** The method that sets up the toolbar */
  private void setToolbar() {
    setSupportActionBar(binding.toolbarActionbar.toolbarActionbar);
    requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
  }

  /** Method that creates and populates a ListVIew */
  private void createListNotes() {
    ListNotesAdapter = new ListNotesAdapter(MainModel.notesArray);
    binding.listNotes.setAdapter(ListNotesAdapter);

    binding.setEmptyNotes(ListNotesAdapter.getData().isEmpty());
    initActionUtils();
  }

  private void createListTags() {
    TagListAdapter = new TagListAdapter(MainModel.tagsArray);
    binding.listTags.setAdapter(TagListAdapter);
  }

  /** Method to manage listeners */
  private void initListener() {
    MainView.sortButton.setOnClickListener(this);
    MainView.formatButton.setOnClickListener(this);
    binding.newNotesButton.setOnClickListener(this);
    binding.moreActivity.setOnClickListener(this);

    TagListAdapter.setOnItemClickListener(
        new TagListAdapter.OnItemClickListener() {

          @Override
          public void onClick(int position) {
            int SystemAction = MainModel.tagsArray.get(position).getSystemAction();
            if (SystemAction == 1) {
              createTagItem();
            } else {
              TagListAdapter.chooseTag(position);
              restartListNotes(
                  TagListAdapter.getCheckedPosition() == 1
                      ? ""
                      : MainModel.tagsArray.get(TagListAdapter.getCheckedPosition()).getNameTag());
            }
          }

          @Override
          public void onLongClick(int position) {
            if (MainModel.tagsArray.get(position).getSystemAction() == 0) {
              String[] keysNote = {
                String.valueOf(position),
                String.valueOf(MainModel.tagsArray.get(position).getNameTag()),
                String.valueOf(
                    MainModel.getCountNotesTag(MainModel.tagsArray.get(position).getNameTag()))
              };
              new ChoiceTagDialog(keysNote).show(getSupportFragmentManager(), "ChoiceDialog");
            }
          }
        });

    ListNotesAdapter.setOnItemClickListener(
        new ListNotesAdapter.OnItemClickListener() {

          @Override
          public void onClick(int position) {
            if (!ActionUtils.getAction()) openNote(ListNotesAdapter.getItem(position).getId());
            else ActionUtils.selectItemAction(position);


          }

          @Override
          public void onLongClick(int position) {
            String[] keysNote = {
              String.valueOf(position),
              String.valueOf(ListNotesAdapter.getItem(position).getId()),
              ListNotesAdapter.getItem(position).getDate(),
              String.valueOf(ListNotesAdapter.getItem(position).getValue().length())
            };
            new ChoiceNoteDialog(keysNote).show(getSupportFragmentManager(), "ChoiceDialog");
          }
        });
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.moreActivity) {
      new ChooseMoreActivityDialog().show(getSupportFragmentManager(), "more activity");
    }

    if (v.getId() == R.id.sortButton) {
      new ChooseSortDialog().show(getSupportFragmentManager(), "Sort Dialog");
    }
    if (v.getId() == R.id.formatButton) {
      formatSwitch.formatNote();
      MainView.setNotesListCountColumns();
    }
    if (v.getId() == R.id.newNotesButton) {
      startActivity.launch(
          new Intent(this, NoteActivity.class)
              .putExtra("NewNote", true)
              .putExtra("tagNote", getNameTagUtil()));
    }

    if (v.getId() == ActionUtils.getActionPanel().findViewById(R.id.deleteNotesArray).getId()) {
      deleteNotesArray();
    }
  }

  @Deprecated
  private void initActionSearch() {
    /*
        binding.actionSearch.setOnQueryTextFocusChangeListener(
            (v, hasFocus) -> {
              Log.wtf("pasic", "focus  " + hasFocus);
              MainView.sortButton.setVisibility(View.GONE);
              MainView.formatButton.setVisibility(View.GONE);
            });
        binding.actionSearch.setOnCloseListener(
            () -> {
              Log.wtf("pasic", "close  ");
              MainView.sortButton.setVisibility(View.VISIBLE);
              MainView.formatButton.setVisibility(View.VISIBLE);
              binding.actionSearch.setFocusable(false);
              return false;
            });
    */
    binding.actionSearch.setOnQueryTextListener(
        new SearchView.OnQueryTextListener() {
          @Override
          public boolean onQueryTextSubmit(String query) {
            return false;
          }

          @Override
          public boolean onQueryTextChange(String newText) {

            filter(newText);
            return false;
          }
        });
  }

  /** The method that implements the creation of a tag */
  private void createTagItem() {
    if (TagListAdapter.getItemCount() <= 10) {
      new NewTagDialog().show(getSupportFragmentManager(), "New Tab");
    } else Toast.makeText(this, getString(R.string.countTagsError), Toast.LENGTH_LONG).show();


  }


  /**
   * Method that implements adapter updates with new data
   *
   * @param tag - selected tag
   */
  @SuppressLint("NotifyDataSetChanged")
  public void restartListNotes(String tag) {
    MainModel.getUpdateCursor(tag);
    ListNotesAdapter.notifyDataSetChanged();
    binding.listNotes.scheduleLayoutAnimation();
    binding.setEmptyNotes(ListNotesAdapter.getData().isEmpty());
  }

  private void initActionUtils() {
    ActionUtils = new ActionUtils(binding.getRoot(), ListNotesAdapter, R.id.activity_main);
    ActionUtils.addButtonToActionPanel(R.drawable.ic_delete, R.id.deleteNotesArray);
    ActionUtils.getActionPanel().findViewById(R.id.deleteNotesArray).setOnClickListener(this);
  }

  @Override
  public void onBackPressed() {
    MainUtils.CloseApp(MainActivity.this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);

    return true;
  }

  private void filter(String text) {
    // creating a new array list to filter our data.
    ArrayList<NoteModel> filteredlist = new ArrayList<>();

    // running a for loop to compare elements.
    for (NoteModel item : MainModel.notesArray) {
      // checking if the entered string matched with any item of our recycler view.
      if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
        // if the item is matched we are
        // adding it to our filtered list.
        filteredlist.add(item);
      }
    }
    if (filteredlist.isEmpty()) {
      // if no item is added in filtered list we are
      // displaying a toast message as no data found.
      Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
    } else {
      // at last we are passing that filtered
      // list to our adapter class.
      ListNotesAdapter.filterList(filteredlist);
    }
  }

  @Override
  public void addTag(String tagName, int noteId, int position) {
    boolean createTag = MainModel.createTag(tagName);
    if (noteId != 0 && createTag) addTagForNote(tagName, noteId, position);
  }

  @Override
  public void addTagForNote(String tagName, int noteId, int position) {
    if (noteId != 0) {
      MainModel.setNoteTagQuery(noteId, tagName);
      ListNotesAdapter.getItem(position).setTag(tagName);
      ListNotesAdapter.notifyItemChanged(position);
    }
  }

  @Override
  public void deleteTag(boolean deleteNotes, int position) {
    MainModel.deleteTag(MainModel.tagsArray.get(position).getNameTag(), deleteNotes);
    MainModel.tagsArray.remove(position);
    TagListAdapter.notifyItemRemoved(position);
    chooseAllTag();
  }



  @Override
  public void onDestroy() {
    super.onDestroy();
    // MainModel.closeDB();
  }

  private void openNote(int id) {
    startActivity.launch(
        new Intent(this, NoteActivity.class)
            .putExtra("NewNote", false)
            .putExtra("idNote", id)
            .putExtra("tagNote", ""));
  }

  private String getNameTagUtil() {

    return "";
  }

  public void deleteNotesArray() {
    /**
     * Вообщем весь абсурд єтой ситуации это то что все определенные заметки удаляються из базы
     * данных но они не удаляються из адапатера а точнее удаляються но как попало
     */
    for (long noteID : ActionUtils.getArrayChecked()) {
      Log.wtf("pasic", String.valueOf(Math.toIntExact(noteID)));
      testFunctionCleanList((int) noteID);
      MainModel.notesMove(
          (int) noteID, MainModel.DbHelper.COLUMN_TRASH, MainModel.DbHelper.COLUMN_NOTES);
    }

    ListNotesAdapter.notifyDataSetChanged();

    ActionUtils.closeActionPanel();
  }

  @Deprecated
  /** Это простой пример реалазаци удаления нескольких елементов из списка Нужно его использовать */
  private void testFunctionCleanList(int noteID) {
    List<NoteModel> data = ListNotesAdapter.getData();
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).getId() == noteID) data.remove(i);
    }
  }

  @Override
  public void shareNote(int item) {
    new ShareNoteUtils(this, ListNotesAdapter.getItem(item).getId()).shareNotes();
  }

  @Override
  public void deleteNote(int noteID, int position) {
    MainModel.notesArray.remove(ListNotesAdapter.getItem(position));
    MainModel.notesMove(noteID, MainModel.DbHelper.COLUMN_TRASH, MainModel.DbHelper.COLUMN_NOTES);
    ListNotesAdapter.notifyItemRemoved(position);
    binding.setEmptyNotes(ListNotesAdapter.getData().isEmpty());
  }

  @Override
  public void actionNote(int item) {
    ActionUtils.selectItemAction(item);
  }

  @SuppressLint("NotifyDataSetChanged")
  @Override
  public void sortList(String sortParam) {
    MainModel.arraySort();
    ListNotesAdapter.notifyDataSetChanged();
  }

  @Override
  public void startTrashActivity() {
    startActivity.launch(new Intent(this, TrashActivity.class));
  }

  @Override
  public void startSettingsActivity() {
    startActivity.launch(new Intent(this, SettingsActivity.class));
  }

  private void onActivityResult(ActivityResult result) {
    if (result.getResultCode() == 24 && result.getData() != null
        || result.getResultCode() == 44 && result.getData() != null) {
      if (result.getData().getBooleanExtra("RestartListView", false))
        restartListNotes(result.getData().getStringExtra("tagNote"));
    }
  }

  private void chooseAllTag() {
    TagListAdapter.chooseTag(1);
    restartListNotes("");
  }
}
