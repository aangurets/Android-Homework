package by.aangurets.contacts.model;

public class Contact {

    private static int mCount = 0;

    private int mId;
    private String mName;
    private String mSurname;
    private String mPhone;
    private String mEmail;
    private String mDateOfBirdth;
    private String mOccupation;

    public Contact() {
        mId = ++mCount;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String surname) {
        mSurname = surname;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getDateOfBirdth() {
        return mDateOfBirdth;
    }

    public void setDateOfBirdth(String dateOfBirdth) {
        mDateOfBirdth = dateOfBirdth;
    }

    public String getOccupation() {
        return mOccupation;
    }

    public void setOccupation(String occupation) {
        mOccupation = occupation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Contact contact = (Contact) o;
        return mId == contact.mId;
    }

    @Override
    public int hashCode() {
        return mId;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "Id = " + mId +
                ", Name = " + mName + '\'' +
                ", Surname = " + mSurname + '\'' +
                ", Phone = " + mPhone + '\'' +
                ", Email = " + mEmail + '\'' +
                ", Date Of Birdth = " + mDateOfBirdth +
                ", Occupation = " + mOccupation + '\'' +
                '}';
    }
}
