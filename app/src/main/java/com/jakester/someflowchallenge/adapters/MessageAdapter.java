package com.jakester.someflowchallenge.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakester.someflowchallenge.R;
import com.jakester.someflowchallenge.models.Message;
import com.jakester.someflowchallenge.viewholders.OtherMessageViewHolder;
import com.jakester.someflowchallenge.viewholders.YourMessageViewHolder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

/**
 * Created by Jake on 10/26/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> mMessages;
    private Context mContext;
    private String mUserId;
    private int YOUR_MESSAGE = 0;
    private int OTHER_MESSAGE = 1;

    public MessageAdapter(Context context, String userId, List<Message> messages) {
        this.mMessages = messages;
        this.mUserId = userId;
        this.mContext = context;
    }

    public void addMessage(Message message){
        this.mMessages.add(message);
        notifyItemChanged(mMessages.size()-1);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mMessages.get(position);
        if(message.getId() != null && message.getId().equals(mUserId)){
            return YOUR_MESSAGE;
        }
        else{
            return OTHER_MESSAGE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        if(viewType == YOUR_MESSAGE){
            View contactView = inflater.inflate(R.layout.your_message_layout, parent, false);
            holder =  new YourMessageViewHolder(contactView);
        }
        else{
            View contactView = inflater.inflate(R.layout.other_message_layout, parent, false);
            holder =  new OtherMessageViewHolder(contactView);
        }

        return holder;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = mMessages.get(position);
        final boolean isMe = message.getId() != null && message.getId().equals(mUserId);

        if(holder instanceof YourMessageViewHolder){
            Glide.with(mContext).load(getProfileUrl(message.getId())).into(((YourMessageViewHolder) holder).imageMe);
            ((YourMessageViewHolder) holder).body.setText(message.getMessage());

            /*if(!message.getImageUrl().equals("")){
            holder.mImage.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(getProfileUrl(message.getImageUrl())).into(holder.mImage);
        }*/
        }
        else{
            Glide.with(mContext).load(getProfileUrl(message.getId())).into(((OtherMessageViewHolder) holder).imageOther);
            ((OtherMessageViewHolder) holder).body.setText(message.getMessage());
            /*if(!message.getImageUrl().equals("")){
            holder.mImage.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(getProfileUrl(message.getImageUrl())).into(holder.mImage);
        }*/
        }

    }


    // Create a gravatar image based on the hash value obtained from userId
    private static String getProfileUrl(final String userId) {
        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

}
