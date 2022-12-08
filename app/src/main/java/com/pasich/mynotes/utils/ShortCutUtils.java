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
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.ui.view.activity.NoteActivity;


/**
 * Тоже нужно переписать чтобы можно было менять название заметки
 * иконку, и другие параметры
 * а также дабл трабл при создании ярлыка на устройствах Xiaomi спрашивает разрешение нужно отследить
 */


//имья может содержать 25 символов


@Deprecated
public class ShortCutUtils {

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


            ShortcutInfo.Builder pinShortcutBuilder = new ShortcutInfo.Builder(
                    context,
                    Integer.toString(note.getId()))
                    .setShortLabel(titleLabel)
                    .setIntent(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(""),
                            context,
                            NoteActivity.class)
                            .putExtra("NewNote", false)
                            .putExtra("idNote", note.getId())
                            .putExtra("shareText", "")
                            .putExtra("tagNote", ""))
                    .setIcon(Icon.createWithResource(context, R.mipmap.ic_launcher_note));


            ShortcutInfo pinShortcutInfo = pinShortcutBuilder.build();


            /** Здесь исключение кидает **/

            shortcutManager.requestPinShortcut(pinShortcutInfo,
                    PendingIntent.getBroadcast(context, 0,
                            shortcutManager.createShortcutResultIntent(pinShortcutInfo), 0).getIntentSender());
            Toast.makeText(context, R.string.addShortCutSuccessfully, Toast.LENGTH_SHORT).show();
        }
    }
}
