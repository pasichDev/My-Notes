package com.pasich.mynotes.ui.presenter;

import com.pasich.mynotes.base.PresenterBase;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.notes.source.NotesRepository;
import com.pasich.mynotes.data.tags.source.TagsRepository;
import com.pasich.mynotes.ui.contract.NoteContract;

import java.util.concurrent.ExecutionException;

public class NotePresenter extends PresenterBase<NoteContract.view>
        implements NoteContract.presenter {

    private DataManager data;
    private TagsRepository tagsRepository;
    private NotesRepository notesRepository;

    public NotePresenter() {
    }

    @Override
    public void setDataManager(DataManager dataManager) {
        data = dataManager;
        tagsRepository = data.getTagsRepository();
        notesRepository = data.getNotesRepository();
        notesRepository = data.getNotesRepository();
    }

    @Override
    public void viewIsReady() {
        getView().settingsActionBar();
        getView().textSizeValueNote(data.getDefaultPreference().getInt("textSize", 16));
        getView().settingsEditTextNote(data.getDefaultPreference().getString("textStyle", "normal"));
        getView().initTypeActivity();
        getView().createSpeechRecognizer(data.getDefaultPreference().getString("spechLaunguage", "default"),
                data.getDefaultPreference().getString("setSpechOutputText", "line"));
        getView().initListeners();
        getView().initListenerSpeechRecognizer();
    }


    @Override
    public void detachView() {
    }

    @Override
    public void destroy() {
        tagsRepository.destroyInstance();
        notesRepository.destroyInstance();
        data = null;
    }


    @Override
    public void closeActivity() {
        getView().closeNoteActivity();

    }

    @Override
    public void loadingData(int idNote) {
        try {
            getView().loadingNote(notesRepository.getNoteFromId(idNote));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activateEditNote() {
        getView().activatedActivity();
    }

    @Override
    public void createNote(Note note) {
        notesRepository.addNote(note);
    }

    @Override
    public void saveNote(Note note) {
        notesRepository.updateNote(note);
    }

    @Override
    public void deleteNote(Note note) {
        synchronized (this) {
            data.getTrashRepository().moveToTrash(note);
        }
        notesRepository.deleteNote(note);
    }
}
