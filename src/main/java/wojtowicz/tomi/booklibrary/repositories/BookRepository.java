package wojtowicz.tomi.booklibrary.repositories;

import org.springframework.data.repository.CrudRepository;
import wojtowicz.tomi.booklibrary.domain.Book;

public interface BookRepository extends CrudRepository<Book, Integer>{
    Book findByAuthorFirstNameAndAuthorLastNameAllIgnoreCase(String firstName, String lastName);
    Book findByTitle(String title);
}
