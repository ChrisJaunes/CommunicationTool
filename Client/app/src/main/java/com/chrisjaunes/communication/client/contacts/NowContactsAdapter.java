package com.chrisjaunes.communication.client.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.contacts.model.ContactsRaw;
import com.chrisjaunes.communication.client.contacts.model.ContactsView;
import com.chrisjaunes.communication.client.contacts.model.ContactsViewManage;
import com.chrisjaunes.communication.client.utils.BitmapHelper;

import java.util.ArrayList;
import java.util.List;

public class NowContactsAdapter extends RecyclerView.Adapter<NowContactsAdapter.NowContactsViewHolder> {
    final List<String> contactsList;
    final ContactsViewManage contactsViewManage;
    final ItemOnClickListener itemOnClickListener;

    public NowContactsAdapter(final ItemOnClickListener itemOnClickListener) {
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
        holder.itemView.setOnClickListener(v -> itemOnClickListener.goToTalk(contactsView.getAccount()));
    }

    @Override
    public int getItemCount() { return contactsList.size(); }

    public void addContactsViewList(final List<String> contactsStringList) {
        for (String contactsString : contactsStringList) {
            contactsList.add(contactsList.size(), contactsString);
            notifyItemChanged(contactsList.size());
        }
    }

    public interface ItemOnClickListener {
        void goToTalk(String account);
    }
    public static class NowContactsViewHolder extends RecyclerView.ViewHolder{
        TextView nickName;
        ImageView avatar;
        public NowContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            nickName = itemView.findViewById(R.id.friendUerName);
            avatar = itemView.findViewById(R.id.friend_image);
        }
    }
}
