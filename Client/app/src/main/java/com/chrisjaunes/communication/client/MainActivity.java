package com.chrisjaunes.communication.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chrisjaunes.communication.client.account.AccountViewModel;
import com.chrisjaunes.communication.client.contacts.ContactsViewModel;
import com.chrisjaunes.communication.client.contacts.NowContactsFragment;
import com.chrisjaunes.communication.client.group.GListFragment;
import com.chrisjaunes.communication.client.utils.DialogHelper;
import com.google.android.material.navigation.NavigationView;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    private final static int MENU_AVATAR = 0;
    private final static int MENU_UPDATE_TEXT_STYLE = 1;
    private final static int MENU_LOGOUT = 2;
    private final static int PHOTO_REQUEST_GALLERY = 1;

    private AccountViewModel accountViewModel;
    private ContactsViewModel contactsViewModel;
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
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);

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
            new DialogHelper.logoutDialog(MainActivity.this, R.style.LogoutDialog, accountViewModel::logout).show();
            return true;
        });

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

    @SuppressLint("NonConstantResourceId")
    private final Toolbar.OnMenuItemClickListener toolbarOnMenuItemClickListener = item -> {
        switch (item.getItemId()){
            case  R.id.action_add_friend:
                Log.i("MainActivity", "add friend");
                Toast.makeText(getApplicationContext(),"add",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.action_create_group:
                Toast.makeText(getApplicationContext(),"create",Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
        return true;
    };

    private final NowContactsFragment nowContactsFragment = new NowContactsFragment();
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
