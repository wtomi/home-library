package wojtowicz.tomi.booklibrary.services;

import wojtowicz.tomi.booklibrary.domain.*;

import java.util.List;

public interface LibraryService extends CRUDService<Library> {
    Library getByOwnerUsername(String username);
    Book createNewBook(Book book, String username);
    Book addExistingBook(Integer bookId, String username);
    List<BookData> getBookDataByLibraryOwnerUsername(String username);
    Invitation createInvitation(String addedUserEmail, Library library);
    Library addGuestToLibrary(Integer libraryId, User user);
    Library getByIdAndGuestsUsername(Integer id, String username);}
