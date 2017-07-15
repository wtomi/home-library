package wojtowicz.tomi.booklibrary.services;

import wojtowicz.tomi.booklibrary.domain.Role;

/**
 * Created by tommy on 7/9/2017.
 */
public interface RoleService extends CRUDService<Role>{
    Role getByRole(String role);
}
