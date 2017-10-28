package com.jakester.someflowchallenge.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jake on 10/26/2017.
 */

public class Message {
    public String userId;
    public String imageUrl;
    public String message;
    public String time;

    public Message(String id, String url, String text){
        userId = id;
        imageUrl = url;
        message = text;
        time = setTime();
    }

    public String getId(){
        return userId;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getMessage(){
        return message;
    }

    public String getTime() { return time; }

    private String setTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
        return sdf.format(new Date());
    }

}
