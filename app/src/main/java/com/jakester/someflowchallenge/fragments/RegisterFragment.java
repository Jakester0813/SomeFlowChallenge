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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.jakester.someflowchallenge.R;

import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class RegisterFragment extends BaseFragment {

    EditText mEmailEdit, mPasswordEdit, mNumberEdit, mRetypePassword;
    Button mRegisterButton;

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
}
