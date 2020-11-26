package com.chrisjaunes.communication.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chrisjaunes.communication.client.account.LoginActivity;
import com.chrisjaunes.communication.client.account.UpdateMessageViewModel;
import com.chrisjaunes.communication.client.utils.UniApiResult;
import com.google.android.material.navigation.NavigationView;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UpdateMessageViewModel updateInfoViewModel = new ViewModelProvider(this).get(UpdateMessageViewModel.class);
        updateInfoViewModel.getResult().observe(this, stringUniApiResult -> {
            Toast.makeText(this, stringUniApiResult.status, Toast.LENGTH_SHORT).show();
        });
    }
}