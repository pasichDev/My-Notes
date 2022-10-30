package com.pasich.mynotes.ui.presenter;

import static com.pasich.mynotes.utils.constants.TagSettings.MAX_TAG_COUNT;

import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class MainPresenter extends AppBasePresenter<MainContract.view> implements MainContract.presenter {


    @Inject
    public MainPresenter(SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable,
                         DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void viewIsReady() {
        getView().settingsSearchView();
        getView().settingsTagsList();
        getView().settingsNotesList();

        getView().loadingData(getDataManager().getTags(), null);

        getView().initListeners();
    }


    @Override
    public void destroy() {
    }

    @Override
    public void newNotesClick() {
        if (isViewAttached()) getView().newNotesButton();
    }

    @Override
    public void moreActivityClick() {
        if (isViewAttached()) getView().moreActivity();
    }

    @Override
    public void deleteTag(Tag tag, boolean deleteNotes) {
        if (getDataManager() != null) {
            if (!deleteNotes) {
            /*    for (Note note : getDataManager().getNotesFroTag(tag.getNameTag())) {
                    note.setTag("");
                    getDataManager().updateNote(note);
                }

             */
            } else {
             /*   for (Note note : getDataManager().getNotesFroTag(tag.getNameTag())) {
                    getDataManager().moveToTrash(note);
                    getDataManager().deleteNote(note);
                }

              */

            }


            getDataManager().deleteTag(tag);
        }
    }


    @Override
    public void editVisibility(Tag tag) {
        //  if (getDataManager() != null) getTagsRepository().updateTag(tag);
    }

    @Override
    public void clickTag(Tag tag, int position) {
            if (tag.getSystemAction() == 1) {
                getDataManager().getCountTagAll()
                        .subscribeOn(getSchedulerProvider().io()).
                        observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                getCompositeDisposable().add(d);
                            }

                            @Override
                            public void onSuccess(Integer integer) {
                                if (integer >= MAX_TAG_COUNT) {
                                    getView().startToastCheckCountTags();
                                } else getView().startCreateTagDialog();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
            } else {
                getView().selectTagUser(position);

            }
    }


    @Override
    public void clickLongTag(Tag tag) {
        if (tag.getSystemAction() == 0) {
            Integer[] keysNote = new Integer[0];
            //    try {
//                keysNote = new Integer[]{getNotesRepository().getCountNoteTag(tag.getNameTag())};
            //   } catch (ExecutionException | InterruptedException e) {
            //     e.printStackTrace();
            //      }
            getView().choiceTagDialog(tag, new Integer[]{0});
        }
    }

    @Override
    public void clickNote(int idNote) {
        getView().openNoteEdit(idNote);
    }


    @Override
    public void deleteNote(Note note) {
        if (getDataManager() != null) {
            //    getTrashRepository().moveToTrash(note);
            //      getNotesRepository().deleteNote(note);
        }
    }

    @Override
    public void deleteNotesArray(ArrayList<Note> notes) {
        if (getDataManager() != null) {
            //     getTrashRepository().moveToTrash(notes);
            //       getNotesRepository().deleteNote(notes);
        }
    }

    @Override
    public void addNote(Note note) {
      /*  try {
            getNotesRepository().addNote(note);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

       */
    }

    @Override
    public void sortButton() {
        getView().sortButton();
    }

    @Override
    public void formatButton() {
        getView().formatButton(getDataManager().getFormatCount());
    }

    @Override
    public void startSearchDialog() {
        getView().startSearchDialog();
    }


}
