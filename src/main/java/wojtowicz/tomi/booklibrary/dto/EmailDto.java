package wojtowicz.tomi.booklibrary.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import wojtowicz.tomi.booklibrary.validation.ValidEmail;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EmailDto {

    @NotNull
    @NotEmpty
    @ValidEmail
    String email;
}
