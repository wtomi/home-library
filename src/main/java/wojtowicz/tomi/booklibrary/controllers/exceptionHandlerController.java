package wojtowicz.tomi.booklibrary.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import wojtowicz.tomi.booklibrary.exceptions.NotFoundException;

@ControllerAdvice
public class exceptionHandlerController {

    @ExceptionHandler({NotFoundException.class})
    public String notFoundException(NotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "notFound";
    }
}
