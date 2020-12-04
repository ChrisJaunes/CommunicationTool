package com.chrisjaunes.communication.client.account;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.account.model.AccountRaw;
import com.chrisjaunes.communication.client.myView.ChatTextStyleHelper;
import com.chrisjaunes.communication.client.utils.BitmapHelper;
import com.chrisjaunes.communication.client.utils.UniApiResult;

import java.io.FileNotFoundException;
/**
 * activity about register
 * status ： XXX
 * @author ChrisJaunes
 * @version 1.1
 */
public class RegisterActivity extends AppCompatActivity {
    private static final int PHOTO_REQUEST_GALLERY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // DONE use RegisterViewModel to manage register
        final RegisterViewModel registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        // DONE set view of R.layout.activity_register
        final EditText et_account = findViewById(R.id.et_account);
        final EditText et_password = findViewById(R.id.et_password);
        final EditText et_nickname = findViewById(R.id.et_nickname);
        final ImageView iv_avatar = findViewById(R.id.iv_avatar);
        iv_avatar.setImageBitmap(BitmapHelper.AVATAR_DEFAULT);
        iv_avatar.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("*/*");
            intent.addCategory("android.intent.category.DEFAULT");
            startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
        });
        final View layout_select_chat_text_style = findViewById(R.id.layout_select_chat_text_style);
        final ChatTextStyleHelper chatTextStyleHelper = new ChatTextStyleHelper(layout_select_chat_text_style);
        final Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(view -> {
            AccountRaw account = new AccountRaw.Builder()
                    .setAccount(et_account.getText().toString())
                    .setNickname(et_nickname.getText().toString())
                    .setAvatar(BitmapHelper.BitmapToString(
                            ((BitmapDrawable)iv_avatar.getDrawable()).getBitmap()
                    ))
                    .setChatTextStyle(chatTextStyleHelper.getChatTextStyle())
                    .build();
            Log.i("Register", account.toString());
            registerViewModel.register(account, et_password.getText().toString());
        });
        // DONE view model
        registerViewModel.getResult().observe(this, stringUniApiResult -> {
            Toast.makeText(getApplicationContext(),stringUniApiResult.data,Toast.LENGTH_SHORT).show();
            if (stringUniApiResult instanceof UniApiResult.Fail) {
                Log.e("register", ((UniApiResult.Fail) stringUniApiResult).error);
            }
            if (Config.STATUS_REGISTER_SUCCESSFUL.equals(stringUniApiResult.status)) {
                finish();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (null == data) return;
            try {
                Uri uri = data.getData();
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                String bitmapStr = BitmapHelper.BitmapToString(bitmap);
                if (bitmapStr.length() > AccountRaw.LIMIT_AVATAR_LEN) {
                    Toast.makeText(this, "选择头像过大", Toast.LENGTH_SHORT).show();
                    return;
                }
                final ImageView iv_avatar = findViewById(R.id.iv_avatar);
                iv_avatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
