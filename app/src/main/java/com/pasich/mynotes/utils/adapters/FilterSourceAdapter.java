package com.pasich.mynotes.utils.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.data.model.SourceFilterModel;
import com.pasich.mynotes.databinding.ItemFilterTagSourceBinding;

import java.util.List;

public class FilterSourceAdapter extends RecyclerView.Adapter<FilterSourceAdapter.ViewHolder> {

    private final List<SourceFilterModel> list;
    private OnItemClickListener mOnItemClickListener;

    public FilterSourceAdapter(List<SourceFilterModel> list) {
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public int getCheckedPosition() {
        int retCount = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSelected()) {
                retCount = i;
                break;
            }
        }
        return retCount;
    }


    public void chooseItem(int position) {
        int positionSelected = getCheckedPosition();
        list.get(positionSelected).setSelected(false);
        notifyItemChanged(positionSelected, 22);
        list.get(position).setSelected(true);
        notifyItemChanged(position, 22);
    }


    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    public List<SourceFilterModel> getData() {
        return this.list;
    }

    public SourceFilterModel getItem(int i) {
        return list != null ? list.get(i) : null;
    }

    @NonNull
    @Override
    public FilterSourceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder view =
                new FilterSourceAdapter.ViewHolder(
                        ItemFilterTagSourceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        if (mOnItemClickListener != null) {
            view.itemView.setOnClickListener(
                    v -> mOnItemClickListener.onClick(view.getAdapterPosition()));

            view.itemView.setOnLongClickListener(
                    v -> {
                        mOnItemClickListener.onLongClick(view.getAdapterPosition());
                        return false;
                    });
        }

        return view;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ItemBinding.setObjectModel(list.get(position));
    }

    @Override
    public void onBindViewHolder(
            @NonNull FilterSourceAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            for (Object payload : payloads) {
                if (payload.equals(22)) {
                    holder.ItemBinding.setObjectModel(list.get(position));
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemFilterTagSourceBinding ItemBinding;

        ViewHolder(ItemFilterTagSourceBinding binding) {
            super(binding.getRoot());
            ItemBinding = binding;
        }
    }
}
