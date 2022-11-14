package com.pasich.mynotes.ui.helloUI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.databinding.ActivityHelloBinding;
import com.pasich.mynotes.ui.contract.HelloContract;
import com.pasich.mynotes.ui.helloUI.fragments.FeaturesFragment;
import com.pasich.mynotes.ui.helloUI.fragments.FinishFragment;
import com.pasich.mynotes.ui.helloUI.tool.HelloTool;
import com.pasich.mynotes.ui.helloUI.tool.SavesNotes;
import com.pasich.mynotes.ui.presenter.HelloPresenter;

import javax.inject.Inject;

public class HelloActivity extends BaseActivity implements HelloTool, SavesNotes, HelloContract {

    @Inject
    public HelloPresenter mPresenter;
    private ActivityHelloBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHelloBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getActivityComponent().inject(this);
    }


    /**
     * Метод который реализует переключение между метками
     * 1 (step) - hello
     * 2 (step) - features
     * 3 (step) - backup and finish
     *
     * @param step - step
     */
    @Override
    public void nextFragment(int step) {
        if (step == 1) {
            openFragment(new FeaturesFragment());
        } else if (step == 2) {
            openFragment(new FinishFragment());
        }


    }


    private void openFragment(Fragment nextFragment) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.fragment_container_view, nextFragment, null).commit();

    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void saveNote(Note note) {
        mPresenter.addNote(note);
    }

    @Override
    public void saveTrash(TrashNote note) {
        mPresenter.addTrashNote(note);
    }

    @Override
    public void createTag(Tag tag) {
        mPresenter.createTag(tag);

    }
}