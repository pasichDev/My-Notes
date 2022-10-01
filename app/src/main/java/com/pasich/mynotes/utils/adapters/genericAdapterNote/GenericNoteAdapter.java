package com.pasich.mynotes.utils.adapters.genericAdapterNote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.utils.recycler.comparator.NoteComparator;
import com.pasich.mynotes.utils.recycler.comparator.TrashNoteComparator;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtilNote;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtilTrash;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GenericNoteAdapter<T, VM extends ViewDataBinding> extends RecyclerView.Adapter<T, GenericNoteAdapter.RecyclerViewHolder> {
    private final int layoutId;
    private final GenericAdapterCallback<VM, T> bindingInterface;
    private OnItemClickListener<T> mOnItemClickListener;

    private DiffUtilNote diffUtilNote = new DiffUtilNote();
    private final AsyncListDiffer<T> mDiffer = new AsyncListDiffer(this, diffUtilNote);


    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    public GenericNoteAdapter(int layoutId,
                              GenericAdapterCallback<VM, T> bindingInterface) {

        this.layoutId = layoutId;
        this.bindingInterface = bindingInterface;

    }

    public GenericNoteAdapter(@NonNull DiffUtilTrash diffCallback,
                              int layoutId,
                              GenericAdapterCallback<VM, T> bindingInterface) {

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
                    v -> mOnItemClickListener.onClick(view.getAdapterPosition(), mDiffer.getCurrentList().get(view.getAdapterPosition())));

            view.itemView.setOnLongClickListener(
                    v -> {
                        mOnItemClickListener.onLongClick(view.getAdapterPosition(), mDiffer.getCurrentList().get(view.getAdapterPosition()));
                        return false;
                    });
        }
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull T holder, int position) {
        GenericNoteAdapter.RecyclerViewHolder mHolder = (RecyclerViewHolder) holder;
        mHolder.bindData(mDiffer.getCurrentList().get(position));
    }


    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }


    public void sortList(String arg) {
        List<T> sortedList = new ArrayList<T>(mDiffer.getCurrentList());
        Collections.sort(sortedList, new NoteComparator().getComparator(arg));
        mDiffer.submitList(sortedList);
    }

    public void sortList(List<T> notesList, String arg) {
        Collections.sort(notesList, new NoteComparator().getComparator(arg));
        mDiffer.submitList(notesList);
    }

    public void sortListTrash(List<T> notesList) {
        Collections.sort(notesList, new TrashNoteComparator().COMPARE_BY_DATE);
        mDiffer.submitList(notesList);
    }

}