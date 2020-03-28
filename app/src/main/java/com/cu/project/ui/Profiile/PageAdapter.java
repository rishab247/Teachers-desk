package com.cu.project.ui.Profiile;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cu.project.HOD_fragment;

public class PageAdapter extends FragmentPagerAdapter {

    private int noOftabs;

    PageAdapter(FragmentManager fm , int numOftabs)
    {
        super(fm);
        this.noOftabs = numOftabs;
    }

    @Override
    public Fragment getItem(int position){

            switch (position)
            {
                case 0:
                    return new profile_fragement();
                case 1:

                    return new View_fragment();
                case 2:
                    return new HOD_fragment();
                default:
                    return null;
            }

    }

    @Override
    public int getCount(){return noOftabs;}
}
