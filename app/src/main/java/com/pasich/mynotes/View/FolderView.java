package com.pasich.mynotes.View;

import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.pasich.mynotes.R;

public class FolderView {

    protected final View view;
    public final Toolbar toolbar;

    public FolderView(View rootView){
        this.view = rootView;
        this.toolbar = view.findViewById(R.id.toolbar_actionbar);

        setToolbar();
    }

    private void setToolbar(){
        toolbar.setTitle(R.string.folders);

    }

}
