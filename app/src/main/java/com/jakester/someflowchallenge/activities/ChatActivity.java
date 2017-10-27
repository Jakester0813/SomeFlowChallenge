package com.jakester.someflowchallenge.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jakester.someflowchallenge.R;
import com.jakester.someflowchallenge.adapters.MessageAdapter;
import com.jakester.someflowchallenge.managers.UserManager;
import com.jakester.someflowchallenge.models.Message;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    EditText mChatEdit;
    RecyclerView mRecycler;
    MessageAdapter mAdapter;
    String copiedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mChatEdit = (EditText) findViewById(R.id.et_chat);

        mRecycler = (RecyclerView) findViewById(R.id.rv_chat);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new MessageAdapter(this, UserManager.getInstance().getUserId(),
                new ArrayList<Message>());

        mRecycler.setAdapter(mAdapter);

        setupUI(mRecycler);

        mChatEdit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if(actionId == EditorInfo.IME_ACTION_DONE){
                    sendMessage(textView.getText().toString(), true);
                    textView.setText("");
                    return true;
                }

                return false;
            }
        });
    }

    private void sendMessage(String chat, boolean fromUser){
        String id = fromUser ? UserManager.getInstance().getUserId() : "";
        mAdapter.addMessage(new Message(id,chat));
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(ChatActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
