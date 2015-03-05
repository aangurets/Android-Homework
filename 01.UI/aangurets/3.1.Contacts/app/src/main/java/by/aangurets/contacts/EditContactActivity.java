package by.aangurets.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import by.aangurets.contacts.model.Contact;

public class EditContactActivity extends Activity {

    private int mContactPosition;
    static final String ID_SELECTED_CONTACT = "selected contact";
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
    @InjectView(R.id.save_button)
    Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact_layout);
        ButterKnife.inject(this);
        List<Contact> mContacts = ContactsStorage.getAll();
        setTitle(R.string.edit_contact);
        mContactPosition = getIntent().getIntExtra(ID_SELECTED_CONTACT, 0);
        final Contact contact = mContacts.get(mContactPosition);
        fillingFields(contact);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dubbingContact(contact);
                Intent intent = new Intent(EditContactActivity.this, ContactListActivity.class);
                startActivity(intent);
            }
        });
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
    }
}
