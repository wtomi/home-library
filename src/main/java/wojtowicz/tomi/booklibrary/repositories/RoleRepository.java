package wojtowicz.tomi.booklibrary.repositories;

import org.springframework.data.repository.CrudRepository;
import wojtowicz.tomi.booklibrary.domain.Role;

/**
 * Created by tommy on 7/9/2017.
 */
public interface RoleRepository extends CrudRepository<Role, Integer>{
}
