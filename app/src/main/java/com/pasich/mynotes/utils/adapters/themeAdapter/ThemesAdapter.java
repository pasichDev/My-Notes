package com.pasich.mynotes.utils.adapters.themeAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.database.model.Theme;

import java.util.ArrayList;
import java.util.List;

public class ThemesAdapter extends RecyclerView.Adapter<ThemesAdapter.ViewHolder> {

    private final ArrayList<Theme> themes;
    private final Context context;
    private final Theme Theme_DEFAULT = new Theme(R.drawable.theme_default, 0);
    private final int PAYLOAD_SET_SELECTED = 44;
    private SelectThemesListener selectThemesListener;
    private Theme mSelectTheme;
    private boolean oneCheckedAll = false;


    public ThemesAdapter(Context context, ArrayList<Theme> list) {
        this.themes = list;
        this.context = context;
    }


    public void setSelectLabelListener(SelectThemesListener selectLabelListener) {
        this.selectThemesListener = selectLabelListener;
    }


    public Theme getSelectTheme() {
        return this.mSelectTheme == null ? Theme_DEFAULT : this.mSelectTheme;
    }


    public void setThemeSelected(@Nullable Theme labelSelect) {
        this.mSelectTheme = labelSelect;
    }

    public void selectTheme(int position) {
        notifyItemChanged(getCheckedPosition(getSelectTheme().setCheckReturn(false)), PAYLOAD_SET_SELECTED);
        setThemeSelected(themes.get(position).setCheckReturn(true));
        notifyItemChanged(position, PAYLOAD_SET_SELECTED);
    }

    public int getCheckedPosition(Theme theme) {
        for (int i = 0; i < themes.size(); i++)
            if (themes.get(i).getId() == theme.getId()) return i;
        return 0;
    }

    @NonNull
    @Override
    public ThemesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder view = new ThemesAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_theme, parent, false));
        if (selectThemesListener != null) {
            view.itemView.setOnClickListener(v -> selectThemesListener.onSelect(view.getAdapterPosition()));

        }
        return view;
    }


    @Override
    public void onBindViewHolder(@NonNull ThemesAdapter.ViewHolder holder, int position) {
        Theme theme = themes.get(position);
        if (!oneCheckedAll && theme.getId() == 0) {
            mSelectTheme = theme.setCheckReturn(true);
            oneCheckedAll = true;
        }

        holder.images.setImageDrawable(AppCompatResources.getDrawable(context, theme.getImage()));
        setCheckView(holder, theme);
    }


    @Override
    public void onBindViewHolder(@NonNull ThemesAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            if (payloads.contains(PAYLOAD_SET_SELECTED)) setCheckView(holder, themes.get(position));
        }
    }


    private void setCheckView(ThemesAdapter.ViewHolder holder, Theme theme) {
        if (theme.isCheck()) {
            holder.item_theme.setBackground(AppCompatResources.getDrawable(context, R.drawable.item_theme_check));
        } else {
            holder.item_theme.setBackground(AppCompatResources.getDrawable(context, R.drawable.item_theme_uncheck));
        }
    }


    @Override
    public int getItemCount() {
        return themes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView images;
        View item_theme;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.imageTheme);
            item_theme = itemView.findViewById(R.id.itemTheme);
        }
    }
}
