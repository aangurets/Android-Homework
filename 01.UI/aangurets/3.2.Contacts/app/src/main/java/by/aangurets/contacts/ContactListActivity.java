package by.aangurets.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import by.aangurets.contacts.model.Contact;

public class ContactListActivity extends Activity {
    private static List<Contact> mContactsList = new ArrayList<>();
    BaseAdapter mAdapter;
    static final String ID_SELECTED_CONTACT = "selected contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_layout);
        mContactsList = ContactsStorage.getAll();

        ListView mListView = (ListView) findViewById(R.id.listView);

        mAdapter = new ContactAdapter(mContactsList);
        setTitle(R.string.app_name);

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactListActivity.this, ReviewContactActivity.class);
                intent.putExtra(ID_SELECTED_CONTACT, position);
                startActivity(intent);
            }
        });
    }


}
