package com.pasich.mynotes.controllers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.utils.Constants.SystemConstant;
import com.pasich.mynotes.view.DialogView.EditThemeColorView;

public class EditThemeColorDialog extends DialogFragment {


  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
    EditThemeColorView editThemeColorView = new EditThemeColorView(getContext(), getLayoutInflater());

    builder.setView(editThemeColorView.uiDialog.getContainer());
    builder.setNegativeButton(getString(R.string.cancel), null);
    editThemeColorView.GridView.setOnItemClickListener(gridviewOnItemClickListener);
    return builder.create();
  }

  /** Element click listener on GridView */
  private final GridView.OnItemClickListener gridviewOnItemClickListener =
      (parent, v, position, id) -> {
        if (!PreferenceManager.getDefaultSharedPreferences(requireContext())
            .getString("themeColor", SystemConstant.Settings_Theme)
            .equals(getResources().getStringArray(R.array.themeColor_values)[position])) {
          editThemePreferences(position);

        }
       requireDialog().dismiss();
      };

  /**
   * Method that changes the color variable in the settings
   *
   * @param pos - the element that was clicked
   */
  private void editThemePreferences(int pos) {
    requireContext()
        .getSharedPreferences(getString(R.string.PreferencesFileName), Context.MODE_PRIVATE)
        .edit()
        .putString("themeColor", getResources().getStringArray(R.array.themeColor_values)[pos])
        .apply();
  }
}
