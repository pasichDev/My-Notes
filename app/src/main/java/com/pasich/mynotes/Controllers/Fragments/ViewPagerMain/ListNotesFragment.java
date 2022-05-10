package com.pasich.mynotes.Controllers.Fragments.ViewPagerMain;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.Controllers.Dialogs.ChoiсeDialog.ChoiceFolderDialog;
import com.pasich.mynotes.Controllers.Dialogs.ChoiсeDialog.ChoiceNoteDialog;
import com.pasich.mynotes.Model.FragmentModel.NotesFragmentModel;
import com.pasich.mynotes.NoteActivity;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Interface.IOnBackPressed;
import com.pasich.mynotes.View.ListNotesView;

public class ListNotesFragment extends Fragment implements IOnBackPressed {

  private DefaultListAdapter defaultListAdapter;
  private String selectFolder = "";
  private ListNotesView ListNotesView;
  private NotesFragmentModel NotesModel;
  private final ActivityResultLauncher<Intent> startActivity =
      registerForActivityResult(
          new ActivityResultContracts.StartActivityForResult(),
          result -> {
            Intent data = result.getData();
            if (result.getResultCode() == 44 && result.getData() != null)
              if (data.getStringExtra("updateList").equals("yes")) restartListNotes();
          });

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_list_notes, container, false);
    ListNotesView = new ListNotesView(view);
    NotesModel = new NotesFragmentModel(getContext());
    defaultListAdapter =
        new DefaultListAdapter(getContext(), R.layout.list_notes, NotesModel.notesArray);
    ListNotesView.NotesList.setAdapter(defaultListAdapter);

    ListNotesView.NotesList.setOnItemClickListener((parent, v, position, id) -> openNote(position));
    view.findViewById(R.id.newNotesButton).setOnClickListener(this::createNotesButton);
    ListNotesView.NotesList.setOnItemLongClickListener(
        (arg0, arg1, position, id) -> {
          openChoiceNote(position);
          return true;
        });

    return view;
  }

  @Override
  public boolean onBackPressed() {
    int countFolderSize = getSelectFolder().length();
    if (countFolderSize != 0) exitFolder();
    return countFolderSize != 0;
  }

  /** Method that changes the number of columns in a list */
  public void formatListView() {
    ListNotesView.setNotesListCountColumns();
  }

  /** Method that restarts adapter and model with list data */
  public void restartListNotes() {
    defaultListAdapter.getData().clear();
    NotesModel.getUpdateArray(getSelectFolder());
    defaultListAdapter.addAll(NotesModel.notesArray);
    defaultListAdapter.notifyDataSetChanged();
  }

  /**
   * Method that removes an element from an array
   *
   * @param position - position Item
   */
  public void removeItems(int position) {
    defaultListAdapter.getData().remove(position);
    defaultListAdapter.notifyDataSetChanged();
  }

  /**
   * Open folder request in real life fragment
   *
   * @return - select folder
   */
  public String getSelectFolder() {
    return this.selectFolder;
  }

  /**
   * A method that closes an open folder, i.e. removes the folder name variable and updates the
   * given adapter
   */
  private void exitFolder() {
    selectFolder = "";
    restartListNotes();
  }

  /**
   * Method that regulates the click of opening a note, possible options, close the folder, open the
   * note, open the folder
   *
   * @param position - the position of the selected in the array, needed to define the annotation
   */
  private void openNote(int position) {
    if (defaultListAdapter.getItem(position).getBackFolder()) exitFolder();
    else if (!defaultListAdapter.getItem(position).getFolder()) {
      startActivity.launch(
          new Intent(getActivity(), NoteActivity.class)
              .putExtra("KeyFunction", "EditNote")
              .putExtra("idNote", defaultListAdapter.getItem(position).getNameList())
              .putExtra("folder", getSelectFolder()));
    } else {
      selectFolder = defaultListAdapter.getItem(position).getNameList();
      restartListNotes();
    }
  }

  /**
   * Method that implements the opening of the action menu on folders and notes
   *
   * @param position - position for arraylist
   */
  private void openChoiceNote(int position) {
    if (defaultListAdapter.getItem(position).getFolder()) {
      new ChoiceFolderDialog(
              new String[] {
                Integer.toString(position), defaultListAdapter.getItem(position).getNameList()
              })
          .show(getChildFragmentManager(), "ChoiceFolderDialog");
    } else {
      new ChoiceNoteDialog(
              new String[] {
                Integer.toString(position),
                defaultListAdapter.getItem(position).getNameList(),
                getSelectFolder()
              })
          .show(getChildFragmentManager(), "ChoiceNoteDialog");
    }
  }

  /**
   * Method for creating a new note
   *
   * @param v - view
   */
  private void createNotesButton(View v) {
    if (v.getId() == R.id.newNotesButton)
      startActivity.launch(
          new Intent(getActivity(), NoteActivity.class)
              .putExtra("KeyFunction", "NewNote")
              .putExtra("idNote", "null")
              .putExtra("folder", getSelectFolder()));
  }
}
