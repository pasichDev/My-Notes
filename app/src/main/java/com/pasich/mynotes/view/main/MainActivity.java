package com.pasich.mynotes.view.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.pasich.mynotes.R;
import com.pasich.mynotes.contract.TagsContract;
import com.pasich.mynotes.databinding.ActivityMainBinding;
import com.pasich.mynotes.injection.App;
import com.pasich.mynotes.view.main.dagger.MainActivityModule;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements TagsContract.view {

  @Inject public TagsContract.presenter tagsPresenter;

  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

    // inject activity
    App.getApp(this)
        .getComponentsHolder()
        .getActivityComponent(getClass(), new MainActivityModule())
        .inject(this);

    tagsPresenter.attachView(this);
    tagsPresenter.viewIsReady();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    tagsPresenter.detachView();
    if (isFinishing()) {
      tagsPresenter.destroy();
      App.getApp(this).getComponentsHolder().releaseActivityComponent(getClass());
    }
  }
}
