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
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                SharedPrefsManager.getInstance(getActivity()).setSMS(credential.getSmsCode());
                Toast.makeText(getActivity(), "Registration compeleted!",
                        Toast.LENGTH_SHORT).show();
                //signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                SharedPrefsManager.getInstance(getActivity()).setVerificationCode(verificationId);
                // Save verification ID and resending token so we can use them later
                //mVerificationId = verificationId;
                //mResendToken = token;

                // ...
            }
        };
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
