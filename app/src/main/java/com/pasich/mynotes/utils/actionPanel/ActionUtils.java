package com.pasich.mynotes.utils.actionPanel;

import android.view.LayoutInflater;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.databinding.ActionPanelBinding;
import com.pasich.mynotes.utils.adapters.NotesAdapter;
import com.pasich.mynotes.utils.adapters.TrashNotesAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActionUtils {

    private boolean ACTION_ON = false;

    /**
     * Два адптера которые мы используем
     */
    private NotesAdapter mAdapter;
    private TrashNotesAdapter mAdapterTrash;


    private final int PAYLOAD_BACKGROUND = 22;
    private final ArrayList<Note> ArrayChecked = new ArrayList<>();
    private ActionPanelBinding binding;
    private ConstraintLayout mViewRoot;
    private ManagerViewAction managerViewAction;


    public void createObject(LayoutInflater inflater, NotesAdapter adapter, ConstraintLayout view) {
        this.mAdapter = adapter;
        this.mViewRoot = view;
        this.binding = ActionPanelBinding.inflate(inflater);
        this.managerViewAction = (ManagerViewAction) mViewRoot.getContext();
        addActionPanel();
        setListener();
    }

    public void createObject(LayoutInflater inflater, TrashNotesAdapter adapter, ConstraintLayout view) {
        this.mAdapterTrash = adapter;
        this.mViewRoot = view;
        this.binding = ActionPanelBinding.inflate(inflater);
        this.managerViewAction = (ManagerViewAction) mViewRoot.getContext();
        addActionPanel();
        setListener();
    }


    private void setListener() {
        binding.closeActionPanel.setOnClickListener(v -> closeActionPanel());
        binding.actionPanelDelete.setOnClickListener(v -> managerViewAction.deleteNotes(getArrayChecked()));
        binding.actionPanelShare.setOnClickListener(v -> managerViewAction.shareNotes(getArrayChecked()));
    }

    private void addActionPanel() {
        mViewRoot.addView(binding.getRoot());
        createConstraintSetActionPanel();
    }

    private void createConstraintSetActionPanel() {
        ConstraintSet set = new ConstraintSet();
        set.constrainHeight(binding.actionPanel.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(binding.actionPanel.getId(), ConstraintSet.WRAP_CONTENT);

        set.connect(
                binding.actionPanel.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 50);
        set.connect(
                binding.actionPanel.getId(),
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM,
                50);

        set.applyTo(mViewRoot);

        binding.actionPanel.setVisibility(View.GONE);
    }

    public void setTrash() {
        binding.actionPanelShare.setVisibility(View.GONE);
    }

    /**
     * @return - Returns the value of ACTION_ON
     */
    public boolean getAction() {
        return this.ACTION_ON;
    }

    /**
     * Set value to ACTION_ON
     *
     * @param arg - (boolean) true/false
     */
    public void setAction(boolean arg) {
        this.ACTION_ON = arg;
    }

    /**
     * Returns an array of data from the adapter
     *
     * @return - data adapter
     */
    private List<Note> getDataAdapter() {
        return mAdapter.getCurrentList();
    }

    /**
     * @return - Number of marked items (int)
     */
    public int getCountCheckedItem() {
        List<Note> data = getDataAdapter();
        int count = 0;
        for (int i = 0; i < data.size(); i++) {
            count = data.get(i).getChecked() ? count + 1 : count;
        }
        return count;
    }

    /**
     * Clear all marks
     */
    private void checkedClean() {
        List<Note> data = getDataAdapter();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getChecked()) data.get(i).setChecked(false);
            mAdapter.notifyItemChanged(i, PAYLOAD_BACKGROUND);
        }
    }

    /**
     * Activate the visibility of the action panel
     */
    private void activateActionPanel() {
        managerViewAction.activateActionPanel();
        binding.actionPanel.setVisibility(View.VISIBLE);
    }

    /**
     * Deactivate the visibility of the action panel
     */
    private void deactivationActionPanel() {
        managerViewAction.deactivationActionPanel();
        binding.actionPanel.setVisibility(View.GONE);
    }

    /**
     * The method that controls the visibility of the action panel
     */
    public void manageActionPanel() {
        int countChecked = getCountCheckedItem();
        if (countChecked == 0) deactivationActionPanel();
        else if (!getAction() || countChecked == 1) activateActionPanel();
    }

    /**
     * Action panel control when unchecked
     */
    public void isCheckedItemFalse(Note note) {
        if (getCountCheckedItem() == 0) {
            getArrayChecked().clear();
            closeActionPanel();
        } else {
            getArrayChecked().remove(note);
        }
    }

    /**
     * Action panel control when adding checkmark
     */
    public void isCheckedItem(Note note) {
        if (!getArrayChecked().contains(note)) getArrayChecked().add(note);
        else getArrayChecked().remove(note);
        if (!(getAction())) setAction(true);
    }

    /**
     * The method that disables the actionPanel when manually accessed from under the key
     */
    public void closeActionPanel() {
        checkedClean();
        deactivationActionPanel();
        setAction(false);
        getArrayChecked().clear();
    }

    public ArrayList<Note> getArrayChecked() {
        return this.ArrayChecked;
    }

    public void selectItemAction(int item) {
        Note note = mAdapter.getCurrentList().get(item);
        if (note.getChecked()) {
            note.setChecked(false);
            isCheckedItemFalse(note);
        } else {
            isCheckedItem(note);
            note.setChecked(true);
        }
        manageActionPanel();
        mAdapter.notifyItemChanged(item, PAYLOAD_BACKGROUND);
    }


}
