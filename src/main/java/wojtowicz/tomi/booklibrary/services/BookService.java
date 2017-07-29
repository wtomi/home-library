package wojtowicz.tomi.booklibrary.services;

import wojtowicz.tomi.booklibrary.domain.Book;

import java.util.List;

public interface BookService extends CRUDService<Book> {
    List<Book> getByAuthorFirstNameAndAuthorLastNameAllIgnoreCase(String firstName, String lastName);
    List<Book> getByTitleIgnoreCase(String title);
    List<Book> getByTitleContainingIgnoreCase(String word);
    List<Book> getByLibraryOwnerUsername(String username);
}
