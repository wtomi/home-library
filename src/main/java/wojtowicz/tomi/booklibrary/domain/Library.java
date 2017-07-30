package wojtowicz.tomi.booklibrary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Getter
@Setter
@Entity
public class Library extends AbstractDomainClass {

    @OneToOne(mappedBy = "library")
    private User owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "library")
    private List<BookData> books;
}
