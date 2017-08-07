package wojtowicz.tomi.booklibrary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"book_id", "library_id"}))
public class BookData extends AbstractDomainClass {

    public BookData() {}

    public BookData(Book book, Library library) {
        this.book = book;
        this.library = library;
    }

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "library_id", nullable = false)
    private Library library;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookData")
    private List<Ownership> ownerships;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookData")
    private List<Rental> rentals;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookData")
    private List<Note> notes;
}
