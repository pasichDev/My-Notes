package com.pasich.mynotes.utils.base.simplifications;

import android.widget.SeekBar;

public abstract class OnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

    protected abstract void changeProgress(int progress);

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        changeProgress(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
