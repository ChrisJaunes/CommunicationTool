package com.chrisjaunes.communication.client;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.chrisjaunes.communication.client.contacts.model.ContactsRaw;
import com.chrisjaunes.communication.client.contacts.model.ContactsDao;
import com.chrisjaunes.communication.client.group.model.GMessage;
import com.chrisjaunes.communication.client.group.model.GroupDao;
import com.chrisjaunes.communication.client.talk.TMessageDao;
import com.chrisjaunes.communication.client.talk.TMessage;

/**
 * @author ChrisJaunes
 * version 1 : entities = {Contacts.class} exportSchema = false, Dao = {ContactsDao}
 * version 2 : entities = {Contacts.class, TalkMessage.class} exportSchema = false, Dao = {ContactsDao, TalkMessageDao}
 * version 3 : entities = {Contacts.class, TalkMessage.class, GMessage.class} exportSchema = false, Dao = {ContactsDao, TalkMessageDao, GMessageDao}
 */
@Database(entities = {ContactsRaw.class, TMessage.class, GMessage.class},
        version = 3,
        exportSchema = false)
public abstract  class LocalDatabase extends RoomDatabase{
    public abstract ContactsDao getContactsDao();
    public abstract TMessageDao getTalkMessageDao();
    public abstract GroupDao getGroupDao();
}
