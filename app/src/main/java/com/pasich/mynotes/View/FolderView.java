package com.pasich.mynotes.View;

import android.view.View;
import android.widget.GridView;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Constants.SystemConstant;

public class FolderView {

    protected final View view;
    public final Toolbar toolbar;
    public GridView foldersList;


    public FolderView(View rootView){
        this.view = rootView;
        this.toolbar = view.findViewById(R.id.toolbar_actionbar);
        this.foldersList = view.findViewById(R.id.ListFileNotes);

        setToolbar();
        setFoldersList();
    }

    private void setToolbar(){
        toolbar.setTitle(R.string.folders);
    }

    private void setFoldersList(){
        foldersList.setNumColumns(PreferenceManager
                .getDefaultSharedPreferences(view.getContext())
                .getInt("formatParam", SystemConstant.Setting_Format));
    }

}
