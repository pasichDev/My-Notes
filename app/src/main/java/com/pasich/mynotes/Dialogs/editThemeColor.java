package com.pasich.mynotes.Dialogs;

import static com.pasich.mynotes.Сore.SystemCostant.settingsFileName;
import static com.pasich.mynotes.Сore.backConstant.UPDATE_THEME;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.pasich.mynotes.Adapters.GridView.ImageAdapter;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Сore.SystemCostant;

import java.util.Objects;

public class editThemeColor extends DialogFragment {
    /**
     * Интерфейс который обновляет активность после смены темы
     */
    public interface updateTheme{
        void updateThemeCheck();
    }

    private final Context context;
    public editThemeColor(Context context){
        this.context = context;
    }
    private updateTheme listen;


    @SuppressLint("RtlHardcoded")
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        listen = (updateTheme) getTargetFragment();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout container = new LinearLayout(getContext());
        container.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(40, 20, 40, 20);
        GridView gridview = new GridView(getContext());
        gridview.setNumColumns(6);
        gridview.setHorizontalSpacing(10);
        gridview.setAdapter(new ImageAdapter(getContext()));
        gridview.setLayoutParams(lp);
        gridview.setGravity(android.view.Gravity.TOP | android.view.Gravity.LEFT);

        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_head_bar, null);
        TextView headText = convertView.findViewById(R.id.textViewHead);
        headText.setText(getString(R.string.selectColorPrimaryApp));
        container.addView(convertView);

        container.addView(gridview, lp);
        builder.setView(container);
        builder.setNegativeButton(getString(R.string.cancel),null);
        gridview.setOnItemClickListener(gridviewOnItemClickListener);
        return  builder.create();
     }

    /**
     * Слушатель нажатия на элемент на GridView
     */
    private final GridView.OnItemClickListener gridviewOnItemClickListener = (parent, v, position, id) -> {
        if(!PreferenceManager.getDefaultSharedPreferences(getContext())
                .getString("themeColor", SystemCostant.Settings_Theme)
                .equals(getResources().getStringArray(R.array.themeColor_values)[position])){
        editThemePrefences(position);
        UPDATE_THEME = true;
        listen.updateThemeCheck();}
        Objects.requireNonNull(getDialog()).dismiss();
    };


    /**
     * Метод который изменяет переменую цвета в настройках
     * @param pos - елемен на который было произведенно нажатие
     */
    private void editThemePrefences(int pos){
        SharedPreferences.Editor editor = context.getSharedPreferences(settingsFileName, Context.MODE_PRIVATE).edit()
                .putString("themeColor", getResources().getStringArray(R.array.themeColor_values)[pos]);
        editor.apply();
    }
}
