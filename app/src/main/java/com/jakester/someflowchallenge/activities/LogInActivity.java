package com.jakester.someflowchallenge.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jakester.someflowchallenge.R;
import com.jakester.someflowchallenge.adapters.LogInPagerAdapter;

public class LogInActivity extends AppCompatActivity {


    LogInPagerAdapter mPagerAdapter;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mPagerAdapter = new LogInPagerAdapter(getSupportFragmentManager());

        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        vpPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

        user = FirebaseAuth.getInstance().getCurrentUser();
    }

}
