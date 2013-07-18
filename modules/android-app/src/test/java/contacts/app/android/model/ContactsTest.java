package contacts.app.android.model;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class ContactsTest {

    @Test
    public void testJson() throws JSONException {
        String json = "{userName : jdoe, firstName : John, lastName : Doe, phone: 1234567890, mail: jdoe@test.com, location: US}";

        JSONObject jsonObject = new JSONObject(json);
        Contact contact = Contact.fromJson(jsonObject);

        assertEquals("jdoe", contact.getUserName());
    }

}
