package wojtowicz.tomi.booklibrary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wojtowicz.tomi.booklibrary.domain.Book;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class LibraryController {

    @RequestMapping("/library")
    public String library() {
        return "library";
    }

    @RequestMapping(value = "/library/add", method = RequestMethod.GET)
    public String addBookGet(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);

        return "createBook";
    }

    @RequestMapping(value = "/library/add", method = RequestMethod.POST)
    public String addBookPost(@ModelAttribute("book") @Valid Book book, Principal principal) {



        return "redirect:/library";
    }
}
