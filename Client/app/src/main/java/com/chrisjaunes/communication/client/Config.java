package com.chrisjaunes.communication.client;

public class Config {
    static public final String URL_BASE = "http://125.216.246.202:8080/communicationServer";
    static public final String URL_LOGIN = URL_BASE + "/account/login";
    static public final String URL_REGISTER = URL_BASE + "/account/register";
    static public final String URL_UPDATE_MESSAGE = URL_BASE + "/account/updateMessage";
    static public final String URL_CONTACTS_QUERY = URL_BASE + "/contacts/query";
    static public final String URL_TALK_QUERY = URL_BASE + "/talk/query";
    static public final String URL_TALK_UPDATE = URL_BASE + "/talk/update";

    static public final String ERROR_NET = "网络错误";
    static public final String ERROR_UNKNOW = "未知错误";
    static public final String ERROR_AVATAR_TOO_LAEGE = "头像太大";
    static public final long   LIMIT_AVATAR_LEN = 4294967295L;


    static final public String STR_STATUS = "status";
    static final public String STR_STATUS_DATA = "data";

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
    static final public String TABLE_CONTACTS = "contacts";
    static final public String STR_ACCOUNT1 = "account1";
    static final public String STR_ACCOUNT2 = "account2";
    static final public String STR_TIME = "time";
    static final public String STR_OPERATION = "operation";
    static final public String CONTACTS_FRIENDS_AGREE = "agree";
    static final public int CONTACTS_FRIENDS_AGREE_CODE = 1;
    static final public String CONTACTS_FRIEND_REQUEST = "request";
    static final public int CONTACTS_FRIENDS_REQUEST_CODE = 2;
    static final public String CONTACTS_FRIENDS_REJECT = "reject";

    static final public String BUNDLE_ACCOUNT = "";
    static final public String STR_SEND_TIME = "send_time";
    static final public String STR_CONTENT_TYPE = "content_type";
    static final public int STR_CONTENT_TYPE_NULL = 0;
    static final public int STR_CONTENT_TYPE_TEXT = 1;
    static final public int STR_CONTENT_TYPE_JPEG = 2;
    static final public int STR_CONTENT_TYPE_PNG  = 3;
    static final public int STR_CONTENT_TYPE_GIF  = 4;
    static final public int STR_CONTENT_TYPE_EMOJI= 5;
    static final public String STR_CONTENT = "content";


    static final public String ACCOUNT_VISITORS = "visitors";
}
