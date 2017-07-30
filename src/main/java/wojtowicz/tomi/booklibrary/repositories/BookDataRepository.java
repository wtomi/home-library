package wojtowicz.tomi.booklibrary.repositories;

import org.springframework.data.repository.CrudRepository;
import wojtowicz.tomi.booklibrary.domain.Book;
import wojtowicz.tomi.booklibrary.domain.BookData;
import wojtowicz.tomi.booklibrary.domain.Library;

import java.util.List;

public interface BookDataRepository extends CrudRepository<BookData, Integer> {
    BookData findByLibraryAndBook(Library library, Book book);
    List<BookData> findByLibraryOwnerUsername(String username);
}
