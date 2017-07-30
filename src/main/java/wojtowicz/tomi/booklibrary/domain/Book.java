package wojtowicz.tomi.booklibrary.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
public class Book extends AbstractDomainClass {

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String authorFirstName;

    @NotNull
    @NotEmpty
    private String authorLastName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private List<BookData> bookDataList;
}
