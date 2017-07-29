package wojtowicz.tomi.booklibrary.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import wojtowicz.tomi.booklibrary.domain.User;
import wojtowicz.tomi.booklibrary.dto.UserDto;
import wojtowicz.tomi.booklibrary.services.RoleService;

@Component
public class UserDtoToUser implements Converter<UserDto, User>{

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public User convert(final UserDto userDto) {
        final User user = new User();
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        return user;
    }
}
