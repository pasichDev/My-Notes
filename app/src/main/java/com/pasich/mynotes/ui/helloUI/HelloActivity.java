package com.pasich.mynotes.ui.helloUI;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ActivityHelloBinding;
import com.pasich.mynotes.ui.helloUI.fragments.FeaturesFragment;
import com.pasich.mynotes.ui.helloUI.fragments.FinishFragment;
import com.pasich.mynotes.ui.helloUI.fragments.HelloFragment;
import com.pasich.mynotes.ui.helloUI.tool.HelloTool;

public class HelloActivity extends AppCompatActivity implements HelloTool {

    private ActivityHelloBinding binding;
    private int mStep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHelloBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
        mStep = step + 1;
        if (step == 1) {
            openFragment(new FeaturesFragment(), step);
        } else if (step == 2) {
            openFragment(new FinishFragment(), step);
        }


    }

    @Override
    public void backFragment(int step) {
        mStep = step - 1;
        if (step == 2) {
            openFragment(new HelloFragment(), mStep);
        }
    }

    public void backButton(View view) {
        backFragment(mStep);
    }


    private void openFragment(Fragment nextFragment, int step) {
        //     binding.setButtonsVisibility(mStep >= 2);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.fragment_container_view, nextFragment, null).commit();

    }
}