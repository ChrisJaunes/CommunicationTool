package com.chrisjaunes.communication.client.account.model;

import androidx.annotation.NonNull;

import com.chrisjaunes.communication.client.myView.ChatTextStyle;

/**
 * @author ChrisJaunes
 * 采用了建造者模式
 */
public class AccountRaw {
    static public final long  LIMIT_AVATAR_LEN = 4294967295L;
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
        public Builder setAccount(String account) {
            this.account.account = account;
            return this;
        }
        public Builder setNickname(String nickname) {
            account.nickname = nickname;
            return this;
        }
        public Builder setAvatar(String avatar) {
            account.avatar = avatar;
            return this;
        }
        public Builder setChatTextStyle(ChatTextStyle textStyle) {
            account.text_style = textStyle;
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
