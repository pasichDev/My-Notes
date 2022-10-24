package com.pasich.mynotes.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.di.ActivityContext;
import com.pasich.mynotes.di.PerActivity;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.presenter.MainPresenter;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Inject
    String text;


    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }


    @Provides
    @PerActivity
    MainContract.presenter providesMainPresenter(MainPresenter presenter) {
        return presenter;
    }



    /*
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideScheduleProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(activity);
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> providesMainPresenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    BlogMvpPresenter<BlogMvpView> providesBlogPresenter(BlogPresenter<BlogMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    OpenSourceMvpPresenter<OpenSourceMvpView> providesOpenSourcePresenter(OpenSourcePresenter<OpenSourceMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    BlogDetailsMvpPresenter<BlogDetailsMvpView> providesBlogDetailsPresenter(BlogDetailsPresenter<BlogDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    OSDetailMvpPresenter<OSDetailMvpView> providesOSDetailPresenter(OSDetailPresenter<OSDetailMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    FeedMvpPresenter<FeedMvpView> providesFeedPresenter(FeedPresenter<FeedMvpView> presenter) {
        return presenter;
    }

    @Provides
    MainPagerAdapter providesMainPagerAdapter(AppCompatActivity activity) {
        return new MainPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    @PerActivity
    BlogAdapter providesBlogAdapter() {
        return new BlogAdapter(new ArrayList<Blog>());
    }

    @Provides
    @PerActivity
    OpenSourceAdapter providesOpenSourceAdapter() {
        return new OpenSourceAdapter(new ArrayList<OpenSource>());
    }

    @Provides
    @PerActivity
    FeedAdapter providesFeedAdapter() {
        return new FeedAdapter(new ArrayList<Object>());
    }

     */
}
