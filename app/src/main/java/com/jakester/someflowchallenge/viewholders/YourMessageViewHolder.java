package com.jakester.someflowchallenge.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakester.someflowchallenge.R;

/**
 * Created by Jake on 10/27/2017.
 */

public class YourMessageViewHolder extends RecyclerView.ViewHolder{
    public ImageView imageMe;
    public LinearLayout messageBody;
    public RelativeLayout messageText;
    public TextView body;
    public ImageView mImage;

    public YourMessageViewHolder(View itemView) {
        super(itemView);
        imageMe = (ImageView)itemView.findViewById(R.id.ivProfileMe);
        messageBody = (LinearLayout) itemView.findViewById(R.id.ll_message_body);
        messageText = (RelativeLayout) itemView.findViewById(R.id.rl_message_text);
        body = (TextView)itemView.findViewById(R.id.tvBody);
        mImage = (ImageView) itemView.findViewById(R.id.iv_image);
    }
}
