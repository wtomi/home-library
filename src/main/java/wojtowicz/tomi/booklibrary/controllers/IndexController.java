package wojtowicz.tomi.booklibrary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tommy on 7/6/2017.
 */

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
