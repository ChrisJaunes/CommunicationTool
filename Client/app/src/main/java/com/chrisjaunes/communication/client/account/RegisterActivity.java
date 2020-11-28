package com.chrisjaunes.communication.client.account;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.chrisjaunes.communication.client.Config;
import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.utils.BitmapHelper;
import com.chrisjaunes.communication.client.utils.ColorTrHelper;

import java.io.FileNotFoundException;

public class RegisterActivity extends AppCompatActivity {
    private static final int PHOTO_REQUEST_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final RegisterViewModel registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.getResult().observe(this, stringUniApiResult -> {
            Toast.makeText(getApplicationContext(),stringUniApiResult.status,Toast.LENGTH_SHORT).show();
            if (Config.STATUS_REGISTER_SUCCESSFUL.equals(stringUniApiResult.status)) finish();
        });

        final EditText et_account = findViewById(R.id.et_account);
        final EditText et_password = findViewById(R.id.et_password);
        final EditText et_nickname = findViewById(R.id.et_nickname);

        final ImageView iv_avatar = findViewById(R.id.iv_avatar);
        iv_avatar.setImageResource(R.drawable.avatar_default);
        iv_avatar.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("*/*");
            intent.addCategory("android.intent.category.DEFAULT");
            startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
        });

        final View vi_font_color = findViewById(R.id.vi_font_color);
        final Spinner sp_font_color = findViewById(R.id.sp_font_color);
        sp_font_color.setAdapter(ColorTrHelper.ColorSelectedAdapter.getColorAdapter(this));
        sp_font_color.setOnItemSelectedListener(new ColorTrHelper.ColorSelectedListener(vi_font_color));

        final View vi_bubble_color = findViewById(R.id.vi_bubble_color);
        final Spinner sp_bubble_color = findViewById(R.id.sp_bubble_color);
        sp_bubble_color.setAdapter(ColorTrHelper.ColorSelectedAdapter.getColorAdapter(this));
        sp_bubble_color.setOnItemSelectedListener(new ColorTrHelper.ColorSelectedListener(vi_bubble_color));

        final View vi_border_color = findViewById(R.id.vi_border_color);
        final Spinner sp_border_color = findViewById(R.id.sp_border_color);
        sp_border_color.setAdapter(ColorTrHelper.ColorSelectedAdapter.getColorAdapter(this));
        sp_border_color.setOnItemSelectedListener(new ColorTrHelper.ColorSelectedListener(vi_border_color));

        final Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(view -> {
            AccountInfo account = new AccountInfo.Builder()
                    .addAccount(et_account.getText().toString())
                    .addNickname(et_nickname.getText().toString())
                    .addAvatar(BitmapHelper.BitmapToString(
                            ((BitmapDrawable)iv_avatar.getDrawable()).getBitmap()
                    ))
                    .addChatFontColor(
                            ColorTrHelper.colorToString(((ColorDrawable) vi_font_color.getBackground()).getColor())
                    )
                    .addChatBubbleColor(
                            ColorTrHelper.colorToString(((ColorDrawable) vi_bubble_color.getBackground()).getColor())
                    )
                    .addChatBorderColor(
                            ColorTrHelper.colorToString(((ColorDrawable) vi_border_color.getBackground()).getColor())
                    )
                    .build();
            Log.i("Register", account.toString());
            registerViewModel.register(account, et_password.getText().toString());
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (null != data) return;
            try {
                Uri uri = data.getData();
                Bitmap chosBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                if (null == chosBitmap) {
                    Log.d("Register", " return chos bitmap is null");
                    return;
                }
                String chosBitmapStr = BitmapHelper.BitmapToString(chosBitmap);
                if (chosBitmapStr.length() > Config.LIMIT_AVATAR_LEN) {
                    Toast.makeText(this, "选择头像过大", Toast.LENGTH_SHORT).show();
                    return;
                }
                final ImageView iv_avatar = findViewById(R.id.iv_avatar);
                iv_avatar.setImageBitmap(chosBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
