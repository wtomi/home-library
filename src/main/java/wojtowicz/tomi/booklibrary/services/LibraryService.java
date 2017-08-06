package wojtowicz.tomi.booklibrary.services;

import wojtowicz.tomi.booklibrary.domain.*;

import java.util.List;

public interface LibraryService extends CRUDService<Library> {
    Library getByOwnerUsername(String username);
    Book createNewBook(Book book, String username);
    Book addExistingBook(Integer bookId, String username);
    List<BookData> getBookDataByLibraryOwnerUsername(String username);
    List<BookData> getNotLentBookDataByOwnerUsername(String username);
    List<BookData> getLentBooksByOwnerUsername(String name);
    List<BookData> getBorrowedBooksByOwnerUsername(String name);
    Invitation createInvitation(String addedUserEmail, Library library);
    Library addGuestToLibrary(Integer libraryId, User user);
    Library getByIdAndGuestsUsername(Integer id, String username);
    List<BookData> searchBooksInLibrary(Integer id, String title, String authorFirstName, String authorLastName);
    List<BookData> searchBooksInLibrary(Integer id, String searchPhrase);
    Rental addRental(Integer ownerLibraryId, Integer borrowerLibraryId, Integer bookId, int days);
    BookData getBookDataByLibraryOwnerUsernameAndBookId(String username, Integer id);
}
