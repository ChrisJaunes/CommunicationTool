package com.chrisjaunes.communication.client.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.talk.TalkActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChrisJaunes
 */
public class NowContactsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_now_contacts, container, false);
        // TODO ContactsViewModel 生命周期为 Activity
        final ContactsViewModel contactsViewModel = new ViewModelProvider(getActivity()).get(ContactsViewModel.class);
        // TODO 下拉刷新控件，用于下拉时请求服务器端
        final TextView swipeText = view.findViewById(R.id.tv_swipe_refresh);
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.layout_swipe_refresh);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeText.setText("正在刷新");
            swipeText.setVisibility(View.VISIBLE);
            contactsViewModel.queryServer();
        });
        // TODO RecycleView控件，更新current Contacts列表，Adapter支持点击跳转
        final List<Contacts> friendList = new ArrayList<>();
        final RecyclerView contactsRecyclerView = view.findViewById(R.id.rv_friend);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        final NowContactsAdapter contactsAdapter = new NowContactsAdapter(friendList, (account) -> {
            Log.d("NowContacts", account);
            Intent intent = new Intent(getActivity(), TalkActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Config.STR_ACCOUNT, account);
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        });
        contactsRecyclerView.setAdapter(contactsAdapter);
        contactsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // TODO contactsViewModel 监听远程服务端的返回结果 监听current contacts list 变化情况
        contactsViewModel.getUniApiResult().observe(getActivity(), uniApiResult -> {
            Log.d("NowContacts", uniApiResult.status + uniApiResult.data);
            Toast.makeText(getActivity(), uniApiResult.status, Toast.LENGTH_SHORT).show();
        });
        contactsViewModel.getNowContactsListResult().observe(getActivity(), contactsList -> {
            Log.d("NowContacts", "contactsList" + contactsList + "size : " + contactsList.size());
            for (Contacts contacts : contactsList) {
                contactsAdapter.addFriendItem(friendList.size(), contacts);
            }
            swipeText.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        });
        // TODO 请求本地缓存数据库
        contactsViewModel.queryLocalNowContactsList();
        return view;
    }
}
