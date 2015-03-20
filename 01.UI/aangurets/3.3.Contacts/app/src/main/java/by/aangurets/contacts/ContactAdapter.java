package by.aangurets.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import by.aangurets.contacts.model.Contact;

public class ContactAdapter extends ArrayAdapter<Contact> {
    static final String ID = "ID # ";
    static final String PHONE = "Phone number: ";

    public ContactAdapter(Context context, List<Contact> contacts) {
        super(context, android.R.layout.simple_list_item_1, android.R.id.text1, contacts);
        LayoutInflater.from(context);
    }

    @Override
    public Contact getItem(int position) {
        return ContactsStorage.getContact(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView;
        if (convertView == null) {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact, parent, false);
        } else {
            mView = convertView;
        }
        TextView mNameTextView = (TextView) mView.findViewById(R.id.name_contact_list);
        TextView mPhoneTextView = (TextView) mView.findViewById(R.id.phone_contact_list);
        mNameTextView.setText(ID + getItem(position).getId() + " " + getItem(position).getName()
                + " " + getItem(position).getSurname());
        mPhoneTextView.setText(PHONE + getItem(position).getPhone());
        return mView;
    }
}
