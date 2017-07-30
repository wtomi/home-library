package wojtowicz.tomi.booklibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wojtowicz.tomi.booklibrary.domain.Book;
import wojtowicz.tomi.booklibrary.domain.BookData;
import wojtowicz.tomi.booklibrary.services.BookService;
import wojtowicz.tomi.booklibrary.services.LibraryService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LibraryController {

    private BookService bookService;

    private LibraryService libraryService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @RequestMapping("/library")
    public String library(Model model, Principal principal) {
        List<BookData> bookDataList = libraryService.getByLibraryOwnerUsername(principal.getName());
        List<Book> books = bookDataList.stream()
                .map(BookData::getBook)
                .collect(Collectors.toList());
        model.addAttribute("books", books);
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
}
