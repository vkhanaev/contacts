package grytsenko.contacts.service;

import grytsenko.contacts.model.Contact;
import grytsenko.contacts.repository.ContactsRepository;
import grytsenko.contacts.util.StringUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Searches a contacts.
 */
@Service
public class SearchContactsService {

    @Autowired
    ContactsRepository contactsRepository;

    /**
     * Finds contact of single person.
     * 
     * @return the found contact or <code>null</code> if contact was not found.
     */
    public Contact findByUserName(String userName) {
        return contactsRepository.findByUserName(userName);
    }

    /**
     * Finds contacts of people by location.
     */
    public List<Contact> findByLocation(String location) {
        if (StringUtils.isNullOrEmpty(location)) {
            throw new IllegalStateException("Location not defined.");
        }

        List<Contact> contacts = contactsRepository.findByLocation(location);

        return contacts;
    }

}
