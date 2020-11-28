package com.chrisjaunes.communication.client.talk;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chrisjaunes.communication.client.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MessageAdapter extends  RecyclerView.Adapter<MessageAdapter.MyViewHolder>{
    //private List<Messages> messagesList;
    private String UserId;
    private String UserName;
    private String TalkerUserId;
    private String TalkerUserName;
    private Bitmap userBitmap;
    private Bitmap talkUserBitmap;
    private int TalkerTextColor;
    private int TalkerBorderColor;
    private int TalkerBackgroundColor;
    private int UserTextColor;
    private int UserBorderColor;
    private int UserBackgroundColor;

//    // headphoto
//    //private UpdateInfoViewModel updateInfoViewModel;
//    public MessageAdapter(List<Messages> myMessagesList, final String UserId, String talkerUserId,
//                          String TalkerUserName, String UserName,
//                          Bitmap userBitmap , Bitmap talkUserBitmap,String talkerTextStyle) throws JSONException {
//        //updateInfoViewModel = new UpdateInfoViewModel();
//        //messagesList = myMessagesList;
//        this.UserId = UserId;
//        this.TalkerUserId = talkerUserId;
//        this.UserName = UserName;
//        this.TalkerUserName = TalkerUserName;
//        this.userBitmap  = userBitmap;
//        this.talkUserBitmap = talkUserBitmap;
//        Log.d("MessageAdapter","talker text style:" + talkerTextStyle);
//        JSONObject textStyleJson = new JSONObject(talkerTextStyle);
//        this.TalkerTextColor = Color.parseColor(textStyleJson.getString("ChatTextColor"));
//        this.TalkerBorderColor = Color.parseColor(textStyleJson.getString("ChatBorderColor"));
//        this.TalkerBackgroundColor = Color.parseColor(textStyleJson.getString("ChatBackgroundColor"));
//
//        //UserTextColor = MyApplication.getMyApplicationInstance().ChatTextColor;
//        //UserBackgroundColor = MyApplication.getMyApplicationInstance().ChatBackgroundColor;
//        //UserBorderColor = MyApplication.getMyApplicationInstance().ChatBorderColor;
//    }

    // 更新好友的textstyle
    public void updateTalkerInfo(String talkerTextStyle) throws JSONException {
        JSONObject textStyleJson = new JSONObject(talkerTextStyle);
        this.TalkerTextColor = Color.parseColor(textStyleJson.getString("ChatTextColor"));
        this.TalkerBorderColor = Color.parseColor(textStyleJson.getString("ChatBorderColor"));
        this.TalkerBackgroundColor = Color.parseColor(textStyleJson.getString("ChatBackgroundColor"));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView time;

       // private ChatTextView message_left;
        private TextView talkerUsername_left;
        private ImageView talkerImage_left;

        //private ChatTextView message_right;
        private TextView talkerUsername_right;
        private ImageView talkerImage_right;

        private LinearLayout leftLayout;
        private LinearLayout rightLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            time = (TextView)itemView.findViewById(R.id.message_time);
//            talkerImage_left = (ImageView)itemView.findViewById(R.id.talker_left_image);
//            talkerImage_right = (ImageView)itemView.findViewById(R.id.talker_right_image);
//            leftLayout = (LinearLayout) itemView.findViewById(R.id.message_left_layout);
//            rightLayout = (LinearLayout) itemView.findViewById(R.id.message_right_layout);
//            talkerUsername_left = (TextView) itemView.findViewById(R.id.talker_left_usrname);
//            talkerUsername_right = (TextView) itemView.findViewById(R.id.talker_right_username);
//
//            //message_left = itemView.findViewById(R.id.message_left_content);
//            //message_right = itemView.findViewById(R.id.message_right_content);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Messages messages = messagesList.get(position);
//        String time = messages.getSendTime();
//        String userId = messages.getFromUserId();
//        String messageContent = messages.getContent();
//        // set view
//        // send time
//        holder.time.setText(time);
//        if(userId.equals(UserId)){
//            Log.d("MessageAdapter","xbx ??? ");
//            // 发送方是登录方 右面
//            // 左面隐藏
//            holder.leftLayout.setVisibility(View.GONE);
//            // 右面显示
//            holder.rightLayout.setVisibility(View.VISIBLE);
//            // 设置view信息 是登录方
//            holder.talkerImage_right.setImageBitmap(userBitmap);
//
//            Log.d("MessageAdapter","setText right" + messageContent);
//            holder.message_right.setMyText(messageContent);
//
////            holder.message_right.updateWidthAndHeight();
//
//            holder.talkerUsername_right.setText(UserName);
//            Log.d("MessageAdapter","hjj ??? ");
//
//            // 设置颜色
//            holder.message_right.setMyColor(UserTextColor,UserBorderColor,UserBackgroundColor);
//        }
//        else{
//            // 发送方是对面方 左面
//            // 右面隐藏
//            holder.rightLayout.setVisibility(View.GONE);
//            // 左面显示
//            holder.leftLayout.setVisibility(View.VISIBLE);
//            // 设置view信息 是对面方
//            holder.talkerImage_left.setImageBitmap(talkUserBitmap);
//
//            Log.d("MessageAdapter","setText left" + messageContent);
//            holder.message_left.setMyText(messageContent);
//
//            holder.talkerUsername_left.setText(TalkerUserName);
//
//            // 设置颜色
//            holder.message_left.setMyColor(TalkerTextColor,TalkerBorderColor,TalkerBackgroundColor);
//        }
    }

//    public void addMessageItem(int position, Messages messages){
//        messagesList.add(position, messages);
//        notifyItemChanged(position);
//    }

    @Override
    public int getItemCount() {
        return 0;//messagesList.size();
    }
}
