package com.pasich.mynotes.ui.view.dialogs.settings.aboutAppDialog;

import android.view.LayoutInflater;

import com.pasich.mynotes.ui.view.customView.dialog.ListDialogView;

public class AboutAppView extends ListDialogView {


    public AboutAppView(LayoutInflater Inflater) {
        super(Inflater);
        addTitle("");
        addView(getItemsView());
    }

}
