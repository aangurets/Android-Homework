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
        if (mContacts.size() == 0) {
            fillingStorage();
        }
        return mContacts;
    }

    public static Contact getContact(int position) {
        return mContacts.get(position);
    }

    public static void addContact(Contact contact) {
        mContacts.add(contact);
    }

    public static void deleteContact() {
        mContacts.remove(mContacts.size() - 1);
    }

    public static String getSelectItemName() {
        int mId = mContacts.size() - 1;
        String mName = mContacts.get(mId).getName() + " " + mContacts.get(mId).getSurname();
        return mName;
    }
}
