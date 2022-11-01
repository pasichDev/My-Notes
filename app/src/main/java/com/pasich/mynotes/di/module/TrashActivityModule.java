package com.pasich.mynotes.di.module;


import androidx.recyclerview.widget.DiffUtil;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.databinding.ItemNoteTrashBinding;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.utils.adapters.TrashAdapter;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtilTrash;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class TrashActivityModule {

    @Provides
    @PerActivity
    TrashAdapter<ItemNoteTrashBinding> providerTrashAdapter(@Named("Trash") DiffUtil.ItemCallback<TrashNote> diff) {
        return new TrashAdapter<>((DiffUtilTrash) diff, R.layout.item_note_trash, ItemNoteTrashBinding::setNote);
    }

    @Named("Trash")
    @Provides
    @PerActivity
    DiffUtil.ItemCallback<TrashNote> providesDiffUtilCallbackNote(DiffUtilTrash diffUtil) {
        return diffUtil;
    }


}
