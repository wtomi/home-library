package wojtowicz.tomi.booklibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wojtowicz.tomi.booklibrary.adduser.event.OnAddUserSuccess;
import wojtowicz.tomi.booklibrary.domain.*;
import wojtowicz.tomi.booklibrary.dto.EmailDto;
import wojtowicz.tomi.booklibrary.dto.RentalDto;
import wojtowicz.tomi.booklibrary.exceptions.NotFoundException;
import wojtowicz.tomi.booklibrary.services.BookService;
import wojtowicz.tomi.booklibrary.services.LibraryService;
import wojtowicz.tomi.booklibrary.services.UserService;
import wojtowicz.tomi.booklibrary.utils.UrlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LibraryController {

    private UserService userService;

    private BookService bookService;

    private LibraryService libraryService;

    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Autowired
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/library")
    public String library(Model model, Principal principal,
                          @RequestParam(required = false) String search,
                          @RequestParam(required = false) String title,
                          @RequestParam(required = false) String authorFirstName,
                          @RequestParam(required = false) String authorLastName,
                          @RequestParam(required = false) String scope) {
        List<BookData> bookDataList;
        List<BookData> borrowedBookDataList = Collections.emptyList();
        List<BookData> lentBookDataList = Collections.emptyList();
        if (search != null) {
            Library library = libraryService.getByOwnerUsername(principal.getName());
            bookDataList = libraryService.searchBooksInLibrary(library.getId(), search);
        } else if (title != null) {
            Library library = libraryService.getByOwnerUsername(principal.getName());
            bookDataList = libraryService.searchBooksInLibrary(library.getId(), title, authorFirstName, authorLastName);
        } else {
            bookDataList = libraryService.getNotLentBookDataByOwnerUsername(principal.getName());
            lentBookDataList = libraryService.getLentBooksByOwnerUsername(principal.getName());
            borrowedBookDataList = libraryService.getBorrowedBooksByOwnerUsername(principal.getName());
        }
        model.addAttribute("books", bookDataList);
        model.addAttribute("borrowedBooks", borrowedBookDataList);
        model.addAttribute("lentBooks", lentBookDataList);
        model.addAttribute("owner", true);
        return "library";
    }

    @RequestMapping("/library/{libraryId}")
    public String libraryById(Model model, Principal principal, @PathVariable Integer libraryId,
                              @RequestParam(required = false) String search,
                              @RequestParam(required = false) String title,
                              @RequestParam(required = false) String authorFirstName,
                              @RequestParam(required = false) String authorLastName,
                              @RequestParam(required = false) String scope) {
        Library library = libraryService.getByIdAndGuestsUsername(libraryId, principal.getName());
        List<BookData> bookDataList;
        if (library == null) {
            throw new NotFoundException("Library not found");
        }
        if (search != null) {
            bookDataList = libraryService.searchBooksInLibrary(libraryId, search);
        } else if (title != null) {
            bookDataList = libraryService.searchBooksInLibrary(libraryId, title, authorFirstName, authorLastName);
        } else {
            bookDataList = library.getBooks();
        }
        model.addAttribute("books", bookDataList);
        model.addAttribute("libraryId", libraryId);
        return "library";
    }

    @RequestMapping(value = "/library/add", method = RequestMethod.GET)
    public String addBookGet(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "createBook";
    }

    @RequestMapping(value = "/library/add", method = RequestMethod.POST)
    public String addBookPost(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            return "createBook";
        }
        List<Book> similarBooks = bookService.getByTitleIgnoreCase(book.getTitle());
        if (!similarBooks.isEmpty()) {
            model.addAttribute("books", similarBooks);
            model.addAttribute("book", book);
            return "similarBooks";
        }
        libraryService.createNewBook(book, principal.getName());
        return "redirect:/library";
    }

    @RequestMapping(value = "/library/add/force", method = RequestMethod.POST)
    public String addBookForce(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            return "createBook";
        }
        libraryService.createNewBook(book, principal.getName());
        return "redirect:/library";
    }

    @RequestMapping(value = "/library/add/{bookId}", method = RequestMethod.GET)
    public String addExistingBook(@PathVariable(value = "bookId") final int bookId, Principal principal) {
        libraryService.addExistingBook(bookId, principal.getName());
        return "redirect:/library";
    }

    @RequestMapping(value = "/library/adduser", method = RequestMethod.POST)
    public String addUser(HttpServletRequest request, Model model, @ModelAttribute @Valid final EmailDto emailDto,
                          BindingResult bindingResult, Principal principal) {
        User user = userService.getByUsername(principal.getName());
        if (user.getEmail().equals(emailDto.getEmail())) {
            bindingResult.rejectValue("email", "You have access to your own library so what's the point ;)");
        }
        if (bindingResult.hasErrors()) {
            return "redirect:/library";
        }
        Library library = libraryService.getByOwnerUsername(principal.getName());
        String appUrl = UrlUtils.getAppUrl(request);
        applicationEventPublisher.publishEvent(new OnAddUserSuccess(emailDto.getEmail(), library, appUrl));
        model.addAttribute("message", "User added successfully");
        return "redirect:/library.html?useradded";
    }

    @ExceptionHandler(MailException.class)
    public String mailException(Model model) {
        model.addAttribute("message", "Sorry, we were unable to send email to the given address");
        return "errorMassage";
    }

    @RequestMapping(value = "/invitation")
    public String invitation(@RequestParam("library") final Integer libraryId, Model model, Principal principal) {
        User user = userService.getByUsername(principal.getName());
        libraryService.addGuestToLibrary(libraryId, user);
        model.addAttribute("message", "You have access to the library, enjoy");
        return "invitationSuccess";
    }

    @RequestMapping("/libraries")
    public String libraries(Model model, Principal principal) {
        User user = userService.getByUsername(principal.getName());
        List<Library> libraries = user.getFriendsLibraries();
        model.addAttribute("libraries", libraries);
        return "libraries";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/books")
    public String books(Model model,
                        @RequestParam(required = false) String search,
                        @RequestParam(required = false) String title,
                        @RequestParam(required = false) String authorFirstName,
                        @RequestParam(required = false) String authorLastName,
                        @RequestParam(required = false) String scope) {
        List<Book> books;
        if (search != null) {
            books = bookService.searchBooks(search);
        } else if (title != null) {
            books = bookService.searchBooks(title, authorFirstName, authorLastName);
        } else {
            books = (List<Book>) bookService.listAll();
        }
        model.addAttribute("books", books);
        return "books";
    }

    @RequestMapping(value = "/borrow", method = RequestMethod.GET)
    public String borrow(Model model, Principal principal,
                         @RequestParam("library") Integer libraryId,
                         @RequestParam("book") Integer bookId) {
        prepareBorrowModel(model, libraryId, bookId);
        return "borrow";
    }

    @RequestMapping(value = "/borrow", method = RequestMethod.POST)
    public String borrowPost(Model model, @Valid RentalDto rentalDto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            prepareBorrowModel(model, rentalDto.getLibraryId(), rentalDto.getBookId());
            return "borrow";
        }
        Library borrowerLibrary = libraryService.getByOwnerUsername(principal.getName());
        libraryService.addRental(rentalDto.getLibraryId(), borrowerLibrary.getId(),
                rentalDto.getBookId(), rentalDto.getDays());
        model.addAttribute("message", "Book has been borrowed, congratulations");
        return "successMessage";
    }

    private void prepareBorrowModel(Model model, Integer libraryId, Integer bookId) {
        if (libraryId != null && bookId != null) {
            Book book = bookService.getById(bookId);
            model.addAttribute("book", book);
            model.addAttribute("libraryId", libraryId);
        } else
            throw new NotFoundException("Not Found");
    }
}
