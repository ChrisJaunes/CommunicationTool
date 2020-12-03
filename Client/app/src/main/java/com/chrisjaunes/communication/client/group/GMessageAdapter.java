package com.chrisjaunes.communication.client.group;

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
import com.chrisjaunes.communication.client.account.model.AccountViewManage;
import com.chrisjaunes.communication.client.contacts.ContactsView;
import com.chrisjaunes.communication.client.contacts.ContactsViewManage;
import com.chrisjaunes.communication.client.group.model.GMessage;
import com.chrisjaunes.communication.client.myView.ChatTextView;

import java.util.List;

public class GMessageAdapter extends RecyclerView.Adapter<GMessageAdapter.ViewHolder> {
    private final List<GMessage> messageList;
    private final AccountViewManage accountViewManage;
    private final ContactsViewManage contactsViewManage;

    public GMessageAdapter(final List<GMessage> messageList) {
        this.messageList = messageList;
        this.accountViewManage = AccountViewManage.getInstance();
        this.contactsViewManage = ContactsViewManage.getInstance();
    }

    @NonNull
    @Override
    public GMessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        return new GMessageAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GMessage message = messageList.get(position);
        final AccountView accountView = accountViewManage.getAccountView();
        final ContactsView contactsView = contactsViewManage.getContactsView(message.getAccount());

        holder.time.setText(message.getSendTime());
        if (accountView.getAccount().equals(message.getAccount())) {
            holder.layout_left.setVisibility(View.GONE);
            holder.layout_right.setVisibility(View.VISIBLE);
            holder.right_avatar.setImageBitmap(contactsView.getAvatar());
            holder.right_nickname.setText(contactsView.getNickName());
            holder.right_content.setMyText(message.getContent());
            //holder.right_content.setMyColor(accountView.getChatTextStyleView());
        } else{
            holder.layout_left.setVisibility(View.VISIBLE);
            holder.layout_right.setVisibility(View.GONE);
            holder.left_avatar.setImageBitmap(contactsView.getAvatar());
            holder.left_nickname.setText(contactsView.getNickName());
            holder.left_content.setMyText(message.getContent());
            holder.left_content.setMyColor(contactsView.getChatTextStyle());
        }
        Log.v("GroupAdapter", message.getAccount() + message.getContent());
    }
    public void addMessageList(@NonNull List<GMessage> newMessageList) {
        for (GMessage gMessage : newMessageList) {
            messageList.add(messageList.size(), gMessage);
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
