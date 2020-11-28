package com.chrisjaunes.communication.client.talk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrisjaunes.communication.client.R;

import org.json.JSONException;

public class TalkActivity extends AppCompatActivity {
    // view
    private TextView editText;
    private TextView swipeFreshText;
    private ImageView backView;
    private TextView titleText;

    // data
    private String TalkerUserId;
    private String TalkerUserName;
    private Bitmap TalkerBitmap;

    private String UserId;
    private String UserName;
    private Bitmap UserBitmap;

//    private List<Messages> messagesList;

    // message recyclerview
    private RecyclerView messageRecyclerView;
    private MessageAdapter messageAdapter;
    private LinearLayoutManager linearLayoutManager;

    // swipe fresh
    private SwipeRefreshLayout swipeRefreshLayout;

    // viewmodel
    TalkViewModel talkActivityViewModel;
    // textStyle
    String TalkerTextStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.talk_activity_title);
        // TODO 下拉刷新
        final TextView swipeText = findViewById(R.id.tv_swipe_refresh);
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.layout_swipe_refresh);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeText.setText("正在刷新");
            swipeText.setVisibility(View.VISIBLE);
            //contactsViewModel.queryServer();
        });
        // TODO 发送文本消息
        final Button btn_send = findViewById(R.id.btn_send_message);
        btn_send.setOnClickListener(v -> {

//                String content = editText.getText().toString();
//                //Messages newMessages = new Messages(UserId,TalkerUserId,MyApplication.getMyApplicationInstance().getNowTime(),content);
//                // 插入是加入recyclerview，并且加入数据库，并且加入服务器
//                talkActivityViewModel.addNewMessage(newMessages);
//                messageAdapter.addMessageItem(messagesList.size(), newMessages);
//                messageRecyclerView.scrollToPosition(messagesList.size()-1);
//                editText.setText("");
        });
//        editText = findViewById(R.id.message_content);
//        swipeFreshText = findViewById(R.id.swipefresh_text);
////
//        Bundle bundle = getIntent().getExtras();
//        TalkerUserId = bundle.getString("talkerUserId");
//        TalkerUserName = bundle.getString("talkerUserName");
//        TalkerTextStyle = bundle.getString("talkerTextStyle");
//        TalkerBitmap = MyApplication.getMyApplicationInstance().TalkerHeadPhoto;
//
//        UserId = MyApplication.getMyApplicationInstance().UserId;
//        UserName = MyApplication.getMyApplicationInstance().UserName;
//        UserBitmap = MyApplication.getMyApplicationInstance().HeadPhoto;
//
//        // 标题
//        backView = findViewById(R.id.talk_activity_back_view);
//        backView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        titleText = findViewById(R.id.title_text);
//        titleText.setText(TalkerUserName);
//
//        // messagelist
//        messagesList = new ArrayList<>();
//
//        // 流程：加载本地数据，加载完后加载recyclerview，initrecyclerview在接受到local数据之后再加载
//        initViewModel();
//        //加载本地数据
//        talkActivityViewModel.queryLocalMessage();
//        initSwipeFresh();
    }

    private void initSendMessageButton(){

    }

    private void initMessageRecyclerView() throws JSONException {
//        messageRecyclerView = findViewById(R.id.message_recyclerview);
//        // linelayout manager
//        linearLayoutManager = new LinearLayoutManager(this);
//        messageRecyclerView.setLayoutManager(linearLayoutManager);
//        // adapter
//        messageAdapter = new MessageAdapter(messagesList,UserId,TalkerUserId,TalkerUserName,UserName,UserBitmap,TalkerBitmap,TalkerTextStyle);
//        messageRecyclerView.setAdapter(messageAdapter);
//
//        messageRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        // 滑倒最后位置
//        messageRecyclerView.scrollToPosition(messagesList.size()-1);
    }

    // viewmodel
    private void initViewModel(){
//        talkActivityViewModel = new ViewModelProvider(this).get(TalkActivityViewModel.class);
//        talkActivityViewModel.setInfo(UserId,TalkerUserId,getApplicationContext());
//        // obser
//        talkActivityViewModel.getLocalMessageSingal().observe(this, new Observer<List<Messages>>() {
//            @Override
//            public void onChanged(List<Messages> messages) {
//                if( messages != null){
//                    messagesList = messages;
//                }
//                try {
//                    initMessageRecyclerView();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                initSendMessageButton();
//            }
//        });
//
//        talkActivityViewModel.getServeMessageSingal().observe(this, new Observer<List<Messages>>() {
//            @Override
//            public void onChanged(List<Messages> messages) {
//                for(int i = 0;i<messages.size();++i){
//                    messageAdapter.addMessageItem(messagesList.size(),messages.get(i));
//                }
//                // 滑动到最后
//                if( messagesList.size() != 0) messageRecyclerView.scrollToPosition(messagesList.size()-1);
//                swipeRefreshLayout.setRefreshing(false);
//                Toast.makeText(getApplicationContext(),"刷新成功",Toast.LENGTH_SHORT).show();
//                swipeFreshText.setVisibility(View.GONE);
//            }
//        });
    }
}