package wojtowicz.tomi.booklibrary.services;

import wojtowicz.tomi.booklibrary.domain.VerificationToken;

/**
 * Created by tommy on 7/18/2017.
 */
public interface VerificationTokenService extends CRUDService<VerificationToken>{
    VerificationToken getByToken(String token);
}
