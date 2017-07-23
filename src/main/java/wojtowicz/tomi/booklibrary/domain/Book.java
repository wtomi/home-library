package wojtowicz.tomi.booklibrary.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Book {

    @NotNull
    @NotEmpty
    String title;

    @NotNull
    @NotEmpty
    String author;
}
