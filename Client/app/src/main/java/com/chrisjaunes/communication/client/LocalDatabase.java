package com.chrisjaunes.communication.client;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.chrisjaunes.communication.client.contacts.Contacts;
import com.chrisjaunes.communication.client.contacts.ContactsDao;

/**
 * @author ChrisJaunes
 * version 1 : entities = {Contacts.class} version = 1, exportSchema = false, Dao = {ContactsDao}
 */
@Database(entities = {Contacts.class},
        version = 1,
        exportSchema = false)
public abstract  class LocalDatabase extends RoomDatabase{
    public abstract ContactsDao getContactsDao();
}
