package com.pasich.mynotes.ui.view.dialogs.settings.settingsAppDialog.aboutAppDialog;

import android.view.LayoutInflater;

import com.pasich.mynotes.ui.view.customView.dialog.ListDialogView;

public class SettingsAppView extends ListDialogView {


    public SettingsAppView(LayoutInflater Inflater) {
        super(Inflater);
        addTitle("");
        addView(getItemsView());
    }

}
