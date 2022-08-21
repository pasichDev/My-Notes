package com.pasich.mynotes.utils;

import static com.pasich.mynotes.utils.constants.TagSettings.MAX_NAME_TAG;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.pasich.mynotes.R;


public class ValidateNameTag {
    private final EditText mEditText;
    private final Button mButtonSave;

    public ValidateNameTag(EditText editText, Button buttonSave) {
        this.mEditText = editText;
        this.mButtonSave = buttonSave;
        this.init();
    }

    private void init() {
        mEditText.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        validateText(s.toString().trim().length());
                    }
                });
    }

    private void validateText(int length) {
        if (length >= MAX_NAME_TAG) {
            mEditText.setBackgroundResource(R.drawable.background_normal_error);
            mButtonSave.setEnabled(false);
        } else if (length == MAX_NAME_TAG - 1) {
            mEditText.setBackgroundResource(R.drawable.background_normal_input);
            mButtonSave.setEnabled(true);
        }
        if (length < 1) mButtonSave.setEnabled(false);
        else if (length < MAX_NAME_TAG - 1) mButtonSave.setEnabled(true);
    }
}
