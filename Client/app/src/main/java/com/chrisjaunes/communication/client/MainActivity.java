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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.chrisjaunes.communication.client.account.AccountViewManage;
import com.chrisjaunes.communication.client.account.AccountViewModel;
import com.chrisjaunes.communication.client.account.LoginActivity;
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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static int MENU_AVATAR = 0;
    private final static int MENU_UPDATE_TEXT_STYLE = 1;
    private final static int MENU_LOGOUT = 2;
    private final static int PHOTO_REQUEST_GALLERY = 1;

    private AccountViewModel accountViewModel;
    private final NowContactsFragment nowContactsFragment = new NowContactsFragment();
    private final NewContactsFragment newContactsFragment = new NewContactsFragment();
    private final AddContactsFragment addContactsFragment = new AddContactsFragment();
    private final GListFragment gListFragment = new GListFragment();
    private final GAddFragment gAddFragment = new GAddFragment();
    private final List<Fragment> fragmentList = new ArrayList<>();

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
        // DONE set drawer layout and toolbar
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        final androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.user_operation);
        toolbar.setNavigationOnClickListener(v -> { if (drawerLayout.isOpen()) drawerLayout.close();else drawerLayout.open(); });
        toolbar.setOnMenuItemClickListener(toolbarOnMenuItemClickListener);
        // DONE set navigation
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
            new DialogHelper.logoutDialog(MainActivity.this, R.style.LogoutDialog, ()-> {
                MyApplication.getInstance().logout();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }).show();
            return true;
        });
        // set Bottom RadioGroup
        final RadioGroup radioGroup = findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);
        final RadioButton rb_now_contacts = findViewById(R.id.rb_now_contacts);
        Drawable[] drawables = rb_now_contacts.getCompoundDrawables();
        drawables[1].setBounds(0,0,100,100);
        rb_now_contacts.setCompoundDrawables(drawables[0],drawables[1],drawables[2],drawables[3]);
        final RadioButton newFriendRadioButton = findViewById(R.id.radio_button_newfriend);
        drawables = newFriendRadioButton.getCompoundDrawables();
        drawables[1].setBounds(0,0,100,100);
        newFriendRadioButton.setCompoundDrawables(drawables[0],drawables[1],drawables[2],drawables[3]);
        final RadioButton chatGroupRadioButton = findViewById(R.id.radio_button_chatgroup);
        drawables = chatGroupRadioButton.getCompoundDrawables();
        drawables[1].setBounds(0,0,100,100);
        chatGroupRadioButton.setCompoundDrawables(drawables[0],drawables[1],drawables[2],drawables[3]);
        rb_now_contacts.performClick();
        // DONE modify info
        AccountViewManage.getInstance().getAccountViewLiveData().observe(this, accountView -> {
            final View navigationHeadView = navigationView.getHeaderView(0);
            final ImageView iv_avatar = navigationHeadView.findViewById(R.id.iv_avatar);
            iv_avatar.setImageBitmap(AccountViewManage.getInstance().getAccountView().getAvatarView());
            final TextView tv_nickname = navigationHeadView.findViewById(R.id.tv_nickname);
            tv_nickname.setText(AccountViewManage.getInstance().getAccountView().getNickName());
        });
        // TODO [lwx] need modify, use ThreadPoolExecutor
        new Thread(contactsViewModel::queryLocalNowContactsList).start();
        new Thread(contactsViewModel::queryLocalNewContactsList).start();
        new Thread(contactsViewModel::queryLocalNewContactsList).start();
        new Thread(gListViewModel::queryServer).start();
        // DONE deal fragment
        fragmentList.add(nowContactsFragment);
        fragmentList.add(newContactsFragment);
        fragmentList.add(addContactsFragment);
        fragmentList.add(gListFragment);
        fragmentList.add(gAddFragment);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data == null) return;
            try {
                final Uri uri = data.getData();
                final Bitmap avatar = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                if (null == avatar) {
                    Log.e("MainActivity", " return chos bitmap is null");
                    return;
                }
                accountViewModel.updateAvatar(avatar);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    @SuppressLint("NonConstantResourceId")
    private final Toolbar.OnMenuItemClickListener toolbarOnMenuItemClickListener = item -> {
        final RadioGroup radioGroup = findViewById(R.id.radio_group);
        radioGroup.clearCheck();
        final TextView toolBarTitle = findViewById(R.id.toolbar_title);
        switch (item.getItemId()) {
            case R.id.action_query_new_contacts_list:
                toolBarTitle.setText("新的联系人");
                updateFragment(newContactsFragment);
                break;
            case  R.id.action_add_contacts:
                toolBarTitle.setText("添加联系人");
                updateFragment(addContactsFragment);
                break;
            case  R.id.action_create_group:
                toolBarTitle.setText("创建群聊");
                updateFragment(gAddFragment);
                break;
            default:break;
        }
        return true;
    };

    @SuppressLint("NonConstantResourceId")
    private final RadioGroup.OnCheckedChangeListener radioGroupOnCheckedChangeListener = (group, checkedId) -> {
        final TextView toolBarTitle = findViewById(R.id.toolbar_title);
        switch (checkedId){
            case R.id.rb_now_contacts:
                updateFragment(nowContactsFragment);
                toolBarTitle.setText("我的好友");
                break;
            case R.id.radio_button_newfriend:
                updateFragment(new Fragment());
                toolBarTitle.setText("机器人");
                break;
            case R.id.radio_button_chatgroup:
                updateFragment(gListFragment);
                toolBarTitle.setText("群聊");
                break;
        }
    };

    private void updateFragment(final Fragment fragment) {
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (Fragment tmpFragment : fragmentList) {
            if(tmpFragment.isAdded()) fragmentTransaction.hide(tmpFragment);
        }
        if(!fragment.isAdded()) {
            fragmentTransaction.add(R.id.frame_layout, fragment).show(fragment).addToBackStack(null);
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();

        if(backStackEntryCount > 1) {
            getSupportFragmentManager().popBackStackImmediate();
            updateFragment(nowContactsFragment);
        } else {
            finish();
        }
    }
}
