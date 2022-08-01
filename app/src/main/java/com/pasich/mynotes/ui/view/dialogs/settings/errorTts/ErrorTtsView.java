package com.pasich.mynotes.ui.view.dialogs.settings.errorTts.aboutAppDialog;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.customView.dialog.ListDialogView;

public class ErrorTtsView extends ListDialogView {


    public ErrorTtsView(LayoutInflater Inflater) {
        super(Inflater);
        addTitle("");
        addErrorText();
    }

    private void addErrorText() {
        TextView textMessage = new TextView(getContextRoot());
        textMessage.setText(getContextRoot().getString(R.string.error_TTS_Message));
        textMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_MESSAGE_SIZE);
        addView(textMessage, LP_DEFAULT);
    }
}
