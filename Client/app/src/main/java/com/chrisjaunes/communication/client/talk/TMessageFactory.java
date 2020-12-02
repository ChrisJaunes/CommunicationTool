package com.chrisjaunes.communication.client.talk;

import android.os.Message;

public class TMessageFactory {
    static final public int STR_CONTENT_TYPE_NULL = 0;
    static final public int STR_CONTENT_TYPE_TEXT = 1;
    static final public int STR_CONTENT_TYPE_IMG = 2;
    static final public int STR_CONTENT_TYPE_PNG  = 3;
    static final public int STR_CONTENT_TYPE_GIF  = 4;
    static final public int STR_CONTENT_TYPE_EMOJI= 5;

    TMessage create(String account1, String account2, String sendTime, int type) {
        return new TMessage(account1, account2, sendTime, type);
    }
}
