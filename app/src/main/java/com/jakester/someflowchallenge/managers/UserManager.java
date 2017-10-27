package com.jakester.someflowchallenge.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Jake on 10/26/2017.
 */

public class UserManager {
    private static UserManager mInstance;
    private FirebaseUser mUser;

    public static UserManager getInstance(){
        if(mInstance == null){
            mInstance = new UserManager();
        }
        return mInstance;
    }

    private UserManager(){
    }

    public void setUser(FirebaseUser user){
        mUser = user;
    }

    public String getUserId(){
        return mUser.getUid();
    }
}
