package com.chrisjaunes.communication.client.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.contacts.model.ContactsView;

import java.util.ArrayList;
import java.util.List;
/**
 * a Adapter of RecycleView
 * @author chrisjuanes
 * @version 1.1
 */
public class NowContactsAdapter extends RecyclerView.Adapter<NowContactsAdapter.NowContactsViewHolder> {
    private final List<String> contactsList;
    private final ContactsViewManage contactsViewManage;
    private final ItemOnClickListener itemOnClickListener;

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

    public void setContactsStringList(final List<String> contactsStringList) {
        contactsList.clear();
        contactsList.addAll(contactsStringList);
        notifyDataSetChanged();
    }


    public interface ItemOnClickListener {
        void goToTalk(String account);
    }

    public static class NowContactsViewHolder extends RecyclerView.ViewHolder{
        final TextView nickName;
        final ImageView avatar;
        public NowContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            nickName = itemView.findViewById(R.id.tv_nickname);
            avatar = itemView.findViewById(R.id.iv_avatar);
        }
    }
}
