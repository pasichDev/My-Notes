package com.pasich.mynotes.ui.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.ui.contract.ShareContract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * An activity that is a gateway to save a note via the save button
 */

@AndroidEntryPoint
public class ShareActivity extends BaseActivity implements ShareContract.view {

    @Inject
    public ShareContract.presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachView(this);
        mPresenter.viewIsReady();

        final Intent intent = getIntent();

        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            try {
                mPresenter.creteNoteShare(readFile(getIntent().getData()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (intent.getType().equals("text/plain")) {
            mPresenter.creteNoteShare(handleSendText());
        } else {
            Toast.makeText(this, getString(R.string.notSupportedShare), Toast.LENGTH_LONG).show();
        }

        finish();
    }

    /**
     * The method that opens the file from which we want to take the text for sharing
     *
     * @param uri - uri file
     * @return - text files share
     * @throws IOException - errors
     */
    private String readFile(Uri uri) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append('\n');
        }

        return stringBuilder.toString();
    }


    /**
     * Returns the received text from the heap
     *
     * @return - String (TextData share)
     */
    private String handleSendText() {
        String sharedText = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        return sharedText != null ? sharedText : "";
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void openNoteEdition(long idNote) {
        startActivity(new Intent(this, NoteActivity.class)
                .putExtra("NewNote", true)
                .putExtra("idNote", idNote));

    }
}
