package com.pasich.mynotes.Dialogs;

import static com.pasich.mynotes.Сore.Methods.MethodCheckEmptyTrash.checkCountListTrashActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Сore.File.FileCore;

public class CleanTrash extends DialogFragment {
    private  final DefaultListAdapter defaultListAdapter;

    public CleanTrash(DefaultListAdapter defaultListAdapter)
    {
        this.defaultListAdapter=defaultListAdapter;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FileCore fileCore = new FileCore(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(100, 60, 40, 40);
        TextView textMessage = new TextView(getContext());
        textMessage.setText(getString(R.string.cleanTrashquestion));
        textMessage.setTextSize(18);
        LinearLayout container = new LinearLayout(getContext());
        container.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_head_bar, null);
        TextView headText = convertView.findViewById(R.id.textViewHead);
        headText.setText(getString(R.string.trashN));
        container.addView(convertView);
        container.addView(textMessage, lp);


        builder.setView(container);
                    builder.setPositiveButton(getString(R.string.yesCleanTrash), (dialog, which) -> {
                        fileCore.deleteAllNotes();
                        defaultListAdapter.clear();
                        defaultListAdapter.notifyDataSetChanged();
                        if(defaultListAdapter.getCount() == 0)
                        checkCountListTrashActivity(getActivity());
                    })
                    .setNegativeButton(getString(R.string.cancel), null);
        return builder.create();
    }

}
