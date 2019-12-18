package com.cu.project.ui.login;

import com.cu.project.ui.base.MvpPresenter;

public interface loginMvpPresenter<V extends loginMvpView> extends MvpPresenter<V> {

    void startLogin(String emailId);

}