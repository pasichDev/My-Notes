package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.pasich.mynotes.Adapters.GridView.ColorsAdapter;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Constants.SystemConstant;
import com.pasich.mynotes.Utils.Interface.UpdateTheme;
import com.pasich.mynotes.View.DialogView.EditThemeColorView;

public class EditThemeColorDialog extends DialogFragment {

  private UpdateTheme UpdateTheme;

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    UpdateTheme = (UpdateTheme) getContext();
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
    EditThemeColorView editThemeColorView = new EditThemeColorView(getContext(), getLayoutInflater());
    editThemeColorView.GridView.setAdapter(new ColorsAdapter(getContext()));
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
          UpdateTheme.recreateActivity();
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
