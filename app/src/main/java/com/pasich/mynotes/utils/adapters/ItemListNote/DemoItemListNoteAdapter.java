package com.pasich.mynotes.utils.adapters.ItemListNote;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.data.model.ItemListNote;
import com.pasich.mynotes.databinding.ItemDemoListNoteBinding;
import com.pasich.mynotes.utils.adapters.ItemListNote.listeners.DemoItemListSetOnClickListener;

import java.util.List;

public class DemoItemListNoteAdapter extends RecyclerView.Adapter<DemoItemListNoteAdapter.ViewHolder> {
    private final List<ItemListNote> items;
    private final DemoItemListSetOnClickListener demoItemListSetOnClickListener;

    public DemoItemListNoteAdapter(List<ItemListNote> list, DemoItemListSetOnClickListener demoItemListSetOnClickListener) {
        this.items = list;
        this.demoItemListSetOnClickListener = demoItemListSetOnClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new DemoItemListNoteAdapter.ViewHolder(ItemDemoListNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        viewHolder.itemView.setOnClickListener(v -> demoItemListSetOnClickListener.click());
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setItemListNote(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ItemDemoListNoteBinding binding;

        public ViewHolder(ItemDemoListNoteBinding bind) {
            super(bind.getRoot());
            binding = bind;
        }

    }
}