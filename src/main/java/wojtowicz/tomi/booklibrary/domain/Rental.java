package wojtowicz.tomi.booklibrary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Rental extends AbstractDomainClass {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Ownership ownership;

    @ManyToOne
    @JoinColumn(nullable = false)
    private BookData bookData;
}
