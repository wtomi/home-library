package wojtowicz.tomi.booklibrary.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Book extends AbstractDomainClass {

    @NotNull
    @NotEmpty
    String title;

    @NotNull
    @NotEmpty
    String authorFirstName;

    @NotNull
    @NotEmpty
    String authorLastName;

    @NotNull
    @ManyToOne
    @JoinColumn
    Library library;
}
