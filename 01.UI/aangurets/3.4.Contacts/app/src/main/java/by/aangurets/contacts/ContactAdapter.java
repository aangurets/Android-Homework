package by.aangurets.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import by.aangurets.contacts.model.Contact;
import by.aangurets.contacts.storage.ContactsStorage;

public class ContactAdapter extends ArrayAdapter<Contact> {
    static final String PREFIX_ID = "PREFIX_ID # ";
    static final String PREFIX_PHONE = "Phone number: ";

    public ContactAdapter(Context context, List<Contact> contacts) {
        super(context, android.R.layout.simple_list_item_1, android.R.id.text1, contacts);
        LayoutInflater.from(context);
    }

    @Override
    public Contact getItem(int position) {
        return ContactsStorage.getInstance(getContext()).getContact(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = convertView;
        ViewHolder holder;
        if (mView == null) {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact, parent, false);
            holder = new ViewHolder();
            holder.mNameTextView = (TextView) mView.findViewById(R.id.name_contact_list);
            holder.mPhoneTextView = (TextView) mView.findViewById(R.id.phone_contact_list);
            mView.setTag(holder);
        } else {
            holder = (ViewHolder) mView.getTag();
        }
        holder.mNameTextView.setText(PREFIX_ID + getItem(position).getId() + ' ' + getItem(position).getName()
                + ' ' + getItem(position).getSurname());
        holder.mPhoneTextView.setText(PREFIX_PHONE + getItem(position).getPhone());
        return mView;
    }

    static class ViewHolder {
        public TextView mNameTextView;
        public TextView mPhoneTextView;
    }
}
