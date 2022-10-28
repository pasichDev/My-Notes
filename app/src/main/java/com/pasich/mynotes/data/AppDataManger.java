package com.pasich.mynotes.data;

import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_TEXT_SIZE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_TEXT_STYLE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_TEXT_SIZE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_TEXT_STYLE;

import com.pasich.mynotes.data.database.DbHelper;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.data.preferences.PreferencesHelper;
import com.pasich.mynotes.utils.constants.PreferencesConfig;
import com.preference.Preference;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class AppDataManger implements DataManger {


    private final DbHelper dbHelper;
    private final PreferencesHelper preferencesHelper;

    @Inject
    AppDataManger(PreferencesHelper preferencesHelper, DbHelper dbHelper) {
        this.dbHelper = dbHelper;
        this.preferencesHelper = preferencesHelper;
    }


    /**
     * Tags
     */

    @Override
    public Observable<List<Tag>> getTags() {
        return dbHelper.getTags();
    }

    @Override
    public Observable<List<Tag>> getTagsUser() {
        return dbHelper.getTagsUser();
    }

    @Override
    public Observable<Integer> getCountTagAll() {
        return dbHelper.getCountTagAll();
    }

    @Override
    public Completable addTag(Tag tag) {
        return dbHelper.addTag(tag);
    }

    @Override
    public Completable deleteTag(Tag tag) {
        return dbHelper.deleteTag(tag);
    }

    @Override
    public Completable updateTag(Tag tag) {
        return dbHelper.updateTag(tag);
    }


    /**
     * Preferences
     */
    @Override
    public Preference getDefaultPreferences() {
        return preferencesHelper.getDefaultPreference();
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
    public String getTypeFaceNoteActivity() {
        return getDefaultPreferences().getString(ARGUMENT_PREFERENCE_TEXT_STYLE, ARGUMENT_DEFAULT_TEXT_STYLE);
    }
}
