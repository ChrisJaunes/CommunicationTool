package com.chrisjaunes.communication.client.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chrisjaunes.communication.client.BuildConfig;
import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.MainActivity;
import com.chrisjaunes.communication.client.MyApplication;
import com.chrisjaunes.communication.client.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getResult().observe(this, uniApiResult -> {
            Toast.makeText(LoginActivity.this, uniApiResult.status, Toast.LENGTH_SHORT).show();
            if(Config.STATUS_LOGIN_SUCCESSFUL.equals(uniApiResult.status)) {
                if (BuildConfig.DEBUG && !(uniApiResult.data instanceof Account)) {
                    throw new AssertionError("Assertion failed");
                }
                MyApplication.getInstance().setAccount((Account) uniApiResult.data);

                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        final EditText et_account = findViewById(R.id.et_account);
        final EditText et_password = findViewById(R.id.et_password);
        final Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(v -> {
            String account = et_account.getText().toString();
            String password = et_password.getText().toString();
            if (account.trim().isEmpty() || password.trim().isEmpty()) {
                Toast.makeText(getApplicationContext(),"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
            } else {
                loginViewModel.login(account, password);
            }
        });

        final Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });

        et_account.setText("111");
        et_password.setText("111");
        btn_login.performClick();
    }

    @Override
    public void onBackPressed() { }
}