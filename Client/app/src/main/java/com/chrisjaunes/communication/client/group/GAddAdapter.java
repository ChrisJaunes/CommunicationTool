package com.chrisjaunes.communication.client.group;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.contacts.model.ContactsView;
import com.chrisjaunes.communication.client.contacts.model.ContactsViewManage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GAddAdapter extends RecyclerView.Adapter<GAddAdapter.ViewHolder> {
    final ContactsViewManage contactsViewManage;
    final List<String> contactsList;
    final Set<String> contactsSet;
    public GAddAdapter() {
        this.contactsViewManage = ContactsViewManage.getInstance();
        this.contactsList = new ArrayList<>();
        this.contactsSet = new HashSet<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_group,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ContactsView contactsView = contactsViewManage.getContactsView(contactsList.get(position));
        holder.avatar.setImageBitmap(contactsView.getAvatarView());
        holder.nickName.setText(contactsView.getNickName());
        holder.cb_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if ( isChecked ) {
                contactsSet.add(contactsView.getAccount());
            } else {
                contactsSet.remove(contactsView.getAccount());}
        });
    }

    @Override
    public int getItemCount() { return contactsList.size(); }

    public void setContactsStringList(final List<String> contactsStringList) {
        contactsList.clear();
        contactsList.addAll(contactsStringList);
        notifyDataSetChanged();
    }
    
    public List<String> getGroupMemberList() {return new ArrayList<>(contactsSet); }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox cb_check;
        ImageView avatar;
        TextView nickName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_check = itemView.findViewById(R.id.create_group_checkbox);
            avatar = itemView.findViewById(R.id.create_group_person_image);
            nickName = itemView.findViewById(R.id.create_group_person_name);
        }
    }

}
