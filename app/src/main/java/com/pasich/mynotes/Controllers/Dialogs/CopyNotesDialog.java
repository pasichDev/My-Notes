package com.pasich.mynotes.Controllers.Dialogs;

import static com.pasich.mynotes.Utils.Constants.SystemConstant.folderSystem;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.Adapters.SpinnerNotes.FolderSpinnerAdapter;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.File.FileCore;
import com.pasich.mynotes.Utils.Interface.UpdateListInterface;
import com.pasich.mynotes.View.CustomView.CustomUIDialog;

import org.apache.commons.io.comparator.NameFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CopyNotesDialog extends DialogFragment {
  private final String[] arrayNoteInfo;
  private int getItem;
  UpdateListInterface UpdateListInterface;

  public CopyNotesDialog(String[] arrayKey) {
    this.arrayNoteInfo = arrayKey;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
    FileCore fileCore = new FileCore(getContext());
    File[] listFolders = requireContext().getFilesDir().listFiles();
    List<String> folderListArray = new ArrayList<>();
    CustomUIDialog uiDialog = new CustomUIDialog(getContext(), getLayoutInflater());
    UpdateListInterface = (UpdateListInterface) requireContext();
    TextView textMessage = new TextView(getContext());
    Spinner spinner = new Spinner(getContext());

    assert listFolders != null;
    Arrays.sort(listFolders, NameFileComparator.NAME_COMPARATOR);

    uiDialog.setHeadTextView(getString(R.string.copyNotesTo));

    if (arrayNoteInfo[2].length() >= 1) {
      folderListArray.add(getString(R.string.rootFolder));
    }
    for (File folderSel : listFolders) {
      if (folderSel.isDirectory()
          && !folderSystem[0].equals(folderSel.getName())
          && !folderSystem[1].equals(folderSel.getName())) {
        folderListArray.add(folderSel.getName());
      }
    }

    spinner.setAdapter(new FolderSpinnerAdapter(getContext(), folderListArray));

    textMessage.setText(getString(R.string.copyNotefor));
    uiDialog.setTextSizeMessage(textMessage);
    uiDialog.getContainer().addView(textMessage, uiDialog.lp);
    uiDialog.getContainer().addView(spinner, uiDialog.lp);
    builder.setView(uiDialog.getContainer());
    spinner.setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(
              AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
            getItem = selectedItemPosition;
          }

          public void onNothingSelected(AdapterView<?> parent) {}
        });

    builder.setPositiveButton(
        getString(R.string.save),
        (dialog, which) -> {
          fileCore.transferNotes(arrayNoteInfo[1], folderListArray.get(getItem), arrayNoteInfo[2]);
          UpdateListInterface.RemoveItem(Integer.parseInt(arrayNoteInfo[0]));
        });


    return builder.create();
  }
}
