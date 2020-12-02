package com.chrisjaunes.communication.client.contacts;

public class ContactsViewManage {
    private static ContactsViewManage instance;
    private ContactsViewManage (){}
    public static ContactsViewManage getInstance() {
        if (null == instance) instance = new ContactsViewManage();
        return instance;
    }
    public ContactsView getContactsView(String contacts) {
        return null;
    }

}
