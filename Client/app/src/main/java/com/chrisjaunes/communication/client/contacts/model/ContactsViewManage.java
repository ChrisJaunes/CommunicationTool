package com.chrisjaunes.communication.client.contacts.model;

import com.chrisjaunes.communication.client.MyApplication;

import java.util.HashMap;

public class ContactsViewManage {
    private static ContactsViewManage instance;
    private final ContactsDao contactsDao;
    private ContactsViewManage (){
        contactsDao = MyApplication.getInstance().getLocalDataBase().getContactsDao();
    }
    public static ContactsViewManage getInstance() {
        if (null == instance) instance = new ContactsViewManage();
        return instance;
    }

    private final HashMap<String, ContactsView> contactsViewHashMap = new HashMap<>();
    public void setContactsRaw(ContactsRaw contactsRaw) {
        String account = contactsRaw.getAccount();
        if(contactsViewHashMap.containsKey(account)) {
            contactsViewHashMap.get(account).setContactsRaw(contactsRaw);
        }else{
            contactsViewHashMap.put(account, new ContactsView(contactsRaw));
        }
    }
    public ContactsView getContactsView(String account) {
        if(contactsViewHashMap.containsKey(account)) return contactsViewHashMap.get(account);
        new Thread(()-> {
            if (contactsDao.isNowContactsExist(account)) {

            }
        }).start();
        return ContactsView.CONTACTS_DEFAULT;
    }
    public ContactsRaw getContactsRaw(String account) {
        if(contactsViewHashMap.containsKey(account)) return contactsViewHashMap.get(account).getContactsRaw();
        return contactsDao.queryContactsByAccountID(account);
    }
}
