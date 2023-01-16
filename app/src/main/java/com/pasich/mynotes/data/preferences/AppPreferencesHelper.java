package com.pasich.mynotes.data.preferences;


import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_DEFAULT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_DEFAULT_LAST_BACKUP_TIME;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_TIME;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_SORT_PREF;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_TEXT_SIZE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_TEXT_STYLE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_SORT;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_TEXT_SIZE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_TEXT_STYLE;

import com.pasich.mynotes.utils.constants.PreferencesConfig;
import com.preference.PowerPreference;
import com.preference.Preference;

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
        return PowerPreference.getFileByName("lastBackupCloudInfo");
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
    public String getTypeFaceNoteActivity() {
        return getDefaultPreferences().getString(ARGUMENT_PREFERENCE_TEXT_STYLE, ARGUMENT_DEFAULT_TEXT_STYLE);
    }


}
