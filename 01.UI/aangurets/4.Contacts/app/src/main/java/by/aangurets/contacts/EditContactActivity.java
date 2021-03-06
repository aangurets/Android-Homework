package by.aangurets.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import by.aangurets.contacts.model.Contact;
import by.aangurets.contacts.storage.ContactsStorage;

public class EditContactActivity extends Activity {

    private static final String POSITION_CONTACT = "position contact";

    private Contact mContact;
    private int mContactPosition;

    @InjectView(R.id.nameEditText)
    EditText mName;

    @InjectView(R.id.surnameEditText)
    EditText mSurname;

    @InjectView(R.id.phoneEditText)
    EditText mPhone;

    @InjectView(R.id.emailAddressEditText)
    EditText mEmail;

    @InjectView(R.id.dateEditText)
    EditText mDate;

    @InjectView(R.id.occupationEditText)
    EditText mOccupation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        ButterKnife.inject(this);
        setTitle(R.string.edit_contact);
        mContactPosition = getIntent().getIntExtra(POSITION_CONTACT, 2);
        mContact = ContactsStorage.getInstance(this).getContact(mContactPosition);
        fillingFields(mContact);
    }

    public void fillingFields(Contact contact) {
        mName.setText(contact.getName());
        mSurname.setText(contact.getSurname());
        mPhone.setText(contact.getPhone());
        mEmail.setText(contact.getEmail());
        mDate.setText(contact.getDateOfBirdth().toString());
        mOccupation.setText(contact.getOccupation());
    }

    public void dubbingContact(Contact contact) {
        contact.setName(mName.getText().toString());
        contact.setSurname(mSurname.getText().toString());
        contact.setPhone(mPhone.getText().toString());
        contact.setEmail(mEmail.getText().toString());
        contact.setOccupation(mOccupation.getText().toString());
        ContactsStorage.getInstance(this).dubbingContact(contact);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_contact:
                dubbingContact(mContact);
                Intent intent = new Intent(EditContactActivity.this, ContactListActivity.class);
                startActivity(intent);
                return true;

            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }
}
