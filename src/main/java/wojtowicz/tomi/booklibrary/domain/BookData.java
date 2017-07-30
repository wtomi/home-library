package wojtowicz.tomi.booklibrary.domain;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

public class BookData extends AbstractDomainClass {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Library library;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookData")
    private List<Ownership> ownerships;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookData")
    private List<Rental> rentals;
}
