package com.pasich.mynotes.Dialogs;

import static com.pasich.mynotes.Сore.Methods.shareNotesMethod.shareNotes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.Adapters.ListNotes.ListNotesfor;
import com.pasich.mynotes.R;
import com.pasich.mynotes.lib.CustomUIDialog;
import com.pasich.mynotes.Сore.File.FileCore;

import java.io.File;
import java.util.ArrayList;

public class TestDialog extends DialogFragment {

    private FileCore fileCore;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        CustomUIDialog uiDialog = new CustomUIDialog(getContext(),
                getLayoutInflater());
        uiDialog.setHeadTextView(getString(R.string.warning));
        builder.setView(uiDialog.getContainer());



        //    uiDialog.getContainer().addView(uiDialog.getConvertView());




     /*   uiDialog.getCloseButton().setOnClickListener(v -> {
            getDialog().dismiss();
        });

*/

      /*  TextView textMessage = new TextView(getContext());
        textMessage.setText(getString(R.string.cleanTrashquestion));
        textMessage.setTextSize(18);
        uiDialog.getContainer().addView(textMessage);
*/
      //  LayoutInflater inflater = getLayoutInflater();
      //  @SuppressLint("InflateParams") View convertView = (View) inflater.inflate(R.layout.dialog_head_bar, null);


       // TextView headText = convertView.findViewById(R.id.textViewHead);

    //    container.setOrientation(LinearLayout.VERTICAL);



        return builder.create();

    }




}
