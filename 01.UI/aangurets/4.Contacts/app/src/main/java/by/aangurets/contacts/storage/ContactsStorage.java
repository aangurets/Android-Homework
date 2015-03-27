package by.aangurets.contacts.storage;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import by.aangurets.contacts.model.Contact;
import by.aangurets.contacts.model.ContactGenerator;

public class ContactsStorage {
    public static ContactsStorage sContactsStorage;
    private static List<Contact> sContacts;

    private DataBase mDataBase;

    public ContactsStorage(Context context) {
        mDataBase = new DataBase(context);
        sContacts = new ArrayList<>();
    }

    public static ContactsStorage getInstance(Context context) {
        if (sContactsStorage == null) {
            sContactsStorage = new ContactsStorage(context);
        }
        return sContactsStorage;
    }

    public List<Contact> getContacts() {
        if (sContacts.isEmpty()) {
            sContacts = mDataBase.getAll();
        }
        return sContacts;
    }

    public void cleaningStorage() {
        sContacts.clear();
    }

    public void dubbingContact(Contact contact) {
        mDataBase.dubbingContact(contact);
    }

    public Contact getContact(int position) {
        return sContacts.get(position);
    }

    public void addContact() {
        Contact newContact = ContactGenerator.generate();
        sContacts.add(newContact);
        mDataBase.addContact(newContact);
    }

    public void deleteContact() {
        if (sContacts.size() != 0) {
            Contact contact = sContacts.get(sContacts.size() - 1);
            sContacts.remove(sContacts.size() - 1);
            mDataBase.deleteContact(contact);
        }
    }
}
