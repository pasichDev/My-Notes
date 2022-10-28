package com.pasich.mynotes.di.module;


import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.databinding.ItemNoteBinding;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.di.scope.PreferenceInfo;
import com.pasich.mynotes.utils.adapters.NoteAdapter;
import com.pasich.mynotes.utils.constants.PreferencesConfig;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtilNote;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtilTag;
import com.preference.PowerPreference;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    @PerActivity
    NoteAdapter<ItemNoteBinding> providerGenericAdapter(@Named("Note") DiffUtil.ItemCallback<Note> diff) {
        return new NoteAdapter<>((DiffUtilNote) diff, R.layout.item_note, ItemNoteBinding::setNote);
    }

    @Named("Note")
    @Provides
    @PerActivity
    DiffUtil.ItemCallback<Note> providesDiffUtilCallbackNote(DiffUtilNote diffUtil) {
        return diffUtil;
    }

    @Named("Tag")
    @Provides
    @PerActivity
    DiffUtil.ItemCallback<Tag> providesDiffUtilCallbackTag(DiffUtilTag diffUtil) {
        return diffUtil;
    }

    @Provides
    @PerActivity
    StaggeredGridLayoutManager providesStaggeredGridLayoutManager(@PreferenceInfo int spanCount) {
        return new StaggeredGridLayoutManager(spanCount, LinearLayoutManager.VERTICAL);
    }

    @Provides
    @PreferenceInfo
    int providesSpanCountStaggerGridLayout() {
        return PowerPreference.getDefaultFile().getInt(PreferencesConfig.ARGUMENT_PREFERENCE_FORMAT, PreferencesConfig.ARGUMENT_DEFAULT_FORMAT_VALUE);
    }
}
