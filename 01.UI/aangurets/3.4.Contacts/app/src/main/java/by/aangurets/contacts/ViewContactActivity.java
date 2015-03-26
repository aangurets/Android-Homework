package by.aangurets.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import by.aangurets.contacts.model.Contact;
import by.aangurets.contacts.storage.ContactsStorage;

public class ViewContactActivity extends Activity {

    private static final String POSITION_CONTACT = "position contact";

    private int mContactPosition;

    @InjectView(R.id.nameTextView)
    TextView mName;

    @InjectView(R.id.surnameTextView)
    TextView mSurname;

    @InjectView(R.id.phoneTextView)
    TextView mPhone;

    @InjectView(R.id.emailAddressTextView)
    TextView mEmail;

    @InjectView(R.id.dateTextView)
    TextView mDate;

    @InjectView(R.id.occupationTextView)
    TextView mOccupation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        ButterKnife.inject(this);
        setTitle(R.string.review_contact);
        mContactPosition = getIntent().getIntExtra(POSITION_CONTACT, 0);
        Contact mContact = ContactsStorage.getInstance(this).getContact(mContactPosition);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.review_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_contact:
                Intent intent = new Intent(ViewContactActivity.this, EditContactActivity.class);
                intent.putExtra(POSITION_CONTACT, mContactPosition);
                startActivity(intent);
                return true;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }
}
