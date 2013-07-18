package contacts.app.android.repository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Base64;
import contacts.app.android.R;
import contacts.app.android.model.Contact;

/**
 * Remote repository that provides access through REST.
 */
public class ContactsRepositoryRest implements ContactsRepository {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private Context context;

    public ContactsRepositoryRest(Context context) {
        this.context = context;
    }

    public List<Contact> findByLocation(Account account)
            throws RepositoryException {
        HttpGet request = new HttpGet();
        String authHeader = createAuthHeader(account);
        request.setHeader(AUTHORIZATION_HEADER, authHeader);
        request.setURI(createUri(context.getString(R.string.searchUrl)));

        String json = sendRequest(request);

        try {
            return parseContacts(json);
        } catch (JSONException e) {
            throw new RepositoryException();
        }
    }

    private String createAuthHeader(Account account) {
        AccountManager accountManager = AccountManager.get(context);
        String username = account.name;
        String password = accountManager.getPassword(account);

        String credentials = username + ":" + password;
        String base64 = new String(Base64.encode(credentials.getBytes(),
                Base64.NO_WRAP));
        return "Basic " + base64;
    }

    private URI createUri(String url) throws RepositoryException {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new RepositoryException();
        }
    }

    private List<Contact> parseContacts(String json) throws JSONException {
        JSONArray jsonContacts = new JSONArray(json);
        List<Contact> contacts = new ArrayList<Contact>();
        for (int i = 0; i < jsonContacts.length(); ++i) {
            JSONObject jsonContact = jsonContacts.getJSONObject(i);
            contacts.add(Contact.fromJson(jsonContact));
        }
        return contacts;
    }

    private String sendRequest(HttpGet request) throws RepositoryException {
        HttpClient client = new DefaultHttpClient();
        try {
            HttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception exception) {
            throw new RepositoryException();
        }
    }

}
