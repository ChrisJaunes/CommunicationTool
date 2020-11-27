package com.chrisjaunes.communication.server.utils_db;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeHelper {
    static public String timeToStdTime(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setLenient(false);
            java.util.Date date = format.parse(time);
            return new Timestamp(date.getTime()).toString();
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "0000-00-00 00:00:00";
    }
}
