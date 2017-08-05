package wojtowicz.tomi.booklibrary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Library extends AbstractDomainClass {

    @OneToOne(mappedBy = "library")
    private User owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "library")
    private List<BookData> books;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    private List<User> guests;

    public void addGuest(User user) {
        if(!guests.contains(user)) {
            guests.add(user);
        }
    }
}
