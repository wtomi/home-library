package wojtowicz.tomi.booklibrary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
public class Ownership extends AbstractDomainClass {

    @ManyToOne
    @JoinColumn(nullable = false)
    private BookData bookData;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownership")
    private List<Rental> rentals;

    @OneToOne
    @JoinColumn
    private Rental currentRental;
}
