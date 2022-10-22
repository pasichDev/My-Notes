package com.pasich.mynotes.ui.view.helloUI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.helloUI.fragments.FeaturesFragment;
import com.pasich.mynotes.ui.view.helloUI.fragments.FinishFragment;
import com.pasich.mynotes.ui.view.helloUI.fragments.HelloFragment;
import com.pasich.mynotes.ui.view.helloUI.tool.HelloTool;

public class HelloActivity extends AppCompatActivity implements HelloTool {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
    }


    /**
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

    @Override
    public void backFragment(int step) {
        if (step == 2) {
            openFragment(new HelloFragment());
        }
    }


    private void openFragment(Fragment nextFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, nextFragment, null).commit();
    }
}