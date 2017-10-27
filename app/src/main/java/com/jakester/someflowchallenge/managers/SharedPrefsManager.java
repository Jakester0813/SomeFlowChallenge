package com.jakester.someflowchallenge.managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jake on 10/26/2017.
 */

public class SharedPrefsManager {

    private static SharedPrefsManager mInstance;
    private SharedPreferences mPrefs;

    public static SharedPrefsManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPrefsManager(context);
        }
        return mInstance;
    }

    private SharedPrefsManager(Context context){
        mPrefs = context.getSharedPreferences("FlowPrefs",Context.MODE_PRIVATE);
    }

    public void setVerificationCode(String code){
        mPrefs.edit().putString("verification_code", code).commit();
    }

    public String getVerificationCode(){
        return mPrefs.getString("verification_code","");
    }

    public void setSMS(String sms){
        mPrefs.edit().putString("sms_code", sms).commit();
    }

    public String getSMSCode(){
        return mPrefs.getString("sms_code","");
    }

}
