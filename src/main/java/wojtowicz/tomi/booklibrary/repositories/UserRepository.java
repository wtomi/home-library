package wojtowicz.tomi.booklibrary.repositories;

import org.springframework.data.repository.CrudRepository;
import wojtowicz.tomi.booklibrary.domain.User;

/**
 * Created by tommy on 7/9/2017.
 */
public interface UserRepository extends CrudRepository<User, Integer>{
    User findByUsername(String username);
}
