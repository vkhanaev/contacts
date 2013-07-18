package contacts.app.android.service.account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Adds new account.
 */
public class AddAccountService extends Service {

    private AccountAutheticator autheticator;

    @Override
    public IBinder onBind(Intent intent) {
        if (autheticator == null) {
            autheticator = new AccountAutheticator(this);
        }
        return autheticator.getIBinder();
    }

}
