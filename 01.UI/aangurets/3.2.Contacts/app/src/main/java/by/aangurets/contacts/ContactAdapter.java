package by.aangurets.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import by.aangurets.contacts.model.Contact;

public class ContactAdapter extends BaseAdapter {
    private List<Contact> mContacts;
    static final String ID = "ID # ";
    static final String PHONE = "Phone number: ";

    public ContactAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    @Override
    public int getCount() {
        return mContacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return mContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView;
        if (convertView == null) {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout, parent, false);
        } else {
            mView = convertView;
        }
        TextView mNameTextView = (TextView) mView.findViewById(R.id.name_contact_list);
        TextView mPhoneTextView = (TextView) mView.findViewById(R.id.phone_contact_list);
        mNameTextView.setText(ID + getItem(position).getId() + " " + getItem(position).getName());
        mPhoneTextView.setText(PHONE + getItem(position).getPhone());
        return mView;
    }

}
