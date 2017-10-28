package com.jakester.someflowchallenge.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.jakester.someflowchallenge.models.Message;

import java.util.Random;

/**
 * Created by Jake on 10/27/2017.
 */

public class InteractiveChatManager {

    private static InteractiveChatManager mInstance;

    public static InteractiveChatManager getInstance(){
        if(mInstance == null){
            mInstance = new InteractiveChatManager();
        }
        return mInstance;
    }

    private InteractiveChatManager(){

    }

    public Message sendMessage(boolean hasImage){
        return new Message("", "", makeNewMessage(hasImage));
    }

    private String makeNewMessage(boolean hasImage){
        Random rand = new Random();
        String message = "";
        int i = rand.nextInt(3) + 1;
        if(hasImage){
            switch (i){
                case 1:
                    message = "That's a cute picture!";
                    break;
                case 2:
                    message = "LMAO Oh my god that is good!";
                    break;
                case 3:
                    message = "What am I seeing here?";
                    break;
            }
        }
        else {
            switch (i){
                case 1:
                    message = "Yo whats up?";
                    break;
                case 2:
                    message = "Yeah I feel you man.";
                    break;
                case 3:
                    message = "Give me a minute, something came up.";
                    break;
            }
        }

        return message;
    }

}
