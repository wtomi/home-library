package wojtowicz.tomi.booklibrary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Rental extends AbstractDomainClass {

    public Rental() {}

    public Rental(Ownership ownership, BookData bookData, int days) {
        this.ownership = ownership;
        this.bookData = bookData;
        startDate = LocalDate.now();
        returnDate = startDate.plusDays(days);
    }

    @ManyToOne
    @JoinColumn(nullable = false)
    private Ownership ownership;

    @ManyToOne
    @JoinColumn(nullable = false)
    private BookData bookData;

    LocalDate startDate;

    LocalDate returnDate;
}
