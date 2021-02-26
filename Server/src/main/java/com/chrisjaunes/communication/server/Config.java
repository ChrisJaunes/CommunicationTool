package com.chrisjaunes.communication.server;

/**
 * @author ChrisJaunes
 */
public class Config {
    static final public String STR_STATUS = "status";
    static final public String STR_STATUS_DATA = "data";



    static final public String STATUS_UPDATE_SUCCESSFUL = "update successful";
    static final public String STATUS_ADD_SUCCESSFUL = "add successful";
    static final public String STATUS_UPDATE_FAIL = "update failed";
    static final public String STATUS_ACCOUNT_NOT_LOGIN = "account not login";
    static final public String STATUS_ILLEGAL_PARAMETER = "illegal parameter";
    static final public String STATUS_DB_ILLEGAL_PARAMETER = "db illegal parameter";

    static final public String STATUS_FAIL_NAME_MISMATCH = "FAIL_NAME_MISMATCH";
    static final public String STATUS_SUCCESSFUL = "successful";
    static final public String STATUS_FAIL_NOT_FIND_PATIENT = "STATUS_FAIL_NOT_FIND_PATIENT";


    static final public String STR_ACCOUNT_AGREE = "account_agree";
    static final public String TABLE_ACCOUNT = "account";
    static final public String STR_ACCOUNT = "account";
    static final public String STR_PASSWORD = "password";
    static final public String STR_NICKNAME = "nickname";
    static final public String STR_AVATAR = "avatar";
    static final public String STR_TEXT_STYLE = "text_style";
    static final public String STR_TIME = "time";
    static final public String STR_OPERATION = "operation";
    static final public String TABLE_TALK_MESSAGES = "talk_messages";
    static final public String STR_SEND_TIME = "send_time";
    static final public String STR_CONTENT_TYPE = "content_type";
    static final public int STR_CONTENT_TYPE_NULL = 0;
    static final public int STR_CONTENT_TYPE_TEXT = 1;
    static final public int STR_CONTENT_TYPE_JPEG = 2;
    static final public int STR_CONTENT_TYPE_PNG  = 3;
    static final public int STR_CONTENT_TYPE_GIF  = 4;
    static final public int STR_CONTENT_TYPE_EMOJI= 5;
    static final public String STR_CONTENT = "content";

    static final public String TABLE_GROUP = "group";
    static final public String STR_GROUP = "group";
    static final public String STR_GROUP_NAME= "group_name";
    static final public String STR_GROUP_AVATAR = "group_avatar";
    static final public String STR_GROUP_MEMBER_LIST = "group_member_list";
    static final public String TABLE_GROUP_MEMBER = "group_member";
    static final public String STR_GROUP_MEMBER_OPERATION = "";
    static final public String TABLE_GROUP_MESSAGES = "group_messages";

    public static final String STR_CONTACTS_ACCOUNT = "account2";
    public static final String TABLE_CONTACTS = "contacts";
    public static final String STR_ACCOUNT1 = "account1";
    public static final String STR_ACCOUNT2 = "account2";
    public static final String CONTACTS_FRIEND_REQUEST = "request";
    public static final String CONTACTS_FRIENDS_AGREE = "agree";
    public static final String CONTACTS_FRIENDS_REJECT = "reject";
    public static final int CONTACTS_FRIENDS_AGREE_CODE = 1;
    public static final int CONTACTS_FRIENDS_REQUEST_CODE = 2;

    public static final String STATUS_QUERY_GROUP_SUCCESSFUL = "query_group_successful";
}
