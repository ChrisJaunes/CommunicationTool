package com.chrisjaunes.communication.client.account.model;

import androidx.annotation.NonNull;

import com.chrisjaunes.communication.client.my_view.ChatTextStyleRaw;

/**
 * 采用了建造者模式
 * version 1.1: 使用了建造者模式
 * version 1.2: 将ChatTextStyleRaw和AccountRaw分开
 * version 1.3：将AccountRaw的构造函数设为私有，确保只能使用Builder创建
 * @author ChrisJaunes
 * @version 1.3(2020-12-18)
 */
public class AccountRaw {
    public String account;
    public String nickname;
    public String avatar;
    public ChatTextStyleRaw text_style;

    private AccountRaw(){}
    public static class Builder{
        private final AccountRaw account;
        public Builder() {
            account = new AccountRaw();
            account.account = "";
            account.nickname = "";
            account.avatar = "";
            account.text_style = ChatTextStyleRaw.CHAT_TEXT_STYLE_RAW_DEFAULT;
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
        public Builder setChatTextStyle(ChatTextStyleRaw textStyle) {
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
}
