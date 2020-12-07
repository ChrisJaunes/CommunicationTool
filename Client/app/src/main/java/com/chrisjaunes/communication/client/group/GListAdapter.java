package com.chrisjaunes.communication.client.group;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.group.model.GInfo;
import com.chrisjaunes.communication.client.group.model.GInfoView;

import java.util.ArrayList;
import java.util.List;

public class GListAdapter extends RecyclerView.Adapter<GListAdapter.ViewHolder> {
    final List<GInfoView> gInfoViewList;
    final ItemOnClickListener itemOnClickListener;

    public GListAdapter(final ItemOnClickListener itemOnClickListener) {
        this.gInfoViewList = new ArrayList<>();
        this.itemOnClickListener = itemOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GInfoView gInfoView = gInfoViewList.get(position);
        holder.nickName.setText(gInfoView.getGroupName());
        holder.avatar.setImageBitmap(gInfoView.getAvatar());
        holder.itemView.setOnClickListener(v -> itemOnClickListener.goToGroup(gInfoView.getGroup(), gInfoView.getGroupName()));
    }

    @Override
    public int getItemCount() { return gInfoViewList.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView nickName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.iv_group_avatar);
            nickName = itemView.findViewById(R.id.iv_group_nickname);
        }
    }

    public void addGInfoList(final List<GInfo> gInfoList) {
        gInfoViewList.clear();
        for(GInfo g : gInfoList) {
            gInfoViewList.add(new GInfoView(g));
            notifyItemChanged(gInfoViewList.size());
        }
    }

    public interface ItemOnClickListener {
        void goToGroup(int group, String group_name);
    }
}