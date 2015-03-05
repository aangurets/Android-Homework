package by.aangurets.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import by.aangurets.contacts.model.Contact;

public class ReviewContactActivity extends Activity {

    private int mContactPosition;
    static final String ID_SELECTED_CONTACT = "selected contact";
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
    @InjectView(R.id.edit_button)
    Button mEditButton;

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

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewContactActivity.this, EditContactActivity.class);
                intent.putExtra(ID_SELECTED_CONTACT, mContactPosition);
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

}
