package com.pasich.mynotes.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.database.model.Features;

import java.util.ArrayList;

public class FeaturesPageAdapter extends RecyclerView.Adapter<FeaturesPageAdapter.ViewHolder> {


    private final ArrayList<Features> featuresList;
    private final Context context;

    public FeaturesPageAdapter(Context context, ArrayList<Features> list) {
        this.featuresList = list;
        this.context = context;
    }


    @NonNull
    @Override
    public FeaturesPageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feature, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FeaturesPageAdapter.ViewHolder holder, int position) {
        Features features = featuresList.get(position);
        holder.title.setText(features.getTitle());
        holder.info.setText(features.getInfo());
        holder.images.setImageResource(features.getImage());

    }

    @Override
    public int getItemCount() {
        return featuresList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView images;
        TextView title;
        TextView info;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.imageFeature);
            info = itemView.findViewById(R.id.infoFeature);
            title = itemView.findViewById(R.id.titleFeature);
        }
    }
}
