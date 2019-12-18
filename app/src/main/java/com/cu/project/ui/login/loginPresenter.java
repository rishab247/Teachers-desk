package com.cu.project.ui.login;

import com.cu.project.data.DataManager;
import com.cu.project.ui.base.BasePresenter;

/**
 * Created by gaura on 22-08-2017.
 */

class LoginPresenter<V extends loginMvpView> extends BasePresenter<V> implements loginMvpPresenter<V> {

    public LoginPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void startLogin(String emailId) {

    }

}