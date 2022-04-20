package com.pasich.mynotes.Dialogs;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.Adapters.SourceNoteList.SouceListAdapter;
import com.pasich.mynotes.Adapters.SourceNoteList.SourceListContent;
import com.pasich.mynotes.R;

import java.util.ArrayList;
import java.util.Objects;


public class sourcesNoteList extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LinearLayout container = new LinearLayout(getContext());
        container.setOrientation(LinearLayout.VERTICAL);
        ListView listView = new ListView(getContext());


        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_head_bar, null);
        TextView headText = convertView.findViewById(R.id.textViewHead);
        headText.setText(getString(R.string.investments));
        container.addView(convertView);
        container.addView(listView);

        builder.setView(container);
        ArrayList<SourceListContent> ListSoc = new ArrayList<>();

        ListSoc.add(new SourceListContent("hideb.com","Url"));
        ListSoc.add(new SourceListContent("+380505304185","Tel"));
        ListSoc.add(new SourceListContent("asdas@sdas.sad","Mail"));


        SouceListAdapter souceListAdapter = new SouceListAdapter(getContext(), R.layout.list_source_note, ListSoc);
        listView.setAdapter(souceListAdapter);

        listView.setOnItemClickListener((parent, v, position, id) -> {
            SourceListContent listItem = ListSoc.get(position);
            String selectedItem = listItem.getSource();

            ClipboardManager clipboard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("simple text", selectedItem);
            clipboard.setPrimaryClip(clip);

            Toast.makeText(getContext(),getString(R.string.copyX)+ " " + selectedItem,Toast.LENGTH_SHORT).show();
            Objects.requireNonNull(getDialog()).dismiss();
        });
        return  builder.create();

    }

}
