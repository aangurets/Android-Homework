package by.aangurets.contacts;

import java.util.ArrayList;
import java.util.List;

import by.aangurets.contacts.model.Contact;

public class ContactsStorage {

    public static List<Contact> mContacts = new ArrayList<>();

    private static List<Contact> fillingStorage() {
        for (int i = 0; i < 50; i++) {
            Contact contact = Contact.generateNewContact();
            mContacts.add(contact);
        }
        return mContacts;
    }


    public static List<Contact> getAll() {
        fillingStorage();
        return mContacts;
    }

    public static void add(Contact contact) {
        mContacts.add(contact);
    }
}
