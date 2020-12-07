package com.chrisjaunes.communication.client;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.chrisjaunes.communication.client.account.AccountViewModel;
import com.chrisjaunes.communication.client.account.LoginActivity;
import com.chrisjaunes.communication.client.account.model.AccountViewManage;
import com.chrisjaunes.communication.client.contacts.AddContactsFragment;
import com.chrisjaunes.communication.client.contacts.ContactsViewModel;
import com.chrisjaunes.communication.client.contacts.NewContactsFragment;
import com.chrisjaunes.communication.client.contacts.NowContactsFragment;
import com.chrisjaunes.communication.client.group.GAddFragment;
import com.chrisjaunes.communication.client.group.GListFragment;
import com.chrisjaunes.communication.client.group.GListViewModel;
import com.chrisjaunes.communication.client.utils.DialogHelper;
import com.google.android.material.navigation.NavigationView;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    private final static int MENU_AVATAR = 0;
    private final static int MENU_UPDATE_TEXT_STYLE = 1;
    private final static int MENU_LOGOUT = 2;
    private final static int PHOTO_REQUEST_GALLERY = 1;

    private AccountViewModel accountViewModel;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.getResult().observe(this, stringUniApiResult -> {
            Log.i("MainActivity", stringUniApiResult.status);
            Toast.makeText(MainActivity.this, stringUniApiResult.status, Toast.LENGTH_SHORT).show();
        });
        final ContactsViewModel contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        final GListViewModel gListViewModel = new ViewModelProvider(this).get(GListViewModel.class);

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        final androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.user_operation);
        toolbar.setNavigationOnClickListener(v -> { if (drawerLayout.isOpen()) drawerLayout.close();else drawerLayout.open(); });
        toolbar.setOnMenuItemClickListener(toolbarOnMenuItemClickListener);

        final NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.getMenu().getItem(MENU_AVATAR).setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("*/*");
            intent.addCategory("android.intent.category.DEFAULT");
            startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
            return true;
        });
        navigationView.getMenu().getItem(MENU_UPDATE_TEXT_STYLE).setOnMenuItemClickListener(item -> {
            new DialogHelper.SelectTextStyleColorDialog(MainActivity.this, accountViewModel::updateTextStyle).show();
            return true;
        });
        navigationView.getMenu().getItem(MENU_LOGOUT).setOnMenuItemClickListener(item -> {
            new DialogHelper.logoutDialog(MainActivity.this, R.style.LogoutDialog, () -> {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }).show();
            return true;
        });
        View navigationHeadView = navigationView.getHeaderView(0);
        ImageView iv_avatar = navigationHeadView.findViewById(R.id.iv_avatar);
        iv_avatar.setImageBitmap(AccountViewManage.getInstance().getAccountView().getAvatarView());
        TextView tv_nickname = navigationHeadView.findViewById(R.id.tv_nickname);
        tv_nickname.setText(AccountViewManage.getInstance().getAccountView().getNickName());

        final RadioGroup radioGroup = findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);

        final RadioButton rb_now_contacts = findViewById(R.id.rb_now_contacts);
        Drawable[] drawables = rb_now_contacts.getCompoundDrawables();
        drawables[1].setBounds(0,0,100,100);
        rb_now_contacts.setCompoundDrawables(drawables[0],drawables[1],drawables[2],drawables[3]);
        rb_now_contacts.performClick();

        final RadioButton newFriendRadioButton = findViewById(R.id.radio_button_newfriend);
        drawables = newFriendRadioButton.getCompoundDrawables();
        drawables[1].setBounds(0,0,100,100);
        newFriendRadioButton.setCompoundDrawables(drawables[0],drawables[1],drawables[2],drawables[3]);

        final RadioButton chatGroupRadioButton = findViewById(R.id.radio_button_chatgroup);
        drawables = chatGroupRadioButton.getCompoundDrawables();
        drawables[1].setBounds(0,0,100,100);
        chatGroupRadioButton.setCompoundDrawables(drawables[0],drawables[1],drawables[2],drawables[3]);

        new Thread(contactsViewModel::queryLocalNowContactsList).start();
        new Thread(contactsViewModel::queryLocalNewContactsList).start();
        new Thread(contactsViewModel::queryLocalNewContactsList).start();
        new Thread(gListViewModel::queryServer).start();
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
                    Log.d("Register", " return chos bitmap is null");
                    return;
                }
                accountViewModel.updateAvatar(avatar);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    private final NowContactsFragment nowContactsFragment = new NowContactsFragment();
    private final NewContactsFragment newContactsFragment = new NewContactsFragment();
    private final AddContactsFragment addContactsFragment = new AddContactsFragment();
    private final GAddFragment gAddFragment = new GAddFragment();
    @SuppressLint("NonConstantResourceId")
    private final Toolbar.OnMenuItemClickListener toolbarOnMenuItemClickListener = item -> {
        switch (item.getItemId()) {
            case R.id.action_query_new_contacts_list:
                updateFragment(newContactsFragment);
                break;
            case  R.id.action_add_contacts:
                updateFragment(addContactsFragment);
                break;
            case  R.id.action_create_group:
                updateFragment(gAddFragment);
                break;
            default:break;
        }
        return true;
    };

    private final GListFragment gListFragment = new GListFragment();
    @SuppressLint("NonConstantResourceId")
    private final RadioGroup.OnCheckedChangeListener radioGroupOnCheckedChangeListener = (group, checkedId) -> {
        final TextView toolBarTitle = findViewById(R.id.toolbar_title);
        switch (checkedId){
            case R.id.rb_now_contacts:
                updateFragment(nowContactsFragment);
                toolBarTitle.setText("我的好友");
                break;
            case R.id.radio_button_newfriend:
                //updateFragment(newFriendsFragment);
                toolBarTitle.setText("好友请求");
                break;
            case R.id.radio_button_chatgroup:
                updateFragment(gListFragment);
                toolBarTitle.setText("群聊");
                break;
        }
    };

    private void updateFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}
