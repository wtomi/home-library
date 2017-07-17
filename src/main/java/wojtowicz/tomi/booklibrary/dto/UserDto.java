package wojtowicz.tomi.booklibrary.dto;

import lombok.Getter;
import lombok.Setter;
import wojtowicz.tomi.booklibrary.validation.PasswordMatches;

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
    @Size(min = 1)
    private String name;

    @NotNull
    @Size(min = 1)
    private String email;

    //TODO custom validation
    @NotNull
    @Size(min = 1)
    private String password;

    @NotNull
    @Size(min = 1)
    private String repeatedPassword;
}
