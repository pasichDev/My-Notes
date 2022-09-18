package com.pasich.mynotes.utils.recycler.comparator;

import com.pasich.mynotes.data.trash.TrashNote;

import java.util.Comparator;

public class TrashNoteComparator<T> {

    public final Comparator<TrashNote> COMPARE_BY_DATE = (e1, e2) -> Math.toIntExact(e2.getDate() - e1.getDate());

}
