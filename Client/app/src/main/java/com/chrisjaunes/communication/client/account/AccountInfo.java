package com.chrisjaunes.communication.client.account;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

/**
 * @author ChrisJaunes
 * 采用了建造者模式
 */
public class AccountInfo {
    public String account;
    public String nickname;
    public String avatar;
    public AccountTextStyle text_style;

    public static class Builder{
        private final AccountInfo account;
        Builder() {
            account = new AccountInfo();
            account.account = "";
            account.nickname = "";
            account.avatar = "";
            account.text_style = new AccountTextStyle();
        }
        public Builder addAccount(String account) {
            this.account.account = account;
            return this;
        }
        public Builder addNickname(String nickname) {
            account.nickname = nickname;
            return this;
        }
        public Builder addAvatar(String avatar) {
            account.avatar = avatar;
            return this;
        }
        public Builder addChatFontColor(String color) {
            account.text_style.font_color = color;
            return this;
        }
        public Builder addChatBubbleColor(String color) {
            account.text_style.bubble_color = color;
            return this;
        }
        public Builder addChatBorderColor(String color) {
            account.text_style.border_color = color;
            return this;
        }
        public AccountInfo build() {
            return account;
        }
    }

    public static class AccountTextStyle{
        public String font_color;
        public String bubble_color;
        public String border_color;

        @NonNull
        @Override
        public String toString() {
            return "AccountTextStyle{" +
                    "font_color='" + font_color + '\'' +
                    ", bubble_color='" + bubble_color + '\'' +
                    ", border_color='" + border_color + '\'' +
                    '}';
        }

        public String toJson() {
            Gson gson = new Gson();
            return gson.toJson(this);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Account{" +
                "account='" + account + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", text_style=" + text_style +
                '}';
    }

    public boolean check() {
        return account != null;
    }
}
