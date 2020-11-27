package com.chrisjaunes.communication.client.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.utils.BitmapHelper;

import java.util.List;

public class NowContactsAdapter extends RecyclerView.Adapter<NowContactsAdapter.NowContactsViewHolder> {
    public interface ItemOnClickListener {
        void goToTalk(String account);
    }
    List<Contacts> friendList;
    ItemOnClickListener itemOnClickListener;
    public NowContactsAdapter(List<Contacts> new_friendList, ItemOnClickListener itemOnClickListener) {
        this.friendList = new_friendList;
        this.itemOnClickListener = itemOnClickListener;
    }

    @NonNull
    @Override
    public NowContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_item,parent,false);
        return new NowContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NowContactsViewHolder holder, int position) {
        final Contacts contacts = friendList.get(position);
        holder.nickName.setText(contacts.getNickname());
        holder.avatar.setImageBitmap(BitmapHelper.StringToBitmap(contacts.getAvatar()));
        holder.itemView.setOnClickListener(v -> itemOnClickListener.goToTalk(contacts.getAccount()));
    }

    @Override
    public int getItemCount() { return friendList.size(); }

    public static class NowContactsViewHolder extends RecyclerView.ViewHolder{
        TextView nickName;
        ImageView avatar;
        public NowContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            nickName = itemView.findViewById(R.id.friendUerName);
            avatar = itemView.findViewById(R.id.friend_image);
        }
    }

    public void addFriendItem(int pos, Contacts friendShip){
        friendList.add(pos, friendShip);
        notifyItemChanged(pos);
    }
}
