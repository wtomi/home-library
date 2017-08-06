package wojtowicz.tomi.booklibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wojtowicz.tomi.booklibrary.domain.*;
import wojtowicz.tomi.booklibrary.exceptions.NotFoundException;
import wojtowicz.tomi.booklibrary.repositories.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryServiceJPA implements LibraryService {

    private LibraryRepository libraryRepository;

    private BookService bookService;

    private BookDataRepository bookDataRepository;

    private OwnershipRepository ownershipRepository;

    private InvitationRepository invitationRepository;

    private RentalRepository rentalRepository;

    @Autowired
    public void setLibraryRepository(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setBookDataRepository(BookDataRepository bookDataRepository) {
        this.bookDataRepository = bookDataRepository;
    }

    @Autowired
    public void setOwnershipRepository(OwnershipRepository ownershipRepository) {
        this.ownershipRepository = ownershipRepository;
    }

    @Autowired
    public void setInvitationRepository(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    @Autowired
    public void setRentalRepository(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    public List<?> listAll() {
        List<Library> libraries = new ArrayList<>();
        libraryRepository.findAll().forEach(libraries::add);
        return libraries;
    }

    @Override
    public Library getById(Integer id) {
        return libraryRepository.findOne(id);
    }

    @Override
    public Library saveOrUpdate(Library domainObject) {
        return libraryRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        libraryRepository.delete(id);
    }

    @Override
    public Library getByOwnerUsername(String username) {
        return libraryRepository.findByOwnerUsername(username);
    }

    @Override
    @Transactional
    public Book createNewBook(Book book, String username) {
        Book createdBook = bookService.saveOrUpdate(book);
        addBookToLibrary(book, username);
        return createdBook;
    }

    @Override
    @Transactional
    public Book addExistingBook(Integer bookId, String username) {
        Book foundBook = bookService.getById(bookId);
        if (foundBook == null) throw new NotFoundException("Book not found");
        addBookToLibrary(foundBook, username);
        return foundBook;
    }

    private void addBookToLibrary(Book book, String username) {
        Library library = this.getByOwnerUsername(username);
        BookData bookData = bookDataRepository.findByLibraryAndBook(library, book);
        if (bookData == null) {
            bookData = new BookData(book, library);
            bookDataRepository.save(bookData);
        }
        Ownership ownership = new Ownership(bookData);
        ownershipRepository.save(ownership);
    }

    @Override
    public List<BookData> getBookDataByLibraryOwnerUsername(String username) {
        return bookDataRepository.findByLibraryOwnerUsername(username);
    }

    @Override
    public List<BookData> getNotLentBookDataByOwnerUsername(String username) {
        return bookDataRepository.findNotLentBooks(username);
    }

    @Override
    public List<BookData> getLentBooksByOwnerUsername(String name) {
        return bookDataRepository.findLentBooks(name);
    }

    @Override
    public List<BookData> getBorrowedBooksByOwnerUsername(String name) {
        return bookDataRepository.findBorrowedBooks(name);
    }

    @Override
    public Invitation createInvitation(String addedUserEmail, Library library) {
        Invitation invitation = invitationRepository.findByAddedUserEmailAndLibrary(addedUserEmail, library);
        if (invitation == null) {
            invitation = new Invitation(addedUserEmail, library);
            invitationRepository.save(invitation);
        }
        return invitation;
    }

    @Override
    @Transactional
    public Library addGuestToLibrary(Integer libraryId, User user) {
        Library library = getById(libraryId);
        Invitation invitation = invitationRepository.findByAddedUserEmailAndLibrary(user.getEmail(), library);
        if (invitation == null)
            throw new NotFoundException();
        invitationRepository.delete(invitation);
        library.addGuest(user);
        return this.saveOrUpdate(library);
    }

    @Override
    public Library getByIdAndGuestsUsername(Integer id, String username) {
        return libraryRepository.findByIdAndGuestsUsername(id, username);
    }

    @Override
    public List<BookData> searchBooksInLibrary(Integer id, String title, String authorFirstName, String authorLastName) {
        return bookDataRepository.findByLibraryIdAndBookTitleContainingIgnoreCaseAndBookAuthorFirstNameContainingIgnoreCaseAndBookAuthorLastNameContainingIgnoreCase(
                id, title, authorFirstName, authorLastName);
    }

    @Override
    public List<BookData> searchBooksInLibrary(Integer id, String searchPhrase) {
        return bookDataRepository.findByLibraryIdAndSearchPhrase(
                id, searchPhrase);
    }

    @Transactional
    @Override
    public Rental addRental(Integer ownerLibraryId, Integer borrowerLibraryId, Integer bookId, int days) {
        if(borrowerLibraryId.equals(ownerLibraryId))
            throw new NotFoundException("Not Found");
        List<Ownership> ownerships = ownershipRepository.findPossibleToBorrowForUpdate(ownerLibraryId, bookId);
        if (ownerships.isEmpty())
            throw new NotFoundException("Sorry, we could not find book in the library");
        Ownership ownership = ownerships.get(0);
        BookData bookData = getBookData(borrowerLibraryId, bookId);

        Rental rental = new Rental(ownership, bookData, days);
        rental = rentalRepository.save(rental);

        ownership.setCurrentRental(rental);
        ownershipRepository.save(ownership);

        return rental;
    }

    @Override
    public BookData getBookDataByLibraryOwnerUsernameAndBookId(String username, Integer bookId) {
        return bookDataRepository.findByLibraryOwnerUsernameAndBookId(username, bookId);
    }

    private BookData getBookData(Integer borrowerLibraryId, Integer bookId) {
        BookData bookData = bookDataRepository.findByLibraryIdAndBookId(borrowerLibraryId, bookId);
        if (bookData == null)
            bookData = createNewBookData(borrowerLibraryId, bookId);
        return bookData;
    }

    private BookData createNewBookData(Integer borrowerLibraryId, Integer bookId) {
        Library library = getById(borrowerLibraryId);
        Book book = bookService.getById(bookId);
        BookData bookData = new BookData(book, library);
        return bookDataRepository.save(bookData);
    }
}
