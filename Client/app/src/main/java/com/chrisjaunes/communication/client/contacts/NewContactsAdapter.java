package com.chrisjaunes.communication.client.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.contacts.model.ContactsConfig;
import com.chrisjaunes.communication.client.contacts.model.ContactsView;
import com.chrisjaunes.communication.client.contacts.model.ContactsViewManage;

import java.util.ArrayList;
import java.util.List;

public class NewContactsAdapter extends RecyclerView.Adapter<NewContactsAdapter.NowContactsViewHolder> {
    final List<String> contactsList;
    final ContactsViewManage contactsViewManage;
    final ItemOnClickListener itemOnClickListener;

    public NewContactsAdapter(final ItemOnClickListener itemOnClickListener) {
        this.contactsList = new ArrayList<>();
        this.contactsViewManage = ContactsViewManage.getInstance();
        this.itemOnClickListener = itemOnClickListener;
    }

    @NonNull
    @Override
    public NowContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts,parent,false);
        return new NowContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NowContactsViewHolder holder, int position) {
        final ContactsView contactsView = contactsViewManage.getContactsView(contactsList.get(position));
        holder.nickName.setText(contactsView.getNickName());
        holder.avatar.setImageBitmap(contactsView.getAvatarView());
        holder.agree.setOnClickListener(v -> itemOnClickListener.handleRequest(contactsView.getAccount(), ContactsConfig.CONTACTS_FRIENDS_AGREE));
        holder.reject.setOnClickListener(v -> itemOnClickListener.handleRequest(contactsView.getAccount(), ContactsConfig.CONTACTS_FRIENDS_REJECT));
    }

    @Override
    public int getItemCount() { return contactsList.size(); }

    public void setContactsStringList(final List<String> contactsStringList) {
        contactsList.clear();
        contactsList.addAll(contactsStringList);
        notifyDataSetChanged();
    }

    public interface ItemOnClickListener {
        void handleRequest(String account, String operation);
    }
    public static class NowContactsViewHolder extends RecyclerView.ViewHolder{
        TextView nickName;
        ImageView avatar;
        final Button agree;
        final Button reject;
        public NowContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            nickName = itemView.findViewById(R.id.tv_nickname);
            avatar = itemView.findViewById(R.id.iv_avatar);
            agree = itemView.findViewById(R.id.btn_agree);
            agree.setVisibility(View.VISIBLE);
            reject = itemView.findViewById(R.id.btn_reject);
            reject.setVisibility(View.VISIBLE);
        }
    }
}
