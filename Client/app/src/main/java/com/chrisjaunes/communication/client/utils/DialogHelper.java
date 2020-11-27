package com.chrisjaunes.communication.client.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.account.Account;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chris
 */
public class DialogHelper {
    public static class logoutDialog extends Dialog {
        public interface LogoutListener {
            void logout();
        }

        public logoutDialog(@NonNull Context context, int themeResId, LogoutListener listener) {
            super(context, themeResId);
            setContentView(R.layout.dialog_logout);
            findViewById(R.id.btn_yes).setOnClickListener(v -> listener.logout());
            findViewById(R.id.btn_no).setOnClickListener(v -> dismiss());
        }
    }

    public static class SelectTextStyleColorDialog extends Dialog {
        public interface UpdateListener {
            void update(Account.AccountTextStyle textStyle);
        }
        public SelectTextStyleColorDialog(@NonNull Context context, UpdateListener listener) {
            super(context);
            setContentView(R.layout.dialog_select_color);
            final View vi_font_color = findViewById(R.id.vi_font_color);
            final Spinner sp_font_color = findViewById(R.id.sp_font_color);
            sp_font_color.setAdapter(ColorTrHelper.ColorSelectedAdapter.getColorAdapter(context));
            sp_font_color.setOnItemSelectedListener(new ColorTrHelper.ColorSelectedListener(vi_font_color));

            final View vi_bubble_color = findViewById(R.id.vi_bubble_color);
            final Spinner sp_bubble_color = findViewById(R.id.sp_bubble_color);
            sp_bubble_color.setAdapter(ColorTrHelper.ColorSelectedAdapter.getColorAdapter(context));
            sp_bubble_color.setOnItemSelectedListener(new ColorTrHelper.ColorSelectedListener(vi_bubble_color));

            final View vi_border_color = findViewById(R.id.vi_border_color);
            final Spinner sp_border_color = findViewById(R.id.sp_border_color);
            sp_border_color.setAdapter(ColorTrHelper.ColorSelectedAdapter.getColorAdapter(context));
            sp_border_color.setOnItemSelectedListener(new ColorTrHelper.ColorSelectedListener(vi_border_color));

            findViewById(R.id.btn_confirm).setOnClickListener(v -> {
                Account.AccountTextStyle textStyle = new Account.AccountTextStyle();
                textStyle.font_color = ColorTrHelper.colorToString(((ColorDrawable) vi_font_color.getBackground()).getColor());
                textStyle.bubble_color = ColorTrHelper.colorToString(((ColorDrawable) vi_bubble_color.getBackground()).getColor());
                textStyle.border_color = ColorTrHelper.colorToString(((ColorDrawable) vi_border_color.getBackground()).getColor());
                listener.update(textStyle);
            });
        }
    }
}
