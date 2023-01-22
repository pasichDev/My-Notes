package com.pasich.mynotes.data.preferences;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_AUTO_BACKUP_CLOUD;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_AUTO_BACKUP_CLOUD_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_DEFAULT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_DEFAULT_LAST_BACKUP_TIME;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_TIME;
import static com.pasich.mynotes.utils.constants.Backup_Constants.FIlE_NAME_PREFERENCE_BACKUP;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_SORT_PREF;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_TEXT_SIZE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_TEXT_STYLE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_SORT;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_TEXT_SIZE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_TEXT_STYLE;

import android.util.Log;

import com.pasich.mynotes.data.model.backup.Preferences;
import com.pasich.mynotes.utils.constants.PreferencesConfig;
import com.preference.PowerPreference;
import com.preference.Preference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class AppPreferencesHelper implements PreferenceHelper {

    @Inject
    AppPreferencesHelper() {
    }

    @Override
    public Preference getDefaultPreferences() {
        return PowerPreference.getDefaultFile();
    }

    @Override
    public Preference getBackupCloudInfoPreference() {
        return PowerPreference.getFileByName(FIlE_NAME_PREFERENCE_BACKUP);
    }

    @Override
    public int getFormatCount() {
        return getDefaultPreferences().getInt(PreferencesConfig.ARGUMENT_PREFERENCE_FORMAT, PreferencesConfig.ARGUMENT_DEFAULT_FORMAT_VALUE);
    }

    @Override
    public int getSizeTextNoteActivity() {
        return getDefaultPreferences().getInt(ARGUMENT_PREFERENCE_TEXT_SIZE, ARGUMENT_DEFAULT_TEXT_SIZE);
    }

    @Override
    public String getSortParam() {
        return getDefaultPreferences().getString(ARGUMENT_PREFERENCE_SORT, ARGUMENT_DEFAULT_SORT_PREF);
    }

    @Override
    public long getLastDataBackupCloud() {
        return getBackupCloudInfoPreference().getLong(ARGUMENT_LAST_BACKUP_TIME, ARGUMENT_DEFAULT_LAST_BACKUP_TIME);
    }

    @Override
    public String getLastBackupCloudId() {
        return getBackupCloudInfoPreference().getString(ARGUMENT_LAST_BACKUP_ID, ARGUMENT_DEFAULT_LAST_BACKUP_ID);
    }

    @Override
    public void editSizeTextNoteActivity(int value) {
        getDefaultPreferences().setInt(ARGUMENT_PREFERENCE_TEXT_SIZE, value);

    }

    @Override
    public int getSetCloudAuthBackup() {
        return getBackupCloudInfoPreference().getInt(ARGUMENT_AUTO_BACKUP_CLOUD, ARGUMENT_AUTO_BACKUP_CLOUD_ID);
    }

    @Override
    public List<Preferences> getListPreferences() {
        List<Preferences> preferences = new ArrayList<>();
        preferences.add(new Preferences<>(PreferencesConfig.ARGUMENT_PREFERENCE_FORMAT, getFormatCount()));
        preferences.add(new Preferences<>(PreferencesConfig.ARGUMENT_PREFERENCE_TEXT_STYLE, getTypeFaceNoteActivity()));
        preferences.add(new Preferences<>(PreferencesConfig.ARGUMENT_PREFERENCE_SORT, getSortParam()));
        preferences.add(new Preferences<>(PreferencesConfig.ARGUMENT_PREFERENCE_TEXT_SIZE, getSizeTextNoteActivity()));
        preferences.add(new Preferences<>(PreferencesConfig.ARGUMENT_PREFERENCE_THEME, PowerPreference.getDefaultFile().getInt(PreferencesConfig.ARGUMENT_PREFERENCE_THEME, PreferencesConfig.ARGUMENT_DEFAULT_THEME_VALUE)));
        preferences.add(new Preferences<>(PreferencesConfig.ARGUMENT_PREFERENCE_DYNAMIC_COLOR, PowerPreference.getDefaultFile().getBoolean(PreferencesConfig.ARGUMENT_PREFERENCE_DYNAMIC_COLOR, PreferencesConfig.ARGUMENT_DEFAULT_DYNAMIC_COLOR_VALUE)));
        return preferences;
    }

    @Override
    public void setListPreferences(List<Preferences> preferences) {
        for (Preferences preference : preferences) {
            Log.wtf(TAG, "setListPreferences: " + preference.getKey());
            getDefaultPreferences().putObject(preference.getKey(), preference.getValue());
        }
     /*   getDefaultPreferences()
                .put(PreferencesConfig.ARGUMENT_PREFERENCE_FORMAT, preferences.get(0).getValue())
                .putString(ARGUMENT_PREFERENCE_TEXT_STYLE, ARGUMENT_DEFAULT_TEXT_STYLE)
                .putString(ARGUMENT_PREFERENCE_SORT, ARGUMENT_DEFAULT_SORT_PREF)
                .putInt(ARGUMENT_PREFERENCE_TEXT_SIZE, ARGUMENT_DEFAULT_TEXT_SIZE)
                .putInt(PreferencesConfig.ARGUMENT_PREFERENCE_THEME, PreferencesConfig.ARGUMENT_DEFAULT_THEME_VALUE)
                .putBoolean(PreferencesConfig.ARGUMENT_PREFERENCE_DYNAMIC_COLOR, PreferencesConfig.ARGUMENT_DEFAULT_DYNAMIC_COLOR_VALUE);

      */
    }

    @Override
    public String getTypeFaceNoteActivity() {
        return getDefaultPreferences().getString(ARGUMENT_PREFERENCE_TEXT_STYLE, ARGUMENT_DEFAULT_TEXT_STYLE);
    }


}
