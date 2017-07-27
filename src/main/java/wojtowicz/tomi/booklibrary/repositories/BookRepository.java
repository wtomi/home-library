package wojtowicz.tomi.booklibrary.repositories;

import org.springframework.data.repository.CrudRepository;
import wojtowicz.tomi.booklibrary.domain.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer>{
    List<Book> findByAuthorFirstNameAndAuthorLastNameAllIgnoreCase(String firstName, String lastName);
    List<Book> findByTitle(String title);
}
