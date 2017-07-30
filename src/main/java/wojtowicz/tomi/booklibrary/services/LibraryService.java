package wojtowicz.tomi.booklibrary.services;

import wojtowicz.tomi.booklibrary.domain.Book;
import wojtowicz.tomi.booklibrary.domain.BookData;
import wojtowicz.tomi.booklibrary.domain.Library;

import java.util.List;

public interface LibraryService extends CRUDService<Library> {
    Library getByOwnerUsername(String username);
    Book createNewBook(Book book, String username);
    Book addExistingBook(Integer bookId, String username);
    List<BookData> getByLibraryOwnerUsername(String username);
}
