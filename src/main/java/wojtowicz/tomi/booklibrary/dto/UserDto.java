package wojtowicz.tomi.booklibrary.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by tommy on 7/17/2017.
 */

//TODO custom validation for matching passwords
@Getter
@Setter
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
