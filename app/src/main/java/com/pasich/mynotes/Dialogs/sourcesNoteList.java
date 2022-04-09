package com.pasich.mynotes.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.Adapters.SourceNiteList.SouceListAdapter;
import com.pasich.mynotes.Adapters.SourceNiteList.SourceListContent;
import com.pasich.mynotes.R;

import java.util.ArrayList;


public class sourcesNoteList extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());



        View view = getLayoutInflater().inflate(R.layout.source_note_list, null, false);
        ListView listSources = view.findViewById(R.id.listSources);
        ArrayList<SourceListContent> ListSoc = new ArrayList<>();

        ListSoc.add(new SourceListContent("hideb","url"));
        ListSoc.add(new SourceListContent("hideb","url"));

        SouceListAdapter souceListAdapter = new SouceListAdapter(getContext(), R.layout.list_notes, ListSoc);
        listSources.setAdapter(souceListAdapter);
        builder.setView(R.layout.source_note_list);
Log.d("xxxx",ListSoc.toString());


      //


     //   NotesListData = new NotesListData(getContext());
     //   SourceListContent SourceListContent = NotesListData.newListAdapter( "",mode_note);
        return  builder.create();

    }

}
