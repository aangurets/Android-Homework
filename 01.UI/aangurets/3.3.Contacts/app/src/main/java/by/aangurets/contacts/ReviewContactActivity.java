package by.aangurets.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import by.aangurets.contacts.model.Contact;

public class ReviewContactActivity extends Activity {

    static final String ID_SELECTED_CONTACT = "selected contact";

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
        setContentView(R.layout.review_contact_layout);
        ButterKnife.inject(this);
        setTitle(R.string.review_contact);
        List<Contact> mContacts = ContactsStorage.getAll();
        mContactPosition = getIntent().getIntExtra(ID_SELECTED_CONTACT, 0);
        Contact contact = mContacts.get(mContactPosition);
        fillingFields(contact);
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
                Intent intent = new Intent(ReviewContactActivity.this, EditContactActivity.class);
                intent.putExtra(ID_SELECTED_CONTACT, mContactPosition);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
