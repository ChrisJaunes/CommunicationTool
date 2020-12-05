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

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.talk.TalkActivity;
import com.chrisjaunes.communication.client.utils.UniApiResult;

/**
 * @author ChrisJaunes
 */
public class NowContactsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_now_contacts, container, false);
        // DONE ContactsViewModel 生命周期为 Activity
        final ContactsViewModel contactsViewModel = new ViewModelProvider(getActivity()).get(ContactsViewModel.class);
        // DONE 下拉刷新控件，用于下拉时请求服务器端
        final TextView tvSwipeRefresh = view.findViewById(R.id.tv_swipe_refresh);
        final SwipeRefreshLayout layoutSwipeRefresh = view.findViewById(R.id.layout_swipe_refresh);
        layoutSwipeRefresh.setEnabled(true);
        layoutSwipeRefresh.setOnRefreshListener(() -> {
            tvSwipeRefresh.setText("正在刷新");
            tvSwipeRefresh.setVisibility(View.VISIBLE);
            contactsViewModel.queryServer();
        });
        // DONE RecycleView控件，更新current Contacts列表，Adapter支持点击跳转
        final RecyclerView rvContacts = view.findViewById(R.id.rv_contacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(getActivity()));
        final NowContactsAdapter contactsAdapter = new NowContactsAdapter((account) -> {
            Log.d("NowContacts[interface]", account);
            Intent intent = new Intent(getActivity(), TalkActivity.class);
            intent.putExtra(TalkActivity.STR_CONTACTS_ACCOUNT, account);
            getActivity().startActivity(intent);
        });
        rvContacts.setAdapter(contactsAdapter);
        rvContacts.setItemAnimator(new DefaultItemAnimator());
        // DONE contactsViewModel 监听本地或远程服务端的返回结果 监听current contacts list 变化情况
        contactsViewModel.getUniApiResult().observe(getViewLifecycleOwner(), uniApiResult -> {
            Log.d("NowContacts[uniApiResult]", uniApiResult.status + uniApiResult.data);
            if(uniApiResult instanceof UniApiResult.Fail) Log.e("NowContacts[uniApiResult]", ((UniApiResult.Fail) uniApiResult).error);
            Toast.makeText(getActivity(), uniApiResult.data, Toast.LENGTH_SHORT).show();
            tvSwipeRefresh.setVisibility(View.GONE);
            layoutSwipeRefresh.setRefreshing(false);
        });
        contactsViewModel.getNowContactsListResult().observe(getViewLifecycleOwner(), stringContactsList -> {
            Log.d("NowContacts[nowContactsListResult]", "contactsList" + stringContactsList + " size : " + stringContactsList.size());
            contactsAdapter.setContactsStringList(stringContactsList);
        });
        return view;
    }
}
