package com.chrisjaunes.communication.client.talk;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TMessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertMessageList(TMessage... message_list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertMessage(TMessage message_list);

    @Query("SELECT * FROM TMessage WHERE account1 = :talk_account or account2 = :talk_account ORDER BY sendTime ASC")
    List<TMessage> queryMessageAboutTalk(String talk_account);

    @Query("SELECT * FROM TMessage WHERE account1 = :account1 and account2 = :account2 and sendTime = :sendTime LIMIT 1")
    boolean isMessageExist(String account1, String account2, String sendTime);
}
