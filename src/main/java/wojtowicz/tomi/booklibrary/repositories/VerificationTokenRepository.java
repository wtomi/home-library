package wojtowicz.tomi.booklibrary.repositories;

import org.springframework.data.repository.CrudRepository;
import wojtowicz.tomi.booklibrary.domain.VerificationToken;

/**
 * Created by tommy on 7/18/2017.
 */
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Integer>{
    VerificationToken findByToken(String token);
}
