package wojtowicz.tomi.booklibrary.services;

import wojtowicz.tomi.booklibrary.domain.User;
import wojtowicz.tomi.booklibrary.domain.VerificationToken;
import wojtowicz.tomi.booklibrary.dto.UserDto;

public interface UserService extends CRUDService<User>{
    User getByUsername(String username);

    User registerNewUser(UserDto userDto);

    VerificationToken createVerificationTokenForUser(User user);

    VerificationToken getVerificationToken(String token);
}
