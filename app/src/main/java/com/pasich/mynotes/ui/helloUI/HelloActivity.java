package com.pasich.mynotes.ui.helloUI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ActivityHelloBinding;
import com.pasich.mynotes.ui.helloUI.fragments.FeaturesFragment;
import com.pasich.mynotes.ui.helloUI.fragments.FinishFragment;
import com.pasich.mynotes.ui.helloUI.tool.HelloTool;
import com.pasich.mynotes.utils.Debug_CreatesTestNotes;

public class HelloActivity extends AppCompatActivity implements HelloTool {

    private ActivityHelloBinding binding;

    private Debug_CreatesTestNotes debug_createsTestNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHelloBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        debug_createsTestNotes = new Debug_CreatesTestNotes(this);


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


    private void openFragment(Fragment nextFragment) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.fragment_container_view, nextFragment, null).commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}