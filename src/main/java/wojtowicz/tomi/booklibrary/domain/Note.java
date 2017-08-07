package wojtowicz.tomi.booklibrary.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Note extends AbstractDomainClass{

    public Note() {}

    public Note(String note, BookData bookData) {
        this.note = note;
        this.bookData = bookData;
    }

    @NotNull
    @NotEmpty
    String note;

    @ManyToOne
    @JoinColumn(nullable = false)
    BookData bookData;
}
