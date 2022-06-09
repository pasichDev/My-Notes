package com.pasich.mynotes.presenter;

import com.pasich.mynotes.contract.TagsContract;

import javax.inject.Inject;

public class TagsPresenter implements TagsContract.presenter, TagsContract<TagsContract.view> {

  @Inject
  public TagsPresenter() {}

  @Override
  public void attachView(view mVIew) {}

  @Override
  public void viewIsReady() {}

  @Override
  public void detachView() {}

  @Override
  public void destroy() {}
}
