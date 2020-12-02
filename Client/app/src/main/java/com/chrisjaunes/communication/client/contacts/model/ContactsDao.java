package com.chrisjaunes.communication.client.contacts.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.chrisjaunes.communication.client.Config;

import java.util.List;

@Dao
public interface ContactsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertContacts(ContactsRaw contactsRaw);

    @Query("SELECT * FROM ContactsRaw WHERE operation = " + Config.CONTACTS_FRIENDS_AGREE_CODE)
    List<ContactsRaw> queryNowContactsList();

    @Query("SELECT * FROM ContactsRaw WHERE operation = " + Config.CONTACTS_FRIENDS_REQUEST_CODE)
    List<ContactsRaw> queryNewContacts();

    @Query("SELECT * FROM ContactsRaw WHERE account = :account and operation = " + Config.CONTACTS_FRIENDS_AGREE_CODE + " LIMIT 1")
    boolean isNowContactsExist(String account);

    @Query("SELECT * FROM ContactsRaw WHERE account = :account")
    ContactsRaw queryContactsByAccountID(String account);
}
