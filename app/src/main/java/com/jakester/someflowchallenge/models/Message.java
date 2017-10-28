package com.jakester.someflowchallenge.models;

/**
 * Created by Jake on 10/26/2017.
 */

public class Message {
    public String userId;
    public String imageUrl;
    public String message;

    public Message(String id, String url, String text){
        userId = id;
        imageUrl = url;
        message = text;
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

}
