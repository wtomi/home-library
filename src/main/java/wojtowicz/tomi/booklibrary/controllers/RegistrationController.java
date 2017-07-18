package wojtowicz.tomi.booklibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wojtowicz.tomi.booklibrary.domain.User;
import wojtowicz.tomi.booklibrary.dto.UserDto;
import wojtowicz.tomi.booklibrary.registration.event.OnRegistrationCompleteEvent;
import wojtowicz.tomi.booklibrary.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by tommy on 7/17/2017.
 */
@Controller
public class RegistrationController {

    private static final String MODEL_ATTRIBUTE_USER = "user";

    private static final String VIEW_REGISTER = "register";

    private static final String VIEW_SUCCESSFUL_REGISTER = "successRegister";

    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Model model) {
        final UserDto userDto = new UserDto();
        model.addAttribute(MODEL_ATTRIBUTE_USER, userDto);
        return VIEW_REGISTER;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPagePost(@ModelAttribute(MODEL_ATTRIBUTE_USER) @Valid final UserDto userDto,
                                   BindingResult bindingResult, final HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return VIEW_REGISTER;
        }
        final User registeredUser = userService.registerNewUser(userDto);
        try {
            final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser, appUrl, request.getLocale()));
        } catch (final Exception ex) {
            return VIEW_REGISTER;
        }

        return VIEW_SUCCESSFUL_REGISTER;
    }

}
