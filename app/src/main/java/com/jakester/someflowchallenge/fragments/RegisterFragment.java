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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.jakester.someflowchallenge.R;
import com.jakester.someflowchallenge.managers.SharedPrefsManager;

import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class RegisterFragment extends BaseFragment {

    EditText mEmailEdit, mPasswordEdit, mNumberEdit, mRetypePassword;
    Button mRegisterButton;
    ProgressBar mProgressBar;

    public RegisterFragment() {

    }
    public static RegisterFragment newInstance(String param1, String param2) {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        mEmailEdit = (EditText) v.findViewById(R.id.et_email);
        mPasswordEdit = (EditText) v.findViewById(R.id.et_password);
        mRetypePassword = (EditText) v.findViewById(R.id.et_repeat_password);
        mNumberEdit = (EditText) v.findViewById(R.id.et_phone_number);
        mRegisterButton = (Button) v.findViewById(R.id.bt_register);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmailValid(mEmailEdit.getText().toString()) &&
                        isPasswordValid(mPasswordEdit.getText().toString()) &&
                        isPhoneNumberValid(mNumberEdit.getText().toString())){

                    mProgressBar.setVisibility(View.VISIBLE);
                    mRegisterButton.setVisibility(View.INVISIBLE);
                    registerUser(mEmailEdit.getText().toString(), mPasswordEdit.getText().toString(),
                            "+1"+mNumberEdit.getText().toString());
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

    private void registerUser(String email, String password, final String number){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            verifyNumber(number);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }

    private void verifyNumber(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                mCallbacks);
    }

    private void setCallback(){
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
                mProgressBar.setVisibility(View.GONE);
                mRegisterButton.setVisibility(View.VISIBLE);
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
}
