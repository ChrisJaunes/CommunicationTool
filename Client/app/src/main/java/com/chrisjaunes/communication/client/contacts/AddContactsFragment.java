package com.chrisjaunes.communication.client.contacts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.utils.UniApiResult;

public class AddContactsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_contacts, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View view = getView();
        final ContactsViewModel contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        assert view != null;
        final Button btn_search_account = view.findViewById(R.id.btn_search_account);
        btn_search_account.setOnClickListener(v -> {
            final EditText et_search_account = view.findViewById(R.id.et_search_account);
            final String account2 = et_search_account.getText().toString();
            contactsViewModel.addContacts(account2);
        });
        contactsViewModel.getUniApiResult().observe(getViewLifecycleOwner(), stringUniApiResult -> {
            Log.d("NewContacts[uniApiResult]", stringUniApiResult.status + stringUniApiResult.data);
            if (stringUniApiResult instanceof UniApiResult.Fail)
                Log.e("NewContacts[uniApiResult]", ((UniApiResult.Fail) stringUniApiResult).error);
            Toast.makeText(getActivity(), stringUniApiResult.data, Toast.LENGTH_SHORT).show();
        });
    }
}