package com.pasich.mynotes.utils.adapters;


import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.utils.adapters.baseGenericAdapter.GenericAdapter;
import com.pasich.mynotes.utils.adapters.baseGenericAdapter.GenericAdapterCallback;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtilNote;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

public class NoteAdapter<VM extends ViewDataBinding> extends GenericAdapter<Note, VM> {


    @Inject
    public NoteAdapter(@NonNull DiffUtilNote diffCallback, int layoutId, GenericAdapterCallback<VM, Note> bindingInterface) {
        super(diffCallback, layoutId, bindingInterface);

    }


    public void sortList(String arg) {
        Collections.sort(getCurrentList(), new NoteComparator().getComparator(arg));
        submitList(getCurrentList());
    }

    public void sortList(List<Note> notesList, String arg) {
        Collections.sort(notesList, new NoteComparator().getComparator(arg));
        submitList(notesList);

    }


    public static class NoteComparator {
        private final Comparator<Note> COMPARE_BY_TITLE_REVERSE = (e1, e2) -> e2.getTitle().toLowerCase().compareTo(e1.getTitle().toLowerCase());
        private final Comparator<Note> COMPARE_BY_TITLE_SORT = (e1, e2) -> e1.getTitle().toLowerCase().compareTo(e2.getTitle().toLowerCase());

        private final Comparator<Note> COMPARE_BY_DATE_REVERSE = (e1, e2) -> Math.toIntExact((long) (e1.getDate() - e2.getDate()));
        private final Comparator<Note> COMPARE_BY_DATE_SORT = (e1, e2) -> Math.toIntExact((long) (e2.getDate() - e1.getDate()));


        public Comparator<Note> getComparator(String arg) {
            switch (arg) {
                case "DataSort":
                    return COMPARE_BY_DATE_SORT;
                case "TitleSort":
                    return COMPARE_BY_TITLE_SORT;
                case "TitleReserve":
                    return COMPARE_BY_TITLE_REVERSE;
                default:
                    return COMPARE_BY_DATE_REVERSE;
            }
        }
    }

}
