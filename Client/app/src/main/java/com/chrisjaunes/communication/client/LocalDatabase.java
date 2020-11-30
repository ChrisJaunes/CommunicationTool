package com.chrisjaunes.communication.client;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.chrisjaunes.communication.client.contacts.Contacts;
import com.chrisjaunes.communication.client.contacts.ContactsDao;
import com.chrisjaunes.communication.client.talk.TMessageDao;
import com.chrisjaunes.communication.client.talk.TMessage;

/**
 * @author ChrisJaunes
 * version 1 : entities = {Contacts.class} version = 1, exportSchema = false, Dao = {ContactsDao}
 * version 2 : entities = {Contacts.class, TalkMessage.class} version = 1, exportSchema = false, Dao = {ContactsDao, TalkMessageDao}
 */
@Database(entities = {Contacts.class, TMessage.class},
        version = 2,
        exportSchema = false)
public abstract  class LocalDatabase extends RoomDatabase{
    public abstract ContactsDao getContactsDao();
    public abstract TMessageDao getTalkMessageDao();
}
