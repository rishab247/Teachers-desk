package com.cu.project.ui.Splash;
import com.cu.project.ui.base.MvpPresenter;


public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {

    void decideNextActivity();

}