package wojtowicz.tomi.booklibrary.repositories;

import org.springframework.data.repository.CrudRepository;
import wojtowicz.tomi.booklibrary.domain.Book;
import wojtowicz.tomi.booklibrary.domain.Library;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findByAuthorFirstNameAndAuthorLastNameAllIgnoreCase(String firstName, String lastName);

    List<Book> findByTitleIgnoreCase(String title);

    List<Book> findByTitleContainingIgnoreCase(String word);

    List<Book> findByTitleContainingOrAuthorFirstNameContainingOrAuthorLastNameContainingAllIgnoreCase(
            String title, String authorFirstName, String authorLastName);

    List<Book> findByTitleContainingAndAuthorFirstNameContainingAndAuthorLastNameContainingAllIgnoreCase(
            String title, String authorFirstName, String authorLastName);
}
