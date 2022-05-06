package com.pasich.mynotes.Controllers.Fragments.ViewPagerMain;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.Adapters.ListNotes.ListNotesModel;
import com.pasich.mynotes.Controllers.Dialogs.ChoiceListDialog;
import com.pasich.mynotes.Controllers.Dialogs.FolderOptionDialog;
import com.pasich.mynotes.NoteActivity;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Interface.IOnBackPressed;
import com.pasich.mynotes.Сore.ListContolers.NotesListData;
import com.pasich.mynotes.Utils.Constants.SystemConstant;

import java.util.ArrayList;

public class FragmentListNotes extends Fragment
    implements FolderOptionDialog.EditNameDialogListener, IOnBackPressed {

  private DefaultListAdapter defaultListAdapter;
  private GridView NotesList;
  private NotesListData NotesListData;
  private ArrayList<ListNotesModel> ListNotesModel;
  private boolean mode_note;
  private String FOLDER = "";
  private boolean mode_noteEdit;

  public static FragmentListNotes newInstance(boolean mode_note) {
    Bundle args = new Bundle();
    args.putBoolean("mode_note", mode_note);
    FragmentListNotes f = new FragmentListNotes();
    f.setArguments(args);
    return f;
  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (isVisibleToUser && isResumed()) {
      getActivity().findViewById(R.id.newNotesButton).setVisibility(View.VISIBLE);
    }
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    mode_note = getArguments().getBoolean("mode_note", true);
    mode_noteEdit = mode_note;

    View view = inflater.inflate(R.layout.fragment_list_notes, container, false);

    NotesList = view.findViewById(R.id.ListFileNotes);
    NotesList.setNumColumns(
        PreferenceManager.getDefaultSharedPreferences(getContext())
            .getInt("formatParam", SystemConstant.Setting_Format));
    NotesListData = new NotesListData(getContext());
    ListNotesModel = NotesListData.newListAdapter("", mode_note);
    defaultListAdapter = new DefaultListAdapter(getContext(), R.layout.list_notes, ListNotesModel);
    NotesList.setAdapter(defaultListAdapter);

    NotesList.setOnItemClickListener(
        (parent, v, position, id) -> {
          ListNotesModel listNotesfor = ListNotesModel.get(position);
          String selectedItem = listNotesfor.getNameList();

          if (!listNotesfor.getBackFolder() == false) {
            mode_noteEdit = true;
            restartListNotes("", mode_note);
          } else if (!listNotesfor.getFolder()) {
            Intent intent = new Intent(getActivity(), NoteActivity.class);
            intent.putExtra("KeyFunction", "EditNote");
            intent.putExtra("idNote", selectedItem);
            intent.putExtra("folder", FOLDER);
            startActivityForResult(intent, 1);
          } else {
            restartListNotes(selectedItem, true);
            mode_noteEdit = true;
          }
        });
    NotesList.setOnItemLongClickListener(
        (arg0, arg1, position, id) -> {
          ListNotesModel listNotesfor = ListNotesModel.get(position);
          if (!listNotesfor.getNameList().equals("VoiceNotes")) {
            boolean typeFile = listNotesfor.getFolder();
            FragmentManager fm = getFragmentManager();
            ChoiceListDialog dialog =
                new ChoiceListDialog(position, ListNotesModel, defaultListAdapter, typeFile, FOLDER);
            dialog.setTargetFragment(FragmentListNotes.this, 300);
            dialog.show(fm, "ChoiceListDialog");
          }
          return true;
        });
    getActivity()
        .findViewById(R.id.newNotesButton)
        .setOnClickListener(
            v -> {
              if (v.getId() == R.id.newNotesButton) {
                Intent intent = new Intent(getActivity(), NoteActivity.class);
                intent.putExtra("KeyFunction", "NewNote");
                intent.putExtra("idNote", "null");
                intent.putExtra("folder", FOLDER);
                startActivityForResult(intent, 1);
              }
            });
    setHasOptionsMenu(true);

    return view;
  }

  private void restartListNotes(String folder, boolean modes) {

    if (defaultListAdapter != null) defaultListAdapter.clear();
    ListNotesModel = NotesListData.newListAdapter(folder, modes);
    defaultListAdapter = new DefaultListAdapter(getContext(), R.layout.list_notes, ListNotesModel);
    NotesList.setAdapter(defaultListAdapter);
    // defaultListAdapter.notifyDataSetChanged();
    NotesList.setNumColumns(
        PreferenceManager.getDefaultSharedPreferences(getContext())
            .getInt("formatParam", SystemConstant.Setting_Format));
    FOLDER = folder;
  }

  @Override // Создание меню и разметки
  public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
    getActivity().getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
    menu.findItem(R.id.addFolder).setVisible(true);
  }

  @Override // Отслеживание нажатия на кнопки тулбара
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.addFolder:
        FragmentManager fm = getFragmentManager();
        FolderOptionDialog editNameDialogFragment = new FolderOptionDialog("");
        editNameDialogFragment.setTargetFragment(FragmentListNotes.this, 300);
        assert fm != null;
        editNameDialogFragment.show(fm, "newFolder");
        return true;
    }
    return false;
  }

  @Override // Слушатель результата возвращения с активити
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (data == null) return;
    if (data.getStringExtra("checkUpdate").equals("yes")) {
      String folder = data.getStringExtra("FOLDER");
      if (folder == null) {
        folder = "";
      }
      restartListNotes(folder, mode_noteEdit);
    }
    getActivity()
        .getWindow()
        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
  }

  @Override // Кнопка назад просле заврешения диалога
  public void onFinishfolderOption(boolean updateList) {
    restartListNotes("", mode_noteEdit);
  }

  // Обновление списка после клика на Spinner
  public void restartListNotes() {
    restartListNotes("", mode_noteEdit);
  }

  @Override
  public boolean onBackPressed() {
    String folder = FOLDER;
    restartListNotes("", mode_noteEdit);
    return folder.length() == 0 ? false : true;
  }
}
