package com.pasich.mynotes.utils.adapters.labelAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.database.model.Label;

import java.util.ArrayList;

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.ViewHolder> {

    private final ArrayList<Label> labels;
    private final Context context;

    public LabelAdapter(Context context, ArrayList<Label> list) {
        this.labels = list;
        this.context = context;
    }


    @NonNull
    @Override
    public LabelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_label, parent, false);
        return new LabelAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull LabelAdapter.ViewHolder holder, int position) {
        Label label = labels.get(position);
        holder.images.setImageResource(label.getImage());
        if (label.isCheck()) {
            holder.item_label.setBackground(AppCompatResources.getDrawable(context, R.drawable.item_label_check));
        } else {
            holder.item_label.setBackground(AppCompatResources.getDrawable(context, R.drawable.item_label_uncheck));
        }
    }

    @Override
    public int getItemCount() {
        return labels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView images;
        View item_label;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.imageLabel);
            item_label = itemView.findViewById(R.id.itemLabel);
        }
    }
}
