package com.jakester.someflowchallenge.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.jakester.someflowchallenge.R;
import com.jakester.someflowchallenge.managers.SharedPrefsManager;
import com.jakester.someflowchallenge.managers.UserManager;

import java.util.concurrent.TimeUnit;

import static android.R.attr.phoneNumber;
import static android.content.ContentValues.TAG;


public class LoginFragment extends BaseFragment {

    EditText mEmailEdit, mPasswordEdit;
    Button mLoginButton;
    TextView mForgotPasswordText;


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        mEmailEdit = (EditText) v.findViewById(R.id.et_email);
        mPasswordEdit = (EditText) v.findViewById(R.id.et_password);
        mLoginButton = (Button) v.findViewById(R.id.bt_login);
        mForgotPasswordText = (TextView) v.findViewById(R.id.tv_forgot_password);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((isEmailValid(mEmailEdit.getText().toString()) || isPhoneNumberValid(mEmailEdit.getText().toString()))
                        && isPasswordValid(mPasswordEdit.getText().toString())){
                    loginProcess(mEmailEdit.getText().toString(), mPasswordEdit.getText().toString());
                }
                else {
                    if(!isEmailValid(mEmailEdit.getText().toString())){
                        Toast.makeText(getActivity(),"Must be a valid Email", Toast.LENGTH_LONG).show();
                    }
                    else if(!isPasswordValid(mPasswordEdit.getText().toString())){
                        Toast.makeText(getActivity(),"Must be a valid password", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getActivity(),"Must be a valid number (numbers only!)", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return v;
    }

    private void loginProcess(String username, String password){
        if(isEmailValid(mEmailEdit.getText().toString())){
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                UserManager.getInstance().setUser(task.getResult().getUser());
                                goToChat();
                            } else {
                                try
                                {
                                    throw task.getException();
                                }
                                // if user enters wrong password.
                                catch (FirebaseAuthInvalidUserException malformedEmail) {
                                    Toast.makeText(getActivity(), "Authentication failed. Account doesn't exist",
                                            Toast.LENGTH_SHORT).show();
                                }
                                catch (FirebaseAuthInvalidCredentialsException existEmail) {
                                    Toast.makeText(getActivity(), "Authentication failed. Your credentials are invalid",
                                            Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception e) {
                                    Log.d(TAG, "onComplete: " + e.getMessage());
                                }
                                Log.w(TAG, "signInWithEmail:failure", task.getException());

                            }

                            // ...
                        }
                    });
        }
        else if(isPhoneNumberValid(mEmailEdit.getText().toString())){
            String verificationId = SharedPrefsManager.getInstance(getActivity()).getVerificationCode();
            String code = SharedPrefsManager.getInstance(getActivity()).getSMSCode();
            //PhoneAuthProvider.getCredential(verificationId, code);
            signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(verificationId, code));
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            UserManager.getInstance().setUser(task.getResult().getUser());
                            goToChat();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}
