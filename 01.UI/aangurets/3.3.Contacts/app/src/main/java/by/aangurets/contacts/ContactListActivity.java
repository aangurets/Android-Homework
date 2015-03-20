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

    public static final String POSITION_OF_THE_SELECTED_CONTACT = "selected contact";
    public static final int LOADER_ID = 1;

    private ListView mContactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_activity);

        mContactList = (ListView) findViewById(R.id.listView);

        setTitle(R.string.app_name);

        mContactList.setAdapter(new ContactAdapter(this, new ArrayList<Contact>()));
        getLoaderManager().initLoader(LOADER_ID, null, this);
        mContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactListActivity.this, ViewContactActivity.class);
                intent.putExtra(POSITION_OF_THE_SELECTED_CONTACT, position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_contact:
                ContactsStorage.addContact(GenerationOfContact.generateContact());
                updateList();
                return true;
            case R.id.delete_contact:
                confirmRemoval();
                updateList();
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

    private void confirmRemoval() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);
        builder.setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContactsStorage.deleteContact();
                updateList();
            }
        });
        builder.setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setTitle(R.string.agree_delete)
                .setMessage(R.string.question
                        + ' ' + ContactsStorage.getSelectItemName() + " ?");
        builder.create().show();
    }

    private void updateList() {
        ((BaseAdapter) mContactList.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public Loader<List<Contact>> onCreateLoader(int id, Bundle args) {
        return new ContactsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Contact>> loader, List<Contact> data) {
        mContactList.setAdapter(new ContactAdapter(this, data));
        updateList();
    }

    @Override
    public void onLoaderReset(Loader<List<Contact>> loader) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        updateList();
    }

    private static class ContactsLoader extends AbstractLoader<List<Contact>> {

        ContactsLoader(Context context) {
            super(context);
        }

        @Override
        public List<Contact> loadInBackground() {
            return ContactsStorage.getAll();
        }
    }
}
