package com.chrisjaunes.communication.client.talk;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.account.model.AccountView;
import com.chrisjaunes.communication.client.account.AccountViewManage;
import com.chrisjaunes.communication.client.contacts.model.ContactsView;
import com.chrisjaunes.communication.client.myView.ChatTextView;

import java.util.List;

public class TalkAdapter extends  RecyclerView.Adapter<TalkAdapter.ViewHolder>{
    final private List<TMessage> messageList;
    ContactsView contactsView;
    final AccountView accountView;
    TalkAdapter(final List<TMessage> messageList) {
        this.messageList = messageList;
        accountView = AccountViewManage.getInstance().getAccountView();
        contactsView = ContactsView.CONTACTS_VIEW_DEFAULT;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TMessage message = messageList.get(position);
        holder.time.setText(message.getSendTime());
        if (accountView.getAccount().equals(message.getAccount1())) {
            holder.layout_left.setVisibility(View.GONE);
            holder.layout_right.setVisibility(View.VISIBLE);
            holder.right_avatar.setImageBitmap(accountView.getAvatarView());
            holder.right_nickname.setText(accountView.getNickName());
            holder.right_content.setMyColor(accountView.getChatTextStyleView());
            if (message.getContent_type() == 1) {
                holder.right_content.setMyText(message.getContent());
            } else {
                holder.right_content.setMyText("非文本请等待之后的迭代");
            }
        } else {
            holder.layout_left.setVisibility(View.VISIBLE);
            holder.layout_right.setVisibility(View.GONE);
            holder.left_avatar.setImageBitmap(contactsView.getAvatarView());
            holder.left_nickname.setText(contactsView.getNickName());
            holder.left_content.setMyColor(contactsView.getChatTextStyleView());
            if (message.getContent_type() == 1) {
                holder.left_content.setMyText(message.getContent());
            } else {
                holder.left_content.setMyText("非文本请等待之后的迭代");
            }
        }
        Log.v("TalkAdapter", message.getAccount1() + message.getContent());
        Log.v("TalkAdapter", "" + contactsView.getAvatarView());
    }
    public void addMessageList(@NonNull List<TMessage> newMessageList) {
        for (TMessage tMessage : newMessageList) {
            messageList.add(messageList.size(), tMessage);
            notifyItemChanged(messageList.size());
        }
    }
    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView time;
        private final LinearLayout layout_left;
        private final LinearLayout layout_right;
        private final ImageView left_avatar;
        private final TextView left_nickname;
        private final ChatTextView left_content;
        private final ImageView right_avatar;
        private final TextView right_nickname;
        private final ChatTextView right_content;
        public ViewHolder(@NonNull View view) {
            super(view);
            time = view.findViewById(R.id.tv_time);
            layout_left = view.findViewById(R.id.layout_left);
            layout_right = view.findViewById(R.id.layout_right);
            left_avatar = view.findViewById(R.id.left_avatar);
            left_nickname = view.findViewById(R.id.left_nickname);
            left_content = view.findViewById(R.id.left_content);
            right_avatar = view.findViewById(R.id.right_avatar);
            right_nickname = view.findViewById(R.id.right_nickname);
            right_content = view.findViewById(R.id.message_right_content);
        }
    }
}
