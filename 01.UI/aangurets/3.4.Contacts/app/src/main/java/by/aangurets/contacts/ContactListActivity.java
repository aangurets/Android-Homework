package by.aangurets.contacts;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
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
import by.aangurets.contacts.storage.ContactsStorage;

public class ContactListActivity extends ListActivity
        implements LoaderManager.LoaderCallbacks<List<Contact>> {

    private static final String QUESTION = "Are you sure you want to delete a contact?";
    private static final String POSITION_CONTACT = "position contact";
    public static final int LOADER_ID = 2;

    private ListView mContactListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mContactListView = (ListView) findViewById(android.R.id.list);

        setTitle(R.string.app_name);

        mContactListView.setAdapter(new ContactAdapter(this, new ArrayList<Contact>()));
        getLoaderManager().initLoader(LOADER_ID, null, this);
        mContactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactListActivity.this, ViewContactActivity.class);
                intent.putExtra(POSITION_CONTACT, position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_contact:
                ContactsStorage.sContactsStorage.addContact();
                updateList();
                return true;

            case R.id.delete_contact:
                deleteLastContact();
                updateList();
                return true;

            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_list_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // метод удаляет последний контакт
    private void deleteLastContact() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);
        builder.setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContactsStorage.getInstance(ContactListActivity.this).deleteContact();
                updateList();
            }
        });
        builder.setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setTitle(R.string.agree_delete).setMessage(QUESTION);
        builder.create().show();
    }

    private void updateList() {
        ((BaseAdapter) mContactListView.getAdapter()).notifyDataSetChanged();
    }

    // Loader
    @Override
    public Loader<List<Contact>> onCreateLoader(int id, Bundle args) {
        return new ContactsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Contact>> loader, List<Contact> data) {
        mContactListView.setAdapter(new ContactAdapter(this, data));
    }

    @Override
    public void onLoaderReset(Loader<List<Contact>> listLoader) {
        ContactsStorage.getInstance(this).cleaningStorage();
    }

    private static class ContactsLoader extends AbstractLoader<List<Contact>> {

        ContactsLoader(Context context) {
            super(context);
        }

        @Override
        public List<Contact> loadInBackground() {
            return ContactsStorage.getInstance(getContext()).getContacts();
        }
    }
}
