package wojtowicz.tomi.booklibrary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class Library extends AbstractDomainClass {

    @OneToOne(mappedBy = "library")
    User owner;
}
