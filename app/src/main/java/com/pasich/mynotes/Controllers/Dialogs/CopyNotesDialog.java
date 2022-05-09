package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.Adapters.SpinnerNotes.FolderSpinnerAdapter;
import com.pasich.mynotes.R;
import com.pasich.mynotes.View.CustomView.CustomUIDialog;
import com.pasich.mynotes.Utils.File.FileCore;

import org.apache.commons.io.comparator.NameFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CopyNotesDialog extends DialogFragment {

  private final ArrayList listNotesfors;
  public final String nameNotes, folderOutput;
  private final int pos;
  private final DefaultListAdapter defaultListAdapter;
  private int getItem;

  public CopyNotesDialog(
      ArrayList ListNotesfors,
      DefaultListAdapter defaultListAdapter,
      String nameNotes,
      int pos,
      String folderOutput) {

    this.defaultListAdapter = defaultListAdapter;
    this.listNotesfors = ListNotesfors;
    this.nameNotes = nameNotes;
    this.pos = pos;
    this.folderOutput = folderOutput;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    FileCore fileCore = new FileCore(getContext());
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    File[] listFolders = getContext().getFilesDir().listFiles();
    List<String> folderListArray = new ArrayList<String>();
    CustomUIDialog uiDialog = new CustomUIDialog(getContext(), getLayoutInflater());
    TextView textMessage = new TextView(getContext());
    Arrays.sort(listFolders, NameFileComparator.NAME_COMPARATOR);

    uiDialog.setHeadTextView(getString(R.string.copyNotesTo));

    if (folderOutput.length() >= 1) {
      folderListArray.add(getString(R.string.rootFolder));
    }
    for (File folderSel : listFolders) {
      if (folderSel.isDirectory()
          && !folderSel.getName().equals("trash")
          && !folderSel.getName().equals("VoiceNotes")
          && !folderOutput.equals(folderSel.getName())) {
        folderListArray.add(folderSel.getName());
      }
    }

    if (folderListArray.size() >= 1) {
      Spinner spinner = new Spinner(getContext());
      spinner.setAdapter(new FolderSpinnerAdapter(getContext(), folderListArray));
      spinner.setGravity(android.view.Gravity.TOP | android.view.Gravity.LEFT);

      textMessage.setText(getString(R.string.copyNotefor));
      uiDialog.setTextSizeMessage(textMessage);
      uiDialog.getContainer().addView(textMessage, uiDialog.lp);
      uiDialog.getContainer().addView(spinner, uiDialog.lp);

      spinner.setOnItemSelectedListener(
          new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                AdapterView<?> parent,
                View itemSelected,
                int selectedItemPosition,
                long selectedId) {
              getItem = selectedItemPosition;
            }

            public void onNothingSelected(AdapterView<?> parent) {}
          });

      builder.setPositiveButton(
          getString(R.string.save),
          (dialog, which) -> {
            fileCore.transferNotes(nameNotes, folderListArray.get(getItem), folderOutput);
            listNotesfors.remove(pos);
            defaultListAdapter.notifyDataSetChanged();
            Log.d("xxx", String.valueOf(getItem));
          });
    } else {
      dismiss();
      Toast.makeText(getContext(), getString(R.string.error_folders_exists), Toast.LENGTH_LONG)
          .show();
    }
    builder.setView(uiDialog.getContainer());
    return builder.create();
  }
}
