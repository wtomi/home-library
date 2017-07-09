package wojtowicz.tomi.booklibrary.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wojtowicz.tomi.booklibrary.domain.User;
import wojtowicz.tomi.booklibrary.services.UserService;

/**
 * Created by tommy on 7/9/2017.
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("userToUserDetails")
    private Converter<User, UserDetails> userToUserDetailsConverter;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.getByUsername(s);
        return userToUserDetailsConverter.convert(user);
    }
}
