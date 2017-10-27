package com.jakester.someflowchallenge.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jakester.someflowchallenge.fragments.LoginFragment;
import com.jakester.someflowchallenge.fragments.RegisterFragment;

/**
 * Created by Jake on 10/24/2017.
 */

public class LogInPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] { "Login", "Register"};

    public LogInPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new LoginFragment();
        }
        else if(position == 1){
            return new RegisterFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
