package com.pasich.mynotes.di.module;


import android.content.Context;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.databinding.ItemNoteBinding;
import com.pasich.mynotes.databinding.ItemNoteTrashBinding;
import com.pasich.mynotes.di.scope.ActivityContext;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.di.scope.PreferenceInfo;
import com.pasich.mynotes.utils.adapters.NoteAdapter;
import com.pasich.mynotes.utils.adapters.TrashAdapter;
import com.pasich.mynotes.utils.adapters.cofeeAdapter.CoffeeAdapter;
import com.pasich.mynotes.utils.adapters.searchAdapter.SearchNotesAdapter;
import com.pasich.mynotes.utils.constants.PreferencesConfig;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtiCoffee;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtilNote;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtilTag;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtilTrash;
import com.preference.PowerPreference;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ListUtilsModule {
    @Named("NotesItemSpaceDecoration")
    @Provides
    @PerActivity
    SpacesItemDecoration providerSpaceItemDecorationNotes() {
        return new SpacesItemDecoration(15);
    }

    @Provides
    @PerActivity
    NoteAdapter<ItemNoteBinding> providerGenericAdapter(@Named("Note") DiffUtil.ItemCallback<Note> diff) {
        return new NoteAdapter<>((DiffUtilNote) diff, R.layout.item_note, ItemNoteBinding::setNote);
    }

    @Provides
    @PerActivity
    SearchNotesAdapter providerSearchAdapter() {
        return new SearchNotesAdapter();
    }

    @Provides
    @PerActivity
    CoffeeAdapter providerCoffeeAdapter(@Named("Themes") DiffUtiCoffee diff) {
        return new CoffeeAdapter(diff);
    }

    @Provides
    @PerActivity
    TrashAdapter<ItemNoteTrashBinding> providerTrashAdapter(@Named("Trash") DiffUtil.ItemCallback<TrashNote> diff) {
        return new TrashAdapter<>((DiffUtilTrash) diff, R.layout.item_note_trash, ItemNoteTrashBinding::setNote);
    }

    @Named("Trash")
    @Provides
    @PerActivity
    DiffUtil.ItemCallback<TrashNote> providesDiffUtilCallbackNoteTrash(DiffUtilTrash diffUtil) {
        return diffUtil;
    }

    @Named("Themes")
    @Provides
    @PerActivity
    DiffUtiCoffee providesDiffUtiCoffee(DiffUtiCoffee diffUtil) {
        return diffUtil;
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
    @PerActivity
    LinearLayoutManager providesLinearLayoutManager(@ActivityContext Context context) {
        return new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
    }

    @Provides
    @PreferenceInfo
    int providesSpanCountStaggerGridLayout() {
        return PowerPreference.getDefaultFile().getInt(PreferencesConfig.ARGUMENT_PREFERENCE_FORMAT, PreferencesConfig.ARGUMENT_DEFAULT_FORMAT_VALUE);
    }

    @Named("TagsItemSpaceDecoration")
    @Provides
    @PerActivity
    SpacesItemDecoration providerSpaceItemDecorationTags() {
        return new SpacesItemDecoration(5);
    }


}
