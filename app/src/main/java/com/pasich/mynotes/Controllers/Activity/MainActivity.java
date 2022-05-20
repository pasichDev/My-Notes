package com.pasich.mynotes.Controllers.Activity;

import static java.util.Objects.requireNonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.Controllers.Dialogs.ChoiceNoteDialog;
import com.pasich.mynotes.Controllers.Dialogs.DeleteTagDialog;
import com.pasich.mynotes.Controllers.Dialogs.NewTagDialog;
import com.pasich.mynotes.Model.Adapter.ListNotesModel;
import com.pasich.mynotes.Model.MainModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.ActionUtils;
import com.pasich.mynotes.Utils.Adapters.DefaultListAdapter;
import com.pasich.mynotes.Utils.Anim.ListViewAnimation;
import com.pasich.mynotes.Utils.Interface.ChoiceNoteInterface;
import com.pasich.mynotes.Utils.Interface.ManageTag;
import com.pasich.mynotes.Utils.MainUtils;
import com.pasich.mynotes.Utils.Simplifications.TabLayoutListenerUtils;
import com.pasich.mynotes.Utils.SwitchButtons.FormatSwitchUtils;
import com.pasich.mynotes.Utils.SwitchButtons.SortSwitchUtils;
import com.pasich.mynotes.View.MainView;

