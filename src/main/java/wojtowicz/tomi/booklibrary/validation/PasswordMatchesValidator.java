package wojtowicz.tomi.booklibrary.validation;

import wojtowicz.tomi.booklibrary.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by tommy on 7/17/2017.
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDto> {

    @Override
    public void initialize(PasswordMatches passwordMatches) {

    }

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        return passwordMatches(userDto);
    }

    boolean passwordMatches(UserDto userDto) {
        return userDto.getPassword().equals(userDto.getRepeatedPassword());
    }
}
