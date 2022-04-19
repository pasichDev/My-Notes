package com.pasich.mynotes.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Сore.File.FileCore;

public class folderOption extends DialogFragment {
    private final  String editName;
    public folderOption(String editName) {
        this.editName = editName;
    }


    public interface EditNameDialogListener {
        void onFinishfolderOption(boolean updateList);
    }

    @SuppressLint("RtlHardcoded")
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FileCore fileCore = new FileCore(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        EditNameDialogListener listener = (EditNameDialogListener) getTargetFragment();

      //  builder.setMessage(getString(R.string.inputNameFolder));
        LinearLayout container = new LinearLayout(getContext());
        container.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(60, 0, 60, 0);


        final EditText input = new EditText(getContext());


        input.setLayoutParams(lp);
        input.setGravity(android.view.Gravity.TOP|android.view.Gravity.LEFT);
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setLines(1);
        input.setHint(getString(R.string.inputNameFolder));
        input.setMaxLines(1);

        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_head_bar, null);
        TextView headText = convertView.findViewById(R.id.textViewHead);
        headText.setText(getString(R.string.newFolder));
        container.addView(convertView);
        container.addView(input, lp);
        builder.setView(container);

        if(editName.length()>=1) input.setText(editName);
        input.setEnabled(true);
        input.setFocusable(true);
        input.setSelection(input.getText().length());
        input.setFocusableInTouchMode(true);
        input.requestFocus();
        input.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(25) });


        if(editName.trim().length()>=1){//
        builder.setPositiveButton(getString(R.string.save), (dialog, which) -> {
            if(!input.getText().toString().equals(editName)){
                fileCore.saveNameFolder(input.getText().toString(),true,editName);
                listener.onFinishfolderOption(true);
               } }); }
        else if(editName.trim().length()==0 || editName.equals("")){
                        builder.setPositiveButton(getString(R.string.createFolder), (dialog, which) -> {
                            if(input.getText().toString().length()>=1){
                                fileCore.saveNameFolder(input.getText().toString(),false,"");
                                listener.onFinishfolderOption(true);
                            }
                        }); }

       InputMethodManager inputMgr = (InputMethodManager)builder.getContext().
                getSystemService(getContext().INPUT_METHOD_SERVICE);
        inputMgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);


       return builder.create();


    }



    @Override
    public void onDismiss (@NonNull DialogInterface dialog){
        super.onDismiss(dialog);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
}




}
