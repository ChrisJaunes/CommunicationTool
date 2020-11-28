package com.chrisjaunes.communication.client.contacts;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.chrisjaunes.communication.client.Config;

import java.util.List;

@Dao
public interface ContactsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertContacts(Contacts contacts);

    @Query("SELECT * FROM Contacts WHERE operation = " + Config.CONTACTS_FRIENDS_AGREE_CODE)
    List<Contacts> queryNowContacts();

    @Query("SELECT * FROM Contacts WHERE operation = " + Config.CONTACTS_FRIENDS_REQUEST_CODE)
    List<Contacts> queryNewContacts();

    @Query("SELECT * FROM Contacts WHERE account = :account and operation = " + Config.CONTACTS_FRIENDS_AGREE_CODE + " LIMIT 1")
    boolean isNowContactsExist(String account);

    @Query("SELECT * FROM Contacts WHERE account = :account")
    Contacts queryContactsByAccountID(String account);
}
