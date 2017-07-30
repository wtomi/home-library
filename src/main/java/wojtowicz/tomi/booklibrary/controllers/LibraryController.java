package wojtowicz.tomi.booklibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wojtowicz.tomi.booklibrary.domain.Book;
import wojtowicz.tomi.booklibrary.domain.Library;
import wojtowicz.tomi.booklibrary.domain.User;
import wojtowicz.tomi.booklibrary.services.BookService;
import wojtowicz.tomi.booklibrary.services.LibraryService;
import wojtowicz.tomi.booklibrary.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class LibraryController {

    private BookService bookService;

    private UserService userService;

    private LibraryService libraryService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @RequestMapping("/library")
    public String library(Model model, Principal principal) {

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

        if(bindingResult.hasErrors()) {
            return "createBook";
        }
        Library library = libraryService.getByOwnerUsername(principal.getName());



        return "redirect:/library";
    }
}
