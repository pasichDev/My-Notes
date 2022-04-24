package com.pasich.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


/**
 * An activity that is a gateway to save a note via the save button
 */
public class ShareActivity extends AppCompatActivity {


    private Intent shareIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.shareIntent = getIntent();

        if (shareIntent.getType().equals("text/plain")) {
            Intent intent = new Intent(getApplication(), NoteActivity.class)
            .putExtra("KeyFunction", "NewNote")
            .putExtra("idNote",  "null")
            .putExtra("shareText",   handleSendText())
            .putExtra("folder", "");
            startActivityForResult(intent, 99);
        }else{
            Toast.makeText(getApplicationContext(), getString(R.string.notSupportedShare), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }


    /**
     * Returns the received text from the heap
     * @return - String (TextData share)
     */
    private String handleSendText() {
        String sharedText = shareIntent.getStringExtra(Intent.EXTRA_TEXT);
        return  sharedText != null ? sharedText : "";
    }
}
