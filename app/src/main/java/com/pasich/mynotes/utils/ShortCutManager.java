package com.pasich.mynotes.utils;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.ui.view.activity.NoteActivity;

public class ShortCutManager {

    @SuppressLint({"NewApi", "UnspecifiedImmutableFlag"})
    public static void createShortCut(Note note, Context context) {
        ShortcutManager shortcutManager = ContextCompat.getSystemService(
                context,
                ShortcutManager.class
        );

        assert shortcutManager != null;
        if (shortcutManager.isRequestPinShortcutSupported()) {

            String titleLabel = note.getTitle().trim().length() >= 2 ?
                    note.getTitle() :
                    note.getValue().length() >= 10 ? note.getValue().substring(0, 10) : context.getString(R.string.notes);

            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(""),
                    context,
                    NoteActivity.class)
                    .putExtra("NewNote", false)
                    .putExtra("idNote", note.getId())
                    .putExtra("tagNote", "");

            ShortcutInfo.Builder pinShortcutBuilder = new ShortcutInfo.Builder(
                    context,
                    Integer.toString(note.getId()))
                    .setShortLabel(titleLabel)
                    .setIntent(intent)
                    .setIcon(Icon.createWithResource(context, R.mipmap.ic_launcher_notes_shortcut));


            ShortcutInfo pinShortcutInfo = pinShortcutBuilder.build();

            shortcutManager.requestPinShortcut(pinShortcutInfo,
                    PendingIntent.getBroadcast(context, 0,
                            shortcutManager.createShortcutResultIntent(pinShortcutInfo), 0).getIntentSender());
            Toast.makeText(context, R.string.addShortCutSuccessfully, Toast.LENGTH_SHORT).show();
        }
    }
}
