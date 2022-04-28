package com.pasich.mynotes.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.Adapters.SpinnerNotes.FolderSpinnerAdapter;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Сore.File.FileCore;

import org.apache.commons.io.comparator.NameFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class copyNotes extends DialogFragment {

  private final ArrayList listNotesfors;
  public final String nameNotes, folderOutput;
  private final int pos;
  private final DefaultListAdapter defaultListAdapter;
  private int getItem;

  public copyNotes(
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
    Arrays.sort(listFolders, NameFileComparator.NAME_COMPARATOR);

    List<String> folderListArray = new ArrayList<String>();

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
    LinearLayout container = new LinearLayout(getContext());
    container.setOrientation(LinearLayout.VERTICAL);
    LayoutInflater inflater = getLayoutInflater();
    View convertView = (View) inflater.inflate(R.layout.dialog_head_bar, null);
    TextView headText = convertView.findViewById(R.id.textViewHead);
    headText.setText(getString(R.string.copyNotesTo));
    container.addView(convertView);

    if (folderListArray.size() >= 1) {
      LinearLayout.LayoutParams lp =
          new LinearLayout.LayoutParams(
              LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
      lp.setMargins(60, 5, 60, 0);
      TextView textMessage = new TextView(getContext());
      textMessage.setText(getString(R.string.copyNotefor));
      Spinner spinner = new Spinner(getContext());
      spinner.setAdapter(new FolderSpinnerAdapter(getContext(), folderListArray));
      spinner.setLayoutParams(lp);
      spinner.setGravity(android.view.Gravity.TOP | android.view.Gravity.LEFT);
      container.addView(textMessage, lp);
      container.addView(spinner);
      builder.setView(container);

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
      builder.setMessage(getString(R.string.error_folders_exists));
      builder.setPositiveButton("Ok", (dialog, which) -> {});
    }

    return builder.create();
  }
}
