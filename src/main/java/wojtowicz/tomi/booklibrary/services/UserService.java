package wojtowicz.tomi.booklibrary.services;

import wojtowicz.tomi.booklibrary.domain.User;

/**
 * Created by tommy on 7/9/2017.
 */
public interface UserService extends CRUDService<User>{
    User getByUsername(String username);
}
