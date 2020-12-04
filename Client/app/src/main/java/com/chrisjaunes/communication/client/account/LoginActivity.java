package com.chrisjaunes.communication.client.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chrisjaunes.communication.client.MainActivity;
import com.chrisjaunes.communication.client.MyApplication;
import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.utils.UniApiResult;

/**
 * activity about login
 * status ： XXX
 * @author ChrisJaunes
 * @version 1.1
 */
public class LoginActivity extends AppCompatActivity {
    public static final String STR_AUTO_LOGIN_ACCOUNT = "auto_login_account";
    public static final String STR_AUTO_LOGIN_PASSWORD = "auto_login_password";
    public static final String STR_AUTO_LOGIN = "auto_login_check";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // DONE use LoginViewModel to manage login, set account if login successful
        final LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // DONE set view of R.layout.activity_login
        final EditText et_account = findViewById(R.id.et_account);
        final EditText et_password = findViewById(R.id.et_password);
        final Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(v -> {
            String account = et_account.getText().toString();
            String password = et_password.getText().toString();
            if (account.trim().isEmpty() || password.trim().isEmpty()) {
                Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
            } else {
                btn_login.setEnabled(false);
                loginViewModel.login(account, password);
            }
        });
        final Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });
        // DONE set Observe
        loginViewModel.getUniApiResultLiveDate().observe(this, uniApiResult -> {
            Toast.makeText(LoginActivity.this, uniApiResult.data, Toast.LENGTH_SHORT).show();
            Log.i("Login", uniApiResult.status);
            if(uniApiResult instanceof UniApiResult.Fail) {
                Log.e("Login", ((UniApiResult.Fail) uniApiResult).error);
            }
            btn_login.setEnabled(true);
        });
        loginViewModel.getAccountRawLiveData().observe(this, accountRaw -> {
            MyApplication.getInstance().setAccount(accountRaw);
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        // DONE auto login
        String account = getIntent().getStringExtra(STR_AUTO_LOGIN_ACCOUNT);
        String password = getIntent().getStringExtra(STR_AUTO_LOGIN_PASSWORD);
        boolean autoLogin = getIntent().getBooleanExtra(STR_AUTO_LOGIN, false);
        et_account.setText(account);
        et_password.setText(password);
        if(autoLogin) { btn_login.performClick(); }
    }
}