package com.pasich.mynotes.presenter;

import android.util.Log;

import com.pasich.mynotes.base.PresenterBase;
import com.pasich.mynotes.contract.TagsContract;

public class TagsPresenter extends PresenterBase<TagsContract.view>
    implements TagsContract.presenter {

  public TagsPresenter() {}

  @Override
  public void viewIsReady() {
    Log.wtf("pasic", "viewIsReady: okayReaddy");
  }

  @Override
  public void detachView() {}

  @Override
  public void destroy() {}

  @Override
  public void clickTag() {}

  @Override
  public void longClickTag() {}
}
