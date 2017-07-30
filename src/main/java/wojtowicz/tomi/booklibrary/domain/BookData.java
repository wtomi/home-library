package wojtowicz.tomi.booklibrary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class BookData extends AbstractDomainClass {

    public BookData(Book book, Library library) {
        this.book = book;
        this.library = library;
    }

    public BookData() {}

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
