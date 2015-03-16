package by.aangurets.contacts;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import by.aangurets.contacts.model.Contact;

public class GenerationOfContact {

    private static String[] names =
            new String[]{"John", "Paul", "Mike", "Jason", "Kasper", "Adam", "Alberto"};
    public static String[] surnames =
            new String[]{"Smith", "Addison", "Johnson", "Williams", "Crawford", "Holmes", "Mills"};

    static final SimpleDateFormat SIMPLE_DATE_FORMAT =
            new SimpleDateFormat("dd-mm-yyyy", Locale.getDefault());

    public static Contact generateContact() {
        Random random = new Random();
        int randomPhone = random.nextInt(9000000);
        int randomId = random.nextInt(20);
        int randomName = random.nextInt(6);
        Contact contact = new Contact();
        contact.setName(names[randomName]);
        contact.setSurname(surnames[randomName]);
        contact.setDateOfBirdth(SIMPLE_DATE_FORMAT.format(new Date()));
        contact.setPhone("" + randomPhone);
        contact.setEmail(names[randomName] + "_" + surnames[randomName] + randomId + "@gmail.com");
        contact.setOccupation("Junior Android Developer");
        return contact;
    }
}
