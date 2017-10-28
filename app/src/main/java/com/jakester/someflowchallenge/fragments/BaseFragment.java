package com.jakester.someflowchallenge.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.jakester.someflowchallenge.R;
import com.jakester.someflowchallenge.activities.ChatActivity;
import com.jakester.someflowchallenge.managers.SharedPrefsManager;

import static android.content.ContentValues.TAG;

public class BaseFragment extends Fragment {

    String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^-_!+=])(?=\\S+$).{6,}$";

    protected FirebaseAuth mAuth;
    protected PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    public BaseFragment() {
        // Required empty public constructor
    }

    public static BaseFragment newInstance() {
        return new BaseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    public boolean isEmailValid(String email){
        return email.contains("@");
    }

    public boolean isPasswordValid(String password){
        return password.matches(passwordRegex);
    }

    public boolean isPhoneNumberValid(String number){
        return TextUtils.isDigitsOnly(number);
    }

    protected void goToChat(){
        Intent i = new Intent(getActivity(), ChatActivity.class);
        startActivity(i);
    }

}
