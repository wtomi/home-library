package wojtowicz.tomi.booklibrary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LibraryController {

    @RequestMapping("/library")
    public String library() {
        return "library";
    }

    @RequestMapping("/library/add")
    public String addBook() {
        return "createBook";
    }
}
