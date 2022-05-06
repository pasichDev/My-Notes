package com.pasich.mynotes.Dialogs;

import static com.pasich.mynotes.Utils.Constants.SystemConstant.settingsFileName;
import static com.pasich.mynotes.Utils.Constants.BackConstant.UPDATE_THEME;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.pasich.mynotes.Adapters.GridView.ImageAdapter;
import com.pasich.mynotes.R;
import com.pasich.mynotes.View.CustomView.CustomUIDialog;
import com.pasich.mynotes.Utils.Constants.SystemConstant;

import java.util.Objects;

public class EditThemeColorDialog extends DialogFragment {
  /** An interface that updates the activity after changing the theme */
  public interface updateTheme {
    void updateThemeCheck();
  }

  private final Context context;

  public EditThemeColorDialog(Context context) {
    this.context = context;
  }

  private updateTheme listen;

  @SuppressLint("RtlHardcoded")
  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    listen = (updateTheme) getTargetFragment();
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    CustomUIDialog uiDialog = new CustomUIDialog(getContext(), getLayoutInflater());
    GridView gridview = new GridView(getContext());
    gridview.setNumColumns(6);
    gridview.setHorizontalSpacing(10);
    gridview.setAdapter(new ImageAdapter(getContext()));
    gridview.setGravity(android.view.Gravity.TOP | android.view.Gravity.LEFT);
    uiDialog.setHeadTextView(getString(R.string.selectColorPrimaryApp));
    uiDialog.getContainer().addView(gridview, uiDialog.lp);
    builder.setView(uiDialog.getContainer());
    builder.setNegativeButton(getString(R.string.cancel), null);
    gridview.setOnItemClickListener(gridviewOnItemClickListener);
    return builder.create();
  }

  /** Element click listener on GridView */
  private final GridView.OnItemClickListener gridviewOnItemClickListener =
      (parent, v, position, id) -> {
        if (!PreferenceManager.getDefaultSharedPreferences(getContext())
            .getString("themeColor", SystemConstant.Settings_Theme)
            .equals(getResources().getStringArray(R.array.themeColor_values)[position])) {
          editThemePrefences(position);
          UPDATE_THEME = true;
          listen.updateThemeCheck();
        }
        Objects.requireNonNull(getDialog()).dismiss();
      };

  /**
   * Method that changes the color variable in the settings
   *
   * @param pos - the element that was clicked
   */
  private void editThemePrefences(int pos) {
    context
        .getSharedPreferences(settingsFileName, Context.MODE_PRIVATE)
        .edit()
        .putString("themeColor", getResources().getStringArray(R.array.themeColor_values)[pos])
        .apply();
  }
}
