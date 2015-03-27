package by.aangurets.contacts.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import by.aangurets.contacts.model.Contact;
import by.aangurets.contacts.model.ContactGenerator;

public class DataBase {
    private static final String DATABASE_NAME = "app_contacts";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "contacts";
    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String DATE = "date";
    private static final String OCCUPATION = "occupation";

    private SQLiteDatabase mDataBase;
    private SQLiteOpenHelper mDBHelper;

    public DataBase(Context context) {
        mDBHelper = new DBHelper(context);
        mDataBase = mDBHelper.getWritableDatabase();
    }

    public List<Contact> getAll() {
        fillingDataBase();
        List<Contact> mContacts = new ArrayList<>();
        Cursor cursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(NAME);
            int surnameColumnIndex = cursor.getColumnIndex(SURNAME);
            int phoneColumnIndex = cursor.getColumnIndex(PHONE);
            int emailColumnIndex = cursor.getColumnIndex(EMAIL);
            int dateColumnIndex = cursor.getColumnIndex(DATE);
            int occupationColumnIndex = cursor.getColumnIndex(OCCUPATION);
            do {
                Contact contact = new Contact();
                contact.setName(cursor.getString(nameColumnIndex));
                contact.setSurname(cursor.getString(surnameColumnIndex));
                contact.setPhone(cursor.getString(phoneColumnIndex));
                contact.setEmail(cursor.getString(emailColumnIndex));
                contact.setDateOfBirdth(cursor.getString(dateColumnIndex));
                contact.setOccupation(cursor.getString(occupationColumnIndex));
                mContacts.add(contact);
            } while (cursor.moveToNext());
        }
        return mContacts;
    }

    public void addContact(Contact contact) {
        mDataBase.insert(TABLE_NAME, null, getContentValuesFromContact(contact));
    }

    public void fillingDataBase() {
        addContact(ContactGenerator.generate());
    }

    public void dubbingContact(Contact contact) {
        mDataBase.update(TABLE_NAME, getContentValuesFromContact(contact), ID + "=" +
                contact.getId(), null);
    }

    public void deleteContact(Contact contact) {
        mDataBase.delete(TABLE_NAME, ID + "=" + contact.getId(), null);
    }

    private ContentValues getContentValuesFromContact(Contact contact) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(OCCUPATION, contact.getOccupation());
        contentValues.put(DATE, contact.getDateOfBirdth());
        contentValues.put(EMAIL, contact.getEmail());
        contentValues.put(PHONE, contact.getPhone());
        contentValues.put(SURNAME, contact.getSurname());
        contentValues.put(NAME, contact.getName());
        return contentValues;
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                    ID + " integer primary key autoincrement, " +
                    NAME + " TEXT NOT NULL , " + SURNAME + " TEXT NOT NULL , "
                    + PHONE + " TEXT NOT NULL , " + EMAIL + " TEXT NOT NULL , "
                    + DATE + " TEXT NOT NULL , " + OCCUPATION + " TEXT NOT NULL" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
