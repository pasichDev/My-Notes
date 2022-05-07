package com.pasich.mynotes.Controllers.Fragments.ViewPagerMain;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.Adapters.ListNotes.ListNotesModel;
import com.pasich.mynotes.Dialogs.ChoiceListDialog;
import com.pasich.mynotes.Dialogs.FolderOptionDialog;
import com.pasich.mynotes.Model.NotesFragmentModel;
import com.pasich.mynotes.NoteActivity;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Constants.SystemConstant;
import com.pasich.mynotes.View.ListNotesView;

public class ListNotesFragment extends Fragment
    implements FolderOptionDialog.EditNameDialogListener {

  private DefaultListAdapter defaultListAdapter;
  private String FOLDER = "";

  protected ListNotesView ListNotesView;
  protected NotesFragmentModel NotesModel;
  protected View view;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    view = inflater.inflate(R.layout.fragment_list_notes, container, false);
    ListNotesView = new ListNotesView(view);
    NotesModel = new NotesFragmentModel(getContext());
    defaultListAdapter =
        new DefaultListAdapter(getContext(), R.layout.list_notes, NotesModel.notesArray);
    ListNotesView.NotesList.setAdapter(defaultListAdapter);

    ListNotesView.NotesList.setOnItemClickListener((parent, v, position, id) -> openNote(position));

    view.findViewById(R.id.newNotesButton).setOnClickListener(this::createNotesButton);

    /**
     * ЄТу хуйню нужно полностью переделать Чтобы для каждого положения было разное диалогвоек окно
     */
    ListNotesView.NotesList.setOnItemLongClickListener(
        (arg0, arg1, position, id) -> {
          ListNotesModel listNotesfor = NotesModel.notesArray.get(position);
          if (!listNotesfor.getNameList().equals("VoiceNotes")) {
            boolean typeFile = listNotesfor.getFolder();
            FragmentManager fm = getFragmentManager();
            ChoiceListDialog dialog =
                new ChoiceListDialog(
                    position, NotesModel.notesArray, defaultListAdapter, typeFile, FOLDER);
            dialog.setTargetFragment(ListNotesFragment.this, 300);
            dialog.show(fm, "ChoiceListDialog");
          }
          return true;
        });

    return view;
  }

  public void openNote(int position) {
    ListNotesModel listNotesfor = NotesModel.notesArray.get(position);
    String selectedItem = listNotesfor.getNameList();
    if (!listNotesfor.getFolder()) {
      Intent intent =
          new Intent(getActivity(), NoteActivity.class)
              .putExtra("KeyFunction", "EditNote")
              .putExtra("idNote", selectedItem)
              .putExtra("folder", FOLDER);
      startActivityForResult(intent, 1);
    } else restartListNotes();
  }

  protected void createNotesButton(View v) {
    if (v.getId() == R.id.newNotesButton) {
      Intent intent =
          new Intent(getActivity(), NoteActivity.class)
              .putExtra("KeyFunction", "NewNote")
              .putExtra("idNote", "null")
              .putExtra("folder", FOLDER);
      startActivityForResult(intent, 12);
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (data == null) return;
  /*  if (data.getStringExtra("checkUpdate").equals("yes"))
      restartListNotes();
      requireActivity()
        .getWindow()
        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);*/
  }


  @Override
  public void onFinishfolderOption(boolean updateList) {
    restartListNotes();
  }

  /** Method that changes the number of columns in a list */
  public void formatListView() {
   ListNotesView.setNotesListCountColumns();
  }

  /** Method that restarts adapter and model with list data */
  public void restartListNotes() {
    if (defaultListAdapter != null) this.defaultListAdapter.clear();
    NotesModel.getUpdateArray();
    ListNotesView.NotesList.setAdapter(
        new DefaultListAdapter(getContext(), R.layout.list_notes, NotesModel.notesArray));
  }
}