public class MainActivity extends AppCompatActivity
    implements ManageTag, View.OnClickListener, ChoiceNoteInterface {

  private MainView MainView;
  private MainUtils MainUtils;
  private DefaultListAdapter defaultListAdapter;
  private MainModel MainModel;
  /** Processing the received response from running activities */
  protected ActivityResultLauncher<Intent> startActivity =
      registerForActivityResult(
          new ActivityResultContracts.StartActivityForResult(),
          result -> {
            if (result.getResultCode() == 24 && result.getData() != null) {
              if (result.getData().getBooleanExtra("RestartListView", false))
                restartListNotes(result.getData().getStringExtra("tagNote"));
            }
          });

  private ActionUtils ActionUtils;
  private SortSwitchUtils sortSwitch;
  private FormatSwitchUtils formatSwitch;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initResourece();
    setToolbar();

    defaultListAdapter = new DefaultListAdapter(this, R.layout.list_notes, MainModel.notesArray);
    MainView.ListView.setAdapter(defaultListAdapter);
    emptyListViewUtil();

    initListener();
  }

  /** The method that sets up the toolbar */
  private void setToolbar() {
    setSupportActionBar(MainView.toolbar);
    requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
  }

  /** Method to manage listeners */
  private void initListener() {
    MainView.sortButton.setOnClickListener(this);
    MainView.formatButton.setOnClickListener(this);
    MainView.newNotesButton.setOnClickListener(this);
    MainView.deleteTag.setOnClickListener(this);

    MainView.TabLayout.addOnTabSelectedListener(
        new TabLayoutListenerUtils() {
          @Override
          public void listener(TabLayout.Tab Tab) {

            if (Tab.getPosition() == 0) {
              createTagItem(unselectedPosition);

            } else if (Tab.getPosition() != 1 && Tab.getPosition() != 0) {

              restartListNotes(requireNonNull(Tab.getText()).toString());
              MainView.deleteTag.setVisibility(View.VISIBLE);
            } else if (Tab.getPosition() == 1 && unselectedPosition != 0) {
              restartListNotes("");
              MainView.deleteTag.setVisibility(View.GONE);
            }
          }
        });

    MainView.ListView.setOnItemClickListener(
        (parent, v, position, id) -> {
          if (!ActionUtils.getAction()) openNote(defaultListAdapter.getItem(position).getId());
          else selectedItemAction(defaultListAdapter.getItem(position));
        });
    MainView.ListView.setOnItemLongClickListener(
        (arg0, arg1, position, id) -> {
          new ChoiceNoteDialog(position).show(getSupportFragmentManager(), "ChoiceDialog");
          return true;
        });
  }

  /**
   * The method that implements the creation of a tag
   *
   * @param unselectedPosition - last selected item
   */
  private void createTagItem(int unselectedPosition) {
    if (MainView.TabLayout.getTabCount() <= 10) {
      new NewTagDialog().show(getSupportFragmentManager(), "New Tab");
      requireNonNull(MainView.TabLayout.getTabAt(unselectedPosition)).select();

    } else Toast.makeText(this, getString(R.string.countTagsError), Toast.LENGTH_LONG).show();
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.sortButton) {
      sortSwitch.sortNote();
      restartListNotes("");
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
    if (v.getId() == R.id.deleteTag) {
      new DeleteTagDialog(defaultListAdapter.getCount())
          .show(getSupportFragmentManager(), "Delete Tag");
    }
  }

  public void restartListNotes(String tag) {
    defaultListAdapter.getData().clear();
    MainModel.getUpdateCursor(tag);
    defaultListAdapter.notifyDataSetChanged();
    if (defaultListAdapter.getCount() > 0)
      ListViewAnimation.setListviewAnimAlphaTranslate(MainView.ListView);
    emptyListViewUtil();
  }

  private void selectedItemAction(ListNotesModel item) {
    if (item.getChecked()) {
      item.setChecked(false);
      item.getView().setBackground(getDrawable(R.drawable.item_note_background));
      if (defaultListAdapter.getCountChecked() == 0) {
        MainView.deactivationActionPanel();
        ActionUtils.setAction(false);
        defaultListAdapter.setChekClean();
      }
    } else {
      item.setChecked(true);
      item.getView().setBackground(getDrawable(R.drawable.item_note_background_selected));
    }
  }

  private void initResourece() {
    MainView = new MainView(getWindow().getDecorView());
    MainUtils = new MainUtils();
    MainModel = new MainModel(this);
    ActionUtils = new ActionUtils();
    sortSwitch = new SortSwitchUtils(this, MainView.sortButton);
    formatSwitch = new FormatSwitchUtils(this, MainView.formatButton);
  }

  @Override
  public void onResume() {
    super.onResume();
    while (MainModel.tags.moveToNext()) {
      MainView.TabLayout.addTab(MainView.TabLayout.newTab().setText(MainModel.tags.getString(0)));
    }
  }

  @Override
  public void onBackPressed() {
    MainUtils.CloseApp(MainActivity.this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
    menu.findItem(R.id.setingsBut).setVisible(true);
    menu.findItem(R.id.searchBut).setVisible(true);
    menu.findItem(R.id.trashButton).setVisible(true);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.setingsBut)
      startActivity.launch(new Intent(this, SettingsActivity.class));
    if (item.getItemId() == R.id.trashButton)
      startActivity.launch(new Intent(this, TrashActivity.class));
    return false;
  }

  @Override
  public void addTag(String tagName) {
    MainModel.createTag(tagName);
    MainView.TabLayout.addTab(MainView.TabLayout.newTab().setText(tagName), 2);
  }

  @Override
  public void deleteTag(boolean deleteNotes) {
    int tagPosition = MainView.TabLayout.getSelectedTabPosition();
    if (MainView.TabLayout.getTabCount() > 2 && tagPosition > 1) {
      MainModel.deleteTag(getNameTagUtil(), deleteNotes);
      MainView.TabLayout.removeTab(requireNonNull(MainView.TabLayout.getTabAt(tagPosition)));
      requireNonNull(MainView.TabLayout.getTabAt(1)).select();
    } else {
      Toast.makeText(this, getString(R.string.errorDeleteTag), Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    MainModel.closeConnection();
  }

  private void openNote(int id) {
    startActivity.launch(
        new Intent(this, NoteActivity.class)
            .putExtra("NewNote", false)
            .putExtra("idNote", id)
            .putExtra("tagNote", ""));
  }

  private String getNameTagUtil() {
    int position = MainView.TabLayout.getSelectedTabPosition();
    return position > 1 ? MainView.TabLayout.getTabAt(position).getText().toString() : "";
  }

  private void emptyListViewUtil() {
    if (defaultListAdapter.getCount() == 0) {
      MainView.ListView.setVisibility(View.GONE);
      findViewById(R.id.emptyListVIew).setVisibility(View.VISIBLE);
    } else {
      MainView.ListView.setVisibility(View.VISIBLE);
      findViewById(R.id.emptyListVIew).setVisibility(View.GONE);
    }
  }

  @Override
  public void shareNote(int item) {
    /**
     * а тут у нас ошибка, в первю выводиться весь текст для адаптера это важко нужно как-то
     * реализовать получение всего текста а превюху обрезать
     */
    // ShareNoteUtils.shareNotes(this, defaultListAdapter.getItem(item).);
  }
}
