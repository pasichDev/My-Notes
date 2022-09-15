package com.pasich.mynotes.utils.adapters.genericAdapterNote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.utils.recycler.comparator.NoteComparator;
import com.pasich.mynotes.utils.recycler.comparator.TrashNoteComparator;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtilNote;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtilTrash;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GenericNoteAdapter<T, VM extends ViewDataBinding> extends ListAdapter<T, GenericNoteAdapter.RecyclerViewHolder> {
    private final int layoutId;
    private final GenericAdapterCallback<VM, T> bindingInterface;
    private OnItemClickListener<T> mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    public GenericNoteAdapter(@NonNull DiffUtilNote diffCallback,
                              int layoutId,
                              GenericAdapterCallback<VM, T> bindingInterface) {
        super((DiffUtil.ItemCallback<T>) diffCallback);
        this.layoutId = layoutId;
        this.bindingInterface = bindingInterface;

    }

    public GenericNoteAdapter(@NonNull DiffUtilTrash diffCallback,
                              int layoutId,
                              GenericAdapterCallback<VM, T> bindingInterface) {
        super((DiffUtil.ItemCallback<T>) diffCallback);
        this.layoutId = layoutId;
        this.bindingInterface = bindingInterface;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        VM binding;

        public RecyclerViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }

        public void bindData(T model) {
            bindingInterface.bindData(binding, model);
        }

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder view = new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false));
        if (mOnItemClickListener != null) {
            view.itemView.setOnClickListener(
                    v -> mOnItemClickListener.onClick(view.getAdapterPosition(), getCurrentList().get(view.getAdapterPosition())));

            view.itemView.setOnLongClickListener(
                    v -> {
                        mOnItemClickListener.onLongClick(view.getAdapterPosition(), getCurrentList().get(view.getAdapterPosition()));
                        return false;
                    });
        }
        return view;
    }

    @Override
    public void onBindViewHolder(GenericNoteAdapter.RecyclerViewHolder holder, int position) {
        holder.bindData(getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        getCurrentList();
        return getCurrentList().size();
    }


    public void sortList(String arg) {
        List<T> sortedList = new ArrayList<>(getCurrentList());
        Collections.sort(sortedList, new NoteComparator().getComparator(arg));
        submitList(sortedList);
    }

    public void sortList(List<T> notesList, String arg) {
        Collections.sort(notesList, new NoteComparator().getComparator(arg));
        submitList(notesList);
    }

    public void sortListTrash(List<T> notesList) {
        Collections.sort(notesList, new TrashNoteComparator().COMPARE_BY_DATE);
        submitList(notesList);
    }


}