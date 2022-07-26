package com.pasich.mynotes.ui.view.dialogs.main.TagDialog;

import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.customView.InputTagView;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

public class TagDialogView extends InputTagView {

    public final RecyclerView listTags;

    public TagDialogView(LayoutInflater inflater) {
        super(inflater);
        this.listTags = new RecyclerView(getContextRoot());
        initialization();

    }

    private void initialization() {
        addTitle("");
        LinearLayout linearLayoutRecycle = new LinearLayout(getContextRoot());
        linearLayoutRecycle.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutRecycle.addView(createRemoveTagButton());
        linearLayoutRecycle.addView(createRecycleView());
        addView(linearLayoutRecycle, getLp());
        addView(getNewTagView());
    }

    private RecyclerView createRecycleView() {
        listTags.addItemDecoration(new SpacesItemDecoration(5));
        listTags.setLayoutManager(
                new LinearLayoutManager(getContextRoot(), RecyclerView.HORIZONTAL, false));
        return listTags;
    }


    private ImageButton createRemoveTagButton() {
        ImageButton imageCLeanTag = new ImageButton(getContextRoot());
        imageCLeanTag.setImageResource(R.drawable.ic_close_search_view);
        imageCLeanTag.setId(R.id.removeTagForDialog);
        imageCLeanTag.setScaleType(ImageView.ScaleType.CENTER);
        imageCLeanTag.setBackground(null);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                (ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 0);

        imageCLeanTag.setLayoutParams(layoutParams);

        return imageCLeanTag;
    }

    private LinearLayout.LayoutParams getLp() {
        LinearLayout.LayoutParams LP = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LP.setMargins(30, 0, 10, 0);
        return LP;
    }
}
