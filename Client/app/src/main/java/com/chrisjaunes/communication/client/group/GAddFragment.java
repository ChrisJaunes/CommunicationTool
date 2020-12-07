package com.chrisjaunes.communication.client.group;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.contacts.ContactsViewModel;
import com.chrisjaunes.communication.client.utils.UniApiResult;

import java.util.List;

public class GAddFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_group, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View view = getView();
        final GAddViewModel gAddViewModel = new ViewModelProvider(this).get(GAddViewModel.class);
        final ContactsViewModel contactsViewModel = new ViewModelProvider(getActivity()).get(ContactsViewModel.class);
        // TODO
        final TextView createGroupName = view.findViewById(R.id.create_group_name);
        final Button confirmButton = view.findViewById(R.id.create_group_confirm_button);
        final RecyclerView recyclerView = view.findViewById(R.id.create_group_person_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final GAddAdapter addGroupAdapter = new GAddAdapter();
        recyclerView.setAdapter(addGroupAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // TODO 初始化完列表才设置confirm 函数
        confirmButton.setOnClickListener(v -> {
            final String groupName = createGroupName.getText().toString();
            if (groupName.length() == 0){
                Toast.makeText(getActivity(),"群名不能为空",Toast.LENGTH_SHORT).show();
            } else{
                List<String> groupMemberList = addGroupAdapter.getGroupMemberList();
                gAddViewModel.addGroup(groupName, groupMemberList);
            }
        });
        gAddViewModel.getUniApiResult().observe(getViewLifecycleOwner(), stringUniApiResult -> {
            Toast.makeText(getActivity(),stringUniApiResult.data,Toast.LENGTH_SHORT).show();
            if (stringUniApiResult instanceof UniApiResult.Fail)
                Log.e("NowContacts[uniApiResult]", ((UniApiResult.Fail) stringUniApiResult).error);
        });
        contactsViewModel.getNowContactsListResult().observe(getViewLifecycleOwner(), stringContactsList -> {
            Log.d("NowContacts[nowContactsListResult]", "contactsList" + stringContactsList + " size : " + stringContactsList.size());
            addGroupAdapter.setContactsStringList(stringContactsList);
        });
    }
}