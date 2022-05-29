package com.pasich.mynotes.Controllers.Activity;

import static java.util.Objects.requireNonNull;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.Controllers.Dialogs.ChoiceNoteDialog;
import com.pasich.mynotes.Controllers.Dialogs.ChooseMoreActivityDialog;
import com.pasich.mynotes.Controllers.Dialogs.ChooseSortDialog;
import com.pasich.mynotes.Controllers.Dialogs.NewTagDialog;
import com.pasich.mynotes.Models.Adapter.NoteItemModel;
import com.pasich.mynotes.Models.MainModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.ActionUtils;
import com.pasich.mynotes.Utils.Adapters.ListNotesAdapter;
import com.pasich.mynotes.Utils.Interface.ChoiceNoteInterface;
import com.pasich.mynotes.Utils.Interface.ManageTag;
import com.pasich.mynotes.Utils.Interface.MoreActivInterface;
import com.pasich.mynotes.Utils.Interface.SortInterface;
import com.pasich.mynotes.Utils.MainUtils;
import com.pasich.mynotes.Utils.Simplifications.TabLayoutListenerUtils;
import com.pasich.mynotes.Utils.SwitchButtons.FormatSwitchUtils;
import com.pasich.mynotes.Utils.Utils.ShareNoteUtils;
import com.pasich.mynotes.View.MainView;
import com.pasich.mynotes.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements ManageTag,
        View.OnClickListener,
        ChoiceNoteInterface,
        SortInterface,
        MoreActivInterface {

  private MainView MainView;
  private MainUtils MainUtils;
  private ListNotesAdapter ListNotesAdapter;
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
    createListVew();
    loadDataTabLayout();

    initListener();

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

  /** The method that sets up the toolbar */
  private void setToolbar() {
    setSupportActionBar(binding.toolbarActionbar.toolbarActionbar);
    requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
  }

  /** Method that creates and populates a ListVIew */
  private void createListVew() {
    ListNotesAdapter = new ListNotesAdapter(this, MainModel.notesArray);
    binding.listNotes.setAdapter(ListNotesAdapter);
    emptyListViewUtil();
    initActionUtils();
  }

  /** Method that loads layouts in TabLayout */
  private void loadDataTabLayout() {
    for (String nameTag : MainModel.tags) {
      binding.Tags.addTab(binding.Tags.newTab().setText(nameTag));
    }
    requireNonNull(binding.Tags.getTabAt(1)).select();
  }

  /** Method to manage listeners */
  private void initListener() {
    MainView.sortButton.setOnClickListener(this);
    MainView.formatButton.setOnClickListener(this);
    binding.newNotesButton.setOnClickListener(this);
    binding.moreActivity.setOnClickListener(this);

    binding.Tags.addOnTabSelectedListener(
        new TabLayoutListenerUtils() {
          @Override
          public void listener(TabLayout.Tab Tab) {
            if (Tab.getPosition() == 0) {
              createTagItem(unselectedPosition);
            } else if (Tab.getPosition() != 1 && Tab.getPosition() != 0) {
              restartListNotes(requireNonNull(Tab.getText()).toString());
            } else if (Tab.getPosition() == 1 && unselectedPosition != 0) {
              restartListNotes("");
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
            new ChoiceNoteDialog(position, ListNotesAdapter.getItem(position).getId())
                .show(getSupportFragmentManager(), "ChoiceDialog");
          }
        });
  }

  /**
   * The method that implements the creation of a tag
   *
   * @param unselectedPosition - last selected item
   */
  private void createTagItem(int unselectedPosition) {
    if (binding.Tags.getTabCount() <= 10) {
      new NewTagDialog().show(getSupportFragmentManager(), "New Tab");
    } else Toast.makeText(this, getString(R.string.countTagsError), Toast.LENGTH_LONG).show();
    requireNonNull(binding.Tags.getTabAt(unselectedPosition)).select();
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.moreActivity) {
      new ChooseMoreActivityDialog(
              ListNotesAdapter.getItemCount(), binding.Tags.getSelectedTabPosition())
          .show(getSupportFragmentManager(), "more activity");
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

  /**
   * Method that implements adapter updates with new data
   *
   * @param tag - selected tag
   */
  @SuppressLint("NotifyDataSetChanged")
  public void restartListNotes(String tag) {
    final LayoutAnimationController controller =
        AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);
    binding.listNotes.setLayoutAnimation(controller);
    ListNotesAdapter.getData().clear();
    MainModel.getUpdateCursor(tag);
    ListNotesAdapter.notifyDataSetChanged();
    binding.listNotes.scheduleLayoutAnimation();
    emptyListViewUtil();
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
    ArrayList<NoteItemModel> filteredlist = new ArrayList<>();

    // running a for loop to compare elements.
    for (NoteItemModel item : MainModel.notesArray) {
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

    if (MainModel.createTag(tagName)) {
      binding.Tags.addTab(binding.Tags.newTab().setText(tagName), 2);
      if (noteId == 0) binding.Tags.selectTab(binding.Tags.getTabAt(2));
    }
    if (noteId != 0) addTagForNote(tagName, noteId, position);
  }

  @Override
  public void addTagForNote(String tagName, int noteId, int position) {
    if (noteId != 0 && tagName.length() >= 2) {
      MainModel.setNoteTagQuery(noteId, tagName);
      ListNotesAdapter.getItem(position).setTag(tagName);
      ListNotesAdapter.notifyItemChanged(position);
    }
  }

  @Override
  public void deleteTag(boolean deleteNotes) {
    int tagPosition = binding.Tags.getSelectedTabPosition();
    if (binding.Tags.getTabCount() > 2 && tagPosition > 1) {
      MainModel.deleteTag(getNameTagUtil(), deleteNotes);
      binding.Tags.removeTab(requireNonNull(binding.Tags.getTabAt(tagPosition)));
      requireNonNull(binding.Tags.getTabAt(1)).select();
    } else {
      Toast.makeText(this, getString(R.string.errorDeleteTag), Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    MainModel.closeDB();
  }

  private void openNote(int id) {
    startActivity.launch(
        new Intent(this, NoteActivity.class)
            .putExtra("NewNote", false)
            .putExtra("idNote", id)
            .putExtra("tagNote", ""));
  }

  private String getNameTagUtil() {
    int position = binding.Tags.getSelectedTabPosition();
    return position > 1
        ? requireNonNull(requireNonNull(binding.Tags.getTabAt(position)).getText()).toString()
        : "";
  }

  private void emptyListViewUtil() {
    if (ListNotesAdapter.getItemCount() == 0) {
      binding.listNotes.setVisibility(View.GONE);
      findViewById(R.id.emptyListVIew).setVisibility(View.VISIBLE);
    } else {
      binding.listNotes.setVisibility(View.VISIBLE);
      findViewById(R.id.emptyListVIew).setVisibility(View.GONE);
    }
  }

  public void deleteNotesArray() {
    for (int noteID : ActionUtils.getArrayChecked()) {
      MainModel.notesMove(noteID, MainModel.DbHelper.COLUMN_TRASH, MainModel.DbHelper.COLUMN_NOTES);
    }
    restartListNotes(getNameTagUtil());
    ActionUtils.closeActionPanel();
  }

  @Override
  public void shareNote(int item) {
    new ShareNoteUtils(this, ListNotesAdapter.getItem(item).getId()).shareNotes();
  }

  @Override
  public void deleteNote(int noteID, int position) {
    ListNotesAdapter.getData().remove(position);
    MainModel.notesMove(noteID, MainModel.DbHelper.COLUMN_TRASH, MainModel.DbHelper.COLUMN_NOTES);
    ListNotesAdapter.notifyItemRemoved(position);
    emptyListViewUtil();
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
}
