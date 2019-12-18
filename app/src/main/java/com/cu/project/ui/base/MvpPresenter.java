package com.cu.project.ui.base;

public interface MvpPresenter <V extends MvpView> {

    void onAttach(V mvpView);
}
