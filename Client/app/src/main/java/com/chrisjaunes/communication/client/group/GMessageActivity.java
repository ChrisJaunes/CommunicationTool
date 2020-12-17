package com.chrisjaunes.communication.client.group;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.group.model.GMessage;
import com.chrisjaunes.communication.client.group.model.GroupConfig;
import com.chrisjaunes.communication.client.utils.UniApiResult;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GMessageActivity extends AppCompatActivity {
    static final private int PHOTO_REQUEST_GALLERY = 1;
    GMessageViewModel groupViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_message);
        Bundle intentBundle = getIntent().getExtras();
        int group = intentBundle.getInt(GroupConfig.STR_GROUP);
        String groupName = intentBundle.getString(GroupConfig.STR_GROUP_NAME);
        // DONE TalkViewModel : lifeOwner this
        groupViewModel = new ViewModelProvider(this, new GMessageViewModel.Factory(group)).get(GMessageViewModel.class);
        // TODO toolbar : set toolbar(back、menu、title)
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        final TextView toolBarTitle = findViewById(R.id.toolbar_title);
        toolBarTitle.setText(groupName);
//        toolbar.inflateMenu(R.menu.group_message);
//        toolbar.setOnMenuItemClickListener(toolbarOnMenuItemClickListener);
        // DONE SwipeRefreshLayout : queryServer when swipe refresh
        final TextView tvSwipeRefresh = findViewById(R.id.tv_swipe_refresh);
        final SwipeRefreshLayout layoutSwipeRefresh = findViewById(R.id.layout_swipe_refresh);
        layoutSwipeRefresh.setEnabled(true);
        layoutSwipeRefresh.setOnRefreshListener(() -> {
            tvSwipeRefresh.setText("正在刷新");
            tvSwipeRefresh.setVisibility(View.VISIBLE);
            groupViewModel.queryServerMessageList();
        });
        // DONE RecycleView : init messageList、set recycleView recycleViewAdapter
        final List<GMessage> messageList = new ArrayList<>();
        final RecyclerView rvMessage = findViewById(R.id.rv_message);
        rvMessage.setLayoutManager(new LinearLayoutManager(this));
        final GMessageAdapter messageAdapter = new GMessageAdapter(messageList);
        rvMessage.setAdapter(messageAdapter);
        rvMessage.setItemAnimator(new DefaultItemAnimator());
        // TODO button : set click listener
        final TextView tvSendText= findViewById(R.id.tv_send_text);
        final Button btnSendText = findViewById(R.id.btn_send_text);
        btnSendText.setOnClickListener(v -> {
            tvSendText.setEnabled(false);
            btnSendText.setEnabled(false);
            groupViewModel.addMessage(GroupConfig.STR_CONTENT_TYPE_TEXT, tvSendText.getText().toString());
        });
        final Button btnSendImg = findViewById(R.id.btn_send_img);
        btnSendImg.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("*/*");
            intent.addCategory("android.intent.category.DEFAULT");
            startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
        });
        // DONE viewModel : set Observe and queryLocalDataBase scrollToEnd
        groupViewModel.getUniApiResult().observe(this, stringUniApiResult -> {
            Log.v("Group", stringUniApiResult.status + stringUniApiResult.data);
            Toast.makeText(GMessageActivity.this, stringUniApiResult.data, Toast.LENGTH_SHORT).show();
            if (stringUniApiResult instanceof UniApiResult.Fail)
                Log.e("Group[uniApiResult]", ((UniApiResult.Fail) stringUniApiResult).error);
            tvSwipeRefresh.setVisibility(View.GONE);
            layoutSwipeRefresh.setRefreshing(false);
            tvSendText.setEnabled(true);
            btnSendText.setEnabled(true);
            tvSendText.setText("");
        });
        groupViewModel.getGMessageList().observe(this, GMessageList -> {
            Log.d("Group", "gMessageList : " + GMessageList + " size : " + GMessageList.size());
            messageAdapter.addMessageList(GMessageList);
            if(messageList.size() != 0) {rvMessage.scrollToPosition(messageList.size() - 1);}
        });
        groupViewModel.queryLocalMessageList();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data == null) return;
            try {
                Uri uri = data.getData();
                Bitmap avatar = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                if (null == avatar) {
                    Log.d("Talk", " return chose bitmap is null");
                }
//                groupViewModel.addMessage(GroupViewModel.STR_CONTENT_TYPE_IMG, BitmapHelper.BitmapToString(avatar));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}