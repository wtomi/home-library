package wojtowicz.tomi.booklibrary.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RentalDto {

    public RentalDto() {}

    public RentalDto(Integer libraryId, Integer bookId, Integer days) {
        this.libraryId = libraryId;
        this.bookId = bookId;
        this.days = days;
    }

    @NotNull
    @Min(0)
    private Integer bookId;

    @NotNull
    @Min(0)
    private Integer libraryId;

    @NotNull
    @Min(1)
    private Integer days;
}
