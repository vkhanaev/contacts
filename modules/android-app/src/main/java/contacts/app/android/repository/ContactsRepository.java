package contacts.app.android.repository;

import java.util.List;

import android.accounts.Account;
import contacts.app.android.model.Contact;

/**
 * Remote repository that stores contacts.
 */
public interface ContactsRepository {

    /**
     * Finds contacts of people in location of user.
     */
    List<Contact> findByLocation(Account account) throws RepositoryException;

}
