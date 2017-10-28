package com.jakester.someflowchallenge.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakester.someflowchallenge.R;
import com.jakester.someflowchallenge.listeners.MessageLongClickListener;

/**
 * Created by Jake on 10/27/2017.
 */

public class YourMessageViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
    public ImageView imageMe;
    public LinearLayout messageBody;
    public RelativeLayout messageText;
    public TextView body;
    public ImageView mImage;
    public MessageLongClickListener mListener;

    public YourMessageViewHolder(View itemView, MessageLongClickListener listener) {
        super(itemView);
        imageMe = (ImageView)itemView.findViewById(R.id.ivProfileMe);
        messageBody = (LinearLayout) itemView.findViewById(R.id.ll_message_body);
        messageText = (RelativeLayout) itemView.findViewById(R.id.rl_message_text);
        body = (TextView)itemView.findViewById(R.id.tvBody);
        mImage = (ImageView) itemView.findViewById(R.id.iv_image);
        mListener = listener;
        body.setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View view) {
        if (mListener != null){
            mListener.onClick(view);
            return true;
        }
        return false;
    }
}
