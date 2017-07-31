package wojtowicz.tomi.booklibrary.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import wojtowicz.tomi.booklibrary.validation.ValidEmail;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"addedUserEmail", "library_id"}))
public class Invitation extends AbstractDomainClass {

    public Invitation(String addedUserEmail, Library library) {
        this.addedUserEmail = addedUserEmail;
        this.library = library;
    }

    public Invitation() {
    }

    @NotNull
    @NotEmpty
    @ValidEmail
    private String addedUserEmail;

    @ManyToOne
    @JoinColumn(name = "library_id", nullable = false)
    private Library library;
}
