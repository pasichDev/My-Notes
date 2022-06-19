package com.pasich.mynotes.data.trash;

import androidx.room.Entity;
import androidx.room.Index;

import com.pasich.mynotes.data.notes.Note;

@Entity(
    tableName = "trash",
    indices = {
      @Index(
          value = {"title"},
          unique = true)
    })
public class TrashNote extends Note {}
