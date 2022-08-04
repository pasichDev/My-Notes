package com.pasich.mynotes.ui.view.customView;

import static com.pasich.mynotes.utils.constants.TagSettings.MAX_NAME_TAG;

import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ViewNewTagInputBinding;
import com.pasich.mynotes.ui.view.customView.dialog.BaseView;
import com.pasich.mynotes.utils.simplifications.TextValidatorUtils;

public class InputTagView extends BaseView {

  private final ViewNewTagInputBinding binding;

  public InputTagView(LayoutInflater inflater) {
    super(inflater);
    this.binding = ViewNewTagInputBinding.inflate(inflater);
    this.initialization();
  }

  private void initialization() {
    binding.setErrorText(false);
    binding.setEnableButtonSave(false);
    validateNameActivate();
  }

  public final void visibilityInputNewTag() {
    binding.inputNameView.setVisibility(View.VISIBLE);
    binding.inputNameView.setAnimation(AnimationUtils.loadAnimation(getContextRoot(), R.anim.item_add_record_information));
  }

  public void goneInputNewTag() {
    binding.inputNameView.setVisibility(View.GONE);
  }


  public final Button getSaveButton() {
    return binding.saveTag;
  }

  public final EditText getInputTag() {
    return binding.inputNameTag;
  }

  public final View getNewTagView() {
    return binding.getRoot();
  }

  public final String getText() {
    return binding.inputNameTag.getText().toString().substring(0, MAX_NAME_TAG);
  }

  private void validateNameActivate() {
    getInputTag()
            .addTextChangedListener(
                    new TextValidatorUtils(getInputTag()) {
                      @Override
                      public void validate(TextView textView, String text) {
                        validateText(text.trim().length());
                      }
                    });
  }

  private void validateText(int length) {
    if (length >= MAX_NAME_TAG) {
      binding.setErrorText(true);
      binding.setEnableButtonSave(false);
    } else if (length == MAX_NAME_TAG - 1) {
      binding.setErrorText(false);
      binding.setEnableButtonSave(true);
    }
    if (length < 1) binding.setEnableButtonSave(false);
    else if (length < MAX_NAME_TAG - 1) binding.setEnableButtonSave(true);
  }
}
