package com.pasich.mynotes.view.DialogView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.pasich.mynotes.R;
import com.pasich.mynotes.view.CustomView.TitleDialog;

public class ChoiceTagDialogView extends TitleDialog {

  public final ListView listView;
  private final Context context;
  private final LinearLayout linearLayout;
  private final LayoutInflater Inflater;
  private final View SwitchVisibilityView;
  private final SwitchCompat switchVisibility;

  public ChoiceTagDialogView(Context requireContext, LayoutInflater Inflater) {
    super(requireContext, Inflater);
    this.context = requireContext;
    this.Inflater = Inflater;
    this.linearLayout = new LinearLayout(requireContext);
    this.listView = new ListView(requireContext);
    this.SwitchVisibilityView = initializeSwitchVisibilityNotesToTag();
    this.switchVisibility = SwitchVisibilityView.findViewById(R.id.switchVisibilityTag);

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
    linearLayout.addView(SwitchVisibilityView);
    linearLayout.addView(listView);
  }

  public LinearLayout getLinearLayout() {
    return this.linearLayout;
  }

  @SuppressLint("InflateParams")
  private View initializeSwitchVisibilityNotesToTag() {
    return Inflater.inflate(R.layout.view_visibility_tag, null);
  }

  @SuppressLint("InflateParams")
  public void initializeInfoLayout(String countNotesToTag) {
    View layoutInfo = Inflater.inflate(R.layout.view_info_item, null);
    TextView textView = layoutInfo.findViewById(R.id.infoTextView);
    textView.setText(context.getString(R.string.layoutStringInfoTags, countNotesToTag));
    linearLayout.addView(layoutInfo);
  }

  public SwitchCompat getSwitchVisibilityNotes() {
    return this.switchVisibility;
  }
}
