package wojtowicz.tomi.booklibrary.repositories;

import org.springframework.data.repository.CrudRepository;
import wojtowicz.tomi.booklibrary.domain.User;

public interface UserRepository extends CrudRepository<User, Integer>{
    User findByUsername(String username);
    User findByEmail(String email);
}
