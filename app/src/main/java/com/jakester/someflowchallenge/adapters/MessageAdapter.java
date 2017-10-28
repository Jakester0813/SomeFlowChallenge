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
import com.jakester.someflowchallenge.listeners.MessageLongClickListener;
import com.jakester.someflowchallenge.models.Message;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

/**
 * Created by Jake on 10/26/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> mMessages;
    private Context mContext;
    private String mUserId;
    private MessageLongClickListener mListener;

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
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_chat, parent, false);

        MessageViewHolder viewHolder = new MessageViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = mMessages.get(position);
        final boolean isMe = message.getId() != null && message.getId().equals(mUserId);

        if (isMe) {
            holder.imageMe.setVisibility(View.VISIBLE);
            holder.imageOther.setVisibility(View.GONE);
            holder.messageBody.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            holder.messageText.setBackgroundColor(mContext.getResources().getColor(R.color.yellow_you));
        } else {
            holder.imageOther.setVisibility(View.VISIBLE);
            holder.imageMe.setVisibility(View.GONE);
            holder.messageBody.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            holder.messageText.setBackgroundColor(mContext.getResources().getColor(R.color.blue_other_person));
        }
        if(!message.getImageUrl().equals("")){
            holder.mImage.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(getProfileUrl(message.getImageUrl())).into(holder.mImage);
        }
        final ImageView profileView = isMe ? holder.imageMe : holder.imageOther;
        Glide.with(mContext).load(getProfileUrl(message.getId())).into(profileView);
        holder.body.setText(message.getMessage());
    }

    public void setClickListener(MessageLongClickListener itemClickListener) {
        this.mListener = itemClickListener;
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


    public class MessageViewHolder extends RecyclerView.ViewHolder  implements View.OnLongClickListener{
        ImageView imageOther;
        ImageView imageMe;
        LinearLayout messageBody;
        RelativeLayout messageText;
        TextView body;
        ImageView mImage;

        public MessageViewHolder(View itemView) {
            super(itemView);
            imageOther = (ImageView)itemView.findViewById(R.id.ivProfileOther);
            imageMe = (ImageView)itemView.findViewById(R.id.ivProfileMe);
            messageBody = (LinearLayout) itemView.findViewById(R.id.ll_message_body);
            messageText = (RelativeLayout) itemView.findViewById(R.id.rl_message_text);
            body = (TextView)itemView.findViewById(R.id.tvBody);
            mImage = (ImageView) itemView.findViewById(R.id.iv_image);
            imageMe = (ImageView)itemView.findViewById(R.id.ivProfileMe);
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
}
