package by.aangurets.contacts;

import android.app.Activity;
import android.app.AlertDialog;
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

public class ContactListActivity extends Activity implements LoaderManager.LoaderCallbacks<List<Contact>> {
    private static List<Contact> mContactsList = new ArrayList<>();
    private static final String QUESTION = "Are you sure you want to delete a contact: ";
    static final String ID_SELECTED_CONTACT = "selected contact";
    public static final int LOADER_ID = 1;

    BaseAdapter mAdapter;
    private ListView mMListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_layout);

        mContactsList = ContactsStorage.getAll();
        mMListView = (ListView) findViewById(R.id.listView);

        mAdapter = new ContactAdapter(this, mContactsList);
        setTitle(R.string.app_name);

        getLoaderManager().initLoader(LOADER_ID, null, this);

        mMListView.setAdapter(mAdapter);
        mMListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactListActivity.this, ReviewContactActivity.class);
                intent.putExtra(ID_SELECTED_CONTACT, position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_contact:
                ContactsStorage.addContact(Contact.generateNewContact());
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.delete_contact:
                acceptDelete(item);
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
    protected void onStop() {
        mAdapter.notifyDataSetChanged();
        super.onStop();
    }

    private void acceptDelete(MenuItem item) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);
        builder.setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContactsStorage.deleteContact();
                mAdapter.notifyDataSetChanged();
            }
        });
        builder.setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setTitle(R.string.agree_delete)
                .setMessage(QUESTION
                        + ContactsStorage.getSelectItemName() + " ?");
        builder.create().show();
    }

    @Override
    public Loader<List<Contact>> onCreateLoader(int id, Bundle args) {
        return new ContactsLoader(this, ContactsStorage.getAll());
    }

    @Override
    public void onLoadFinished(Loader<List<Contact>> loader, List<Contact> data) {
        if (data != null) {
            data.clear();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Contact>> loader) {

    }

    static class ContactsLoader extends AbstractLoader<List<Contact>> {

        private List<Contact> mContacts;

        ContactsLoader(Context context, List<Contact> mContacts) {
            super(context);
            mContacts = ContactsStorage.getAll();
        }

        @Override
        public List<Contact> loadInBackground() {
            return mContacts;
        }
    }
}
