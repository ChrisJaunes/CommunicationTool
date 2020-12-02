package com.chrisjaunes.communication.client.group.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertMessageList(GMessage... message_list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertMessage(GMessage message_list);

    @Query("SELECT * FROM GMessage WHERE `group` = :group ORDER BY sendTime ASC")
    List<GMessage> queryMessageAboutGroup(String group);

    @Query("SELECT * FROM GMessage WHERE `group` = :group and `account` = :account and sendTime = :send_time LIMIT 1")
    boolean isMessageExist(int group, String account, String send_time);
}
