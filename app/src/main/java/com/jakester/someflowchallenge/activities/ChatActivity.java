package com.jakester.someflowchallenge.activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakester.someflowchallenge.R;
import com.jakester.someflowchallenge.adapters.MessageAdapter;
import com.jakester.someflowchallenge.listeners.MessageLongClickListener;
import com.jakester.someflowchallenge.managers.InteractiveChatManager;
import com.jakester.someflowchallenge.managers.UserManager;
import com.jakester.someflowchallenge.models.Message;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements MessageLongClickListener {

    EditText mChatEdit;
    RecyclerView mRecycler;
    MessageAdapter mAdapter;
    Button mSendButton;
    Toolbar toolbar;
    ClipboardManager clipboard;
    ClipData clip;
    Runnable interaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Chat");
        setSupportActionBar(toolbar);
        mChatEdit = (EditText) findViewById(R.id.et_chat);

        mRecycler = (RecyclerView) findViewById(R.id.rv_chat);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new MessageAdapter(this, UserManager.getInstance().getUserId(),
                new ArrayList<Message>());

        mAdapter.setClickListener(this);

        mRecycler.setAdapter(mAdapter);

        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        interaction = new Runnable() {
            @Override
            public void run() {
                mAdapter.addMessage(InteractiveChatManager.getInstance().sendMessage(false));
            }
        };

        /*mRecycler.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(view.getId() == R.id.tvBody){

                    clip = ClipData.newPlainText("message", ((TextView)view).getText().toString());

                    Toast.makeText(ChatActivity.this,"Text Copied", Toast.LENGTH_SHORT).show();

                    return true;
                }

                return false;
            }
        });*/

        mSendButton = (Button) findViewById(R.id.btn_send);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(mChatEdit.getText().toString(), true);
                mChatEdit.setText("");
                hideSoftKeyboard(ChatActivity.this);
                new Handler().postDelayed(interaction, 5000L);
            }
        });

        setupUI(mRecycler);

        mChatEdit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipData.Item item = clip.getItemAt(0);
                if(item != null){
                    mChatEdit.setText(item.getText());
                }
                return false;
            }
        });
    }

    private void sendMessage(String chat, boolean fromUser){
        String id = fromUser ? UserManager.getInstance().getUserId() : "";
        mAdapter.addMessage(new Message(id, "", chat));
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

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.tvBody){

            clip = ClipData.newPlainText("message", ((TextView)view).getText().toString());

            Toast.makeText(ChatActivity.this,"Text Copied", Toast.LENGTH_SHORT).show();

        }

    }
}
