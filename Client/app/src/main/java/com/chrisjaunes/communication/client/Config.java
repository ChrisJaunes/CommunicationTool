package com.chrisjaunes.communication.client;

public class Config {
    static public final String URL_BASE = "http://125.216.246.202:8080/communicationServer";
    static public final String URL_LOGIN = URL_BASE + "/account/login";
    static public final String URL_REGISTER = URL_BASE + "/account/register";
    static public final String URL_UPDATE_MESSAGE = URL_BASE + "/account/updateMessage";
    static public final String ERROR_NET = "网络错误";
    static public final String ERROR_UNKNOW = "未知错误";
    static public final String ERROR_AVATAR_TOO_LAEGE = "头像太大";
    static public final long   LIMIT_AVATAR_LEN = 4294967295L;


    static final public String STR_STATUS = "status";

    static final public String STATUS_LOGIN_SUCCESSFUL = "login successful";
    static final public String STATUS_LOGIN_ACCOUNT_ERROR = "account is not exist";
    static final public String STATUS_LOGIN_PASSWORD_ERROR = "password is not correct";
    static final public String STATUS_REGISTER_SUCCESSFUL = "register successful";
    static final public String STATUS_REGISTER_ACCOUNT_EXIST = "account is exist";
    static final public String STATUS_UPDATE_SUCCESSFUL = "update successful";
    static final public String STATUS_UPDATE_FAIL = "update failed";

    static final public String TABLE_ACCOUNT = "account";
    static final public String STR_ACCOUNT = "account";
    static final public String STR_PASSWORD = "password";
    static final public String STR_NICKNAME = "nickname";
    static final public String STR_AVATAR = "avatar";
    static final public String STR_TEXT_STYLE = "text_style";

}
