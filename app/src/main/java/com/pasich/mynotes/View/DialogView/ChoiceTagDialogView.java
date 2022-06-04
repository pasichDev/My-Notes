package com.pasich.mynotes.View.DialogView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.View.CustomView.CustomHeadUIDialog;

public class ChoiceTagDialogView extends CustomHeadUIDialog {

  public final ListView listView;
  private final Context context;
  private final LinearLayout linearLayout;
  private final LayoutInflater Inflater;

  public ChoiceTagDialogView(Context requireContext, LayoutInflater Inflater) {
    super(requireContext, Inflater);
    this.context = requireContext;
    this.Inflater = Inflater;
    this.linearLayout = new LinearLayout(requireContext);
    this.listView = new ListView(requireContext);

    initializeListView();
    initializeLinearLayout();
  }

  private void initializeListView() {

    listView.setLayoutAnimation(
        new LayoutAnimationController(
            AnimationUtils.loadAnimation(context, R.anim.item_animation_dialog)));
    listView.setDivider(null);
  }

  private void initializeLinearLayout() {
    linearLayout.setOrientation(LinearLayout.VERTICAL);
    linearLayout.addView(getContainer());
    linearLayout.addView(listView);
  }

  public LinearLayout getLinearLayout() {
    return this.linearLayout;
  }

  @SuppressLint("InflateParams")
  public void initializeInfoLayout(String countNotesToTag) {
    View layoutInfo = Inflater.inflate(R.layout.view_info_item, null);
    TextView textView = layoutInfo.findViewById(R.id.infoTextView);
    textView.setText(context.getString(R.string.layoutStringInfoTags, countNotesToTag));
    linearLayout.addView(layoutInfo);
  }
}
