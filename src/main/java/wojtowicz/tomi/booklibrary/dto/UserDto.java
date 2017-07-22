package wojtowicz.tomi.booklibrary.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import wojtowicz.tomi.booklibrary.validation.PasswordMatches;
import wojtowicz.tomi.booklibrary.validation.ValidEmail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by tommy on 7/17/2017.
 */

@Getter
@Setter
@PasswordMatches
public class UserDto {

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    //TODO custom validation
    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String repeatedPassword;
}
