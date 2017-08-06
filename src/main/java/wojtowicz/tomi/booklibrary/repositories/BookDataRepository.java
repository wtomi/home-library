package wojtowicz.tomi.booklibrary.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import wojtowicz.tomi.booklibrary.domain.Book;
import wojtowicz.tomi.booklibrary.domain.BookData;
import wojtowicz.tomi.booklibrary.domain.Library;

import java.util.List;

public interface BookDataRepository extends CrudRepository<BookData, Integer> {
    BookData findByLibraryAndBook(Library library, Book book);

    BookData findByLibraryIdAndBookId(Integer libraryId, Integer bookId);

    BookData findByLibraryOwnerUsernameAndBookId(String username, Integer id);

    List<BookData> findByLibraryOwnerUsername(String username);

    List<BookData> findByLibraryIdAndBookTitleContainingIgnoreCaseAndBookAuthorFirstNameContainingIgnoreCaseAndBookAuthorLastNameContainingIgnoreCase(
            Integer id, String title, String authorFirstName, String authorLastName);

    @Query("SELECT d FROM BookData d WHERE d.library.id = :id AND " +
            "(UPPER(d.book.title) LIKE '%'||UPPER(:searchPhrase)||'%' OR " +
            "UPPER(d.book.authorFirstName) LIKE '%'||UPPER(:searchPhrase)||'%' OR " +
            "UPPER(d.book.authorLastName) LIKE '%'||UPPER(:searchPhrase)||'%')")
    List<BookData> findByLibraryIdAndSearchPhrase(@Param("id") Integer id, @Param("searchPhrase") String searchPhrase);

    @Query("SELECT DISTINCT d FROM BookData d INNER JOIN d.ownerships o WHERE d.library.owner.username = :username AND " +
            "o.currentRental IS NULL")
    List<BookData> findNotLentBooks(@Param("username") String username);

    @Query("SELECT DISTINCT d FROM BookData d INNER JOIN d.ownerships o WHERE d.library.owner.username = :username AND " +
            "o.currentRental IS NOT NULL")
    List<BookData> findLentBooks(@Param("username") String username);

    @Query("SELECT DISTINCT d FROM BookData d INNER JOIN d.rentals WHERE d.library.owner.username = :username")
    List<BookData> findBorrowedBooks(@Param("username") String username);
}
