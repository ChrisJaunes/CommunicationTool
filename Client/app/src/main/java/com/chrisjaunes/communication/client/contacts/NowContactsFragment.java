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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.talk.TalkActivity;
import com.chrisjaunes.communication.client.utils.UniApiResult;

import java.util.ArrayList;
import java.util.List;

public class NowContactsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_now_contacts, container, false);

        final ContactsViewModel contactsViewModel = new ViewModelProvider(getActivity()).get(ContactsViewModel.class);

        final TextView swipeText = view.findViewById(R.id.tv_swipe_refresh);
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.layout_swipe_refresh);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeText.setText("正在刷新");
            swipeText.setVisibility(View.VISIBLE);
            contactsViewModel.queryServer();
        });

        final List<Contacts> friendList = new ArrayList<>();
        final RecyclerView contactsRecyclerView = view.findViewById(R.id.rv_friend);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        contactsRecyclerView.setLayoutManager(linearLayoutManager);
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

        contactsViewModel.getUniApiResult().observe(getActivity(), uniApiResult -> {
            Toast.makeText(getActivity(), uniApiResult.status, Toast.LENGTH_SHORT).show();
            if (uniApiResult.data instanceof List) {
                List contactsList = (List) uniApiResult.data;
                for (int i = 0; i < contactsList.size(); i++) {
                    Contacts contacts = (Contacts) contactsList.get(i);
                    contactsAdapter.addFriendItem(friendList.size(), contacts);
                }
            }
            swipeText.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        });

        return view;
    }

    // init viewmodel
    private void initViewModel() {
//        Log.i("NowFriendsFragment" , "initViewModel");
//        friendShipViewModel = new ViewModelProvider(this).get(FriendShipViewModel.class);
//        friendShipViewModel.setInfo(getApplicationContext(),((MyApplication)getApplication()).UserId);
////        Log.d("NowFriendsFragment",((MyApplication)getApplication()).UserId);
//        // bind 获取所有friend
//        friendShipViewModel.getLocalFriendsListSingal().observe(getViewLifecycleOwner(), new Observer<List<FriendShip>>() {
//            @Override
//            public void onChanged(List<FriendShip> friendShipList) {
//                for(int i = 0; i< friendShipList.size(); ++i){
//                    FriendShip friendShip = friendShipList.get(i);
//                    friendAdapter.addFriendItem(friendList.size(),friendShip);
//                }
//            }
//        });
//
//        friendShipViewModel.getServeFriendsListSingal().observe(getViewLifecycleOwner(), new Observer<List<FriendShip>>() {
//            @Override
//            public void onChanged(List<FriendShip> reFriendShipList) {
//                for(int i = 0; i< reFriendShipList.size(); ++i){
//                    FriendShip friendShip = reFriendShipList.get(i);
//                    friendAdapter.addFriendItem(friendList.size(),friendShip);
//                }
//                swipeText.setVisibility(View.GONE);
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//
//        friendShipViewModel.queryLocalFriends();
//    }
    }
}
