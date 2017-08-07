package wojtowicz.tomi.booklibrary.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NoteDto {

    @NotNull
    @NotEmpty
    String note;

    @NotNull
    @Min(0)
    Integer bookId;
}
