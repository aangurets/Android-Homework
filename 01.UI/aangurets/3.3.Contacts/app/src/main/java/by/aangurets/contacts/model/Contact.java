package by.aangurets.contacts.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Contact {

    private static int mCount = 0;

    private static String[] names =
            new String[]{"John", "Paul", "Mike", "Jason", "Kasper", "Adam", "Alberto"};
    public static String[] surnames =
            new String[]{"Smith", "Addison", "Johnson", "Williams", "Crawford", "Holmes", "Mills"};

    static final SimpleDateFormat SIMPLE_DATE_FORMAT =
            new SimpleDateFormat("dd-mm-yyyy", Locale.getDefault());

    private int mId;
    private String mName;
    private String mSurname;
    private String mPhone;
    private String mEmail;
    private String mDateOfBirdth;
    private String mOccupation;

    public Contact() {
        mId = ++mCount;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String surname) {
        mSurname = surname;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getDateOfBirdth() {
        return mDateOfBirdth;
    }

    public void setDateOfBirdth(String dateOfBirdth) {
        mDateOfBirdth = dateOfBirdth;
    }

    public String getOccupation() {
        return mOccupation;
    }

    public void setOccupation(String occupation) {
        mOccupation = occupation;
    }

    public static ArrayList<Contact> fillContacts(ArrayList<Contact> contacts) {
        if (contacts.size() == 0) {
            for (int i = 0; i < 50; i++) {
                {
                    contacts.add(getContact());
                }
            }
        }
        return contacts;
    }

    public static Contact getContact() {
        Random random = new Random();
        int randomPhone = random.nextInt(9000000);
        int randomId = random.nextInt(20);
        int randomName = random.nextInt(6);
        Contact contact = new Contact();
        contact.setName(Contact.names[randomName]);
        contact.setSurname(Contact.surnames[randomName]);
        contact.setDateOfBirdth(SIMPLE_DATE_FORMAT.format(new Date()));
        contact.setPhone("" + randomPhone);
        contact.setEmail(Contact.names[randomName] + randomId + "@gmail.com");
        contact.setOccupation("Junior Android Developer");
        return contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Contact contact = (Contact) o;
        return mId == contact.mId;
    }

    @Override
    public int hashCode() {
        return mId;
    }
}
