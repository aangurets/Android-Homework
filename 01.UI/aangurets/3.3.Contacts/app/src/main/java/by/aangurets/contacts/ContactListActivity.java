package by.aangurets.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_contact:
                ContactsStorage.addContact(Contact.generateNewContact());
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.delete_contact:
                return true;
            case R.id.agree:
                ContactsStorage.deleteContact();
                mAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_list_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

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

    @Override
    protected void onStop() {
        mAdapter.notifyDataSetChanged();
        super.onStop();
    }
}
