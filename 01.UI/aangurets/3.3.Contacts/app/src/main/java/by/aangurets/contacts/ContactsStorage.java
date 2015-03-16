package by.aangurets.contacts;

import java.util.ArrayList;

import by.aangurets.contacts.model.Contact;

public final class ContactsStorage {

    public static ArrayList<Contact> sContacts = new ArrayList<>();

    public static ArrayList<Contact> getAll() {
        Contact.fillContacts(sContacts);
        return sContacts;
    }

    public static Contact getContact(int position) {
        return sContacts.get(position);
    }

    public static void addContact(Contact contact) {
        sContacts.add(contact);
    }

    public static void deleteContact() {
        sContacts.remove(sContacts.size() - 1);
    }

    public static String getSelectItemName() {
        int mId = sContacts.size() - 1;
        String mName = sContacts.get(mId).getName() + " " + sContacts.get(mId).getSurname();
        return mName;
    }
}
