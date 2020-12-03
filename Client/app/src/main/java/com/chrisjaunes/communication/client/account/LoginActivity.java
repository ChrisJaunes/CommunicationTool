package com.chrisjaunes.communication.client.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chrisjaunes.communication.client.BuildConfig;
import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.MainActivity;
import com.chrisjaunes.communication.client.MyApplication;
import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.account.model.AccountRaw;
import com.chrisjaunes.communication.client.utils.UniApiResult;

/**
 * @author ChrisJaunes
 * @version 1
 * @status XXX
 * 管理登录
 */
public class LoginActivity extends AppCompatActivity {
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
                Toast.makeText(getApplicationContext(),"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
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
        //et_account.setText("222");
        //et_password.setText("222");
        //btn_login.performClick();
    }
}