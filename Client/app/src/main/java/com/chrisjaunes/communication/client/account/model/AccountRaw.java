package com.chrisjaunes.communication.client.account.model;

import androidx.annotation.NonNull;

import com.chrisjaunes.communication.client.myView.ChatTextStyle;

/**
 * @author ChrisJaunes
 * 采用了建造者模式
 */
public class AccountRaw {
    public String account;
    public String nickname;
    public String avatar;
    public ChatTextStyle text_style;

    public static class Builder{
        private final AccountRaw account;
        public Builder() {
            account = new AccountRaw();
            account.account = "";
            account.nickname = "";
            account.avatar = "";
            account.text_style = new ChatTextStyle();
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
        public AccountRaw build() {
            return account;
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
