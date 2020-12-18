package com.chrisjaunes.communication.client;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.chrisjaunes.communication.client.account.LoginActivity;

public class PreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre);
        SharedPreferences sharedPreferences = getSharedPreferences("login_account", Context.MODE_PRIVATE);
        String login_account = sharedPreferences.getString(LoginActivity.STR_AUTO_LOGIN_ACCOUNT, "111");
        String login_password = sharedPreferences.getString(LoginActivity.STR_AUTO_LOGIN_PASSWORD, "111");
        boolean login_auto = sharedPreferences.getBoolean(LoginActivity.STR_AUTO_LOGIN, false);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(LoginActivity.STR_AUTO_LOGIN_ACCOUNT, login_account);
        intent.putExtra(LoginActivity.STR_AUTO_LOGIN_PASSWORD, login_password);
        intent.putExtra(LoginActivity.STR_AUTO_LOGIN, login_auto);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}