package com.chrisjaunes.communication.client.talk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chrisjaunes.communication.client.MyApplication;
import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.contacts.ContactsView;

import java.util.ArrayList;
import java.util.List;

public class TalkActivity extends AppCompatActivity {
    static final public String STR_CONTACTS_ACCOUNT = "contacts_account";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        Bundle intentBundle = getIntent().getExtras();
        String contacts_account = intentBundle.getString(STR_CONTACTS_ACCOUNT);
        // DONE TalkViewModel : lifeOwner this
        final TalkViewModel talkViewModel = new ViewModelProvider(this, new TalkViewModel.Factory(contacts_account)).get(TalkViewModel.class);
        // DONE toolbar : set toolbar(back、menu、title)
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.inflateMenu(R.menu.talk_message);
        toolbar.setOnMenuItemClickListener(toolbarOnMenuItemClickListener);
        // DONE SwipeRefreshLayout : queryServer when swipe refresh
        final TextView tvSwipeRefresh = findViewById(R.id.tv_swipe_refresh);
        final SwipeRefreshLayout layoutSwipeRefresh = findViewById(R.id.layout_swipe_refresh);
        layoutSwipeRefresh.setEnabled(true);
        layoutSwipeRefresh.setOnRefreshListener(() -> {
            tvSwipeRefresh.setText("正在刷新");
            tvSwipeRefresh.setVisibility(View.VISIBLE);
            talkViewModel.queryServer();
        });
        // DONE RecycleView : init messageList、set recycleView recycleViewAdapter
        final List<TMessage> messageList = new ArrayList<>();
        final RecyclerView rvTalkMessage = findViewById(R.id.rv_talk_message);
        rvTalkMessage.setLayoutManager(new LinearLayoutManager(this));
        final TalkAdapter talkAdapter = new TalkAdapter(messageList);
        rvTalkMessage.setAdapter(talkAdapter);
        rvTalkMessage.setItemAnimator(new DefaultItemAnimator());
        // DONE button : set click listener
        final TextView tvSendMessage= findViewById(R.id.tv_send_message);
        final Button btnSendMessage = findViewById(R.id.btn_send_message);
        btnSendMessage.setOnClickListener(v -> {
            tvSendMessage.setEnabled(false);
            btnSendMessage.setEnabled(false);
            talkViewModel.updateMessage(TMessageFactory.STR_CONTENT_TYPE_TEXT, tvSendMessage.getText().toString());
        });
        // DONE viewModel : set Observe and queryLocalDataBase scrollToEnd
        talkViewModel.getUniApiResult().observe(this, stringUniApiResult -> {
            Log.v("talk", stringUniApiResult.status + stringUniApiResult.data);
            Toast.makeText(TalkActivity.this, stringUniApiResult.status, Toast.LENGTH_SHORT).show();
            tvSwipeRefresh.setVisibility(View.GONE);
            layoutSwipeRefresh.setRefreshing(false);
            tvSendMessage.setEnabled(true);
            btnSendMessage.setEnabled(true);
        });
        talkViewModel.getTMessageList().observe(this, TMessageList -> {
            Log.d("Talk", "tMessageList : " + TMessageList + " size : " + TMessageList.size());
            talkAdapter.addMessageList(TMessageList);
            if(messageList.size() != 0) {rvTalkMessage.scrollToPosition(messageList.size() - 1);}
        });
        talkViewModel.queryLocalMessageList();
        // TODO getContactsViewLiveData
        final MutableLiveData<ContactsView> contactsViewLiveData = new MutableLiveData<>();
        new Thread(()-> contactsViewLiveData.postValue(
                new ContactsView(MyApplication.getInstance().getLocalDataBase().getContactsDao().queryContactsByAccountID(contacts_account))
        )).start();
        contactsViewLiveData.observe(this, contactsView -> {
            final TextView toolBarTitle = findViewById(R.id.toolbar_title);
            toolBarTitle.setText(contactsView.getNickName());
            talkAdapter.contactsView = contactsView;
            talkAdapter.notifyDataSetChanged();
        });
    }
    @SuppressLint("NonConstantResourceId")
    private final Toolbar.OnMenuItemClickListener toolbarOnMenuItemClickListener = item -> {
        switch (item.getItemId()){
            case  R.id.action_dial:
                Log.i("TalkActivity", "dial");
                Toast.makeText(getApplicationContext(),"add",Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
        return true;
    };
}