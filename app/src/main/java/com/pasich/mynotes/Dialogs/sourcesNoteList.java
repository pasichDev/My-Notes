package com.pasich.mynotes.Dialogs;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.Adapters.ListNotes.ListNotesfor;
import com.pasich.mynotes.Adapters.SourceNoteList.SouceListAdapter;
import com.pasich.mynotes.Adapters.SourceNoteList.SourceListContent;
import com.pasich.mynotes.R;

import java.util.ArrayList;


public class sourcesNoteList extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_source_note, null);
        builder.setView(convertView);
        ListView listSources = convertView.findViewById(R.id.listSources);
        ArrayList<SourceListContent> ListSoc = new ArrayList<>();

        ListSoc.add(new SourceListContent("hideb.com","Url"));
        ListSoc.add(new SourceListContent("+380505304185","Tel"));
        ListSoc.add(new SourceListContent("asdas@sdas.sad","Mail"));


        SouceListAdapter souceListAdapter = new SouceListAdapter(getContext(), R.layout.list_source_note, ListSoc);
        listSources.setAdapter(souceListAdapter);

        listSources.setOnItemClickListener((parent, v, position, id) -> {
            SourceListContent listItem = ListSoc.get(position);
            String selectedItem = listItem.getSource();

            //
            switch (listItem.getType()){
                case "Url":
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(selectedItem)));
                    break;
                case "Tel":
                    startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse(selectedItem)));
                    break;
                case "Mail":
                    Intent intentEmail = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
                    intentEmail.putExtra(Intent.EXTRA_EMAIL, selectedItem);
                    intentEmail.putExtra(Intent.EXTRA_SUBJECT, "");
                    intentEmail.putExtra(Intent.EXTRA_TEXT, "");
                    intentEmail.setType("text/plain");
                    startActivity(Intent.createChooser(intentEmail, "Choose an email client from..."));
                    break;

            }


        });

        listSources.setOnItemLongClickListener((arg0, arg1, position, id) -> {
            SourceListContent listItem = ListSoc.get(position);
            String selectedItem = listItem.getSource();

            ClipboardManager clipboard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("simple text", selectedItem);
            clipboard.setPrimaryClip(clip);

            //Обязательно добавить в перевод
            Toast.makeText(getContext(),"Скопировано",Toast.LENGTH_LONG).show();
            getDialog().dismiss();
            return true;
        });

        return  builder.create();

    }

}
