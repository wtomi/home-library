package wojtowicz.tomi.booklibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wojtowicz.tomi.booklibrary.domain.User;
import wojtowicz.tomi.booklibrary.domain.VerificationToken;
import wojtowicz.tomi.booklibrary.dto.UserDto;
import wojtowicz.tomi.booklibrary.exceptions.UserAlreadyExistsException;
import wojtowicz.tomi.booklibrary.registration.event.OnRegistrationCompleteEvent;
import wojtowicz.tomi.booklibrary.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;

/**
 * Created by tommy on 7/17/2017.
 */
@Controller
public class RegistrationController {

    private static final String MODEL_ATTRIBUTE_USER = "user";

    private static final String VIEW_REGISTER = "register";

    private static final String VIEW_SUCCESSFUL_REGISTER = "successRegister";

    private ApplicationEventPublisher applicationEventPublisher;

    private UserService userService;

    @Autowired
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String registerPage(Model model) {
        final UserDto userDto = new UserDto();
        model.addAttribute(MODEL_ATTRIBUTE_USER, userDto);
        return VIEW_REGISTER;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String registerPagePost(@ModelAttribute(MODEL_ATTRIBUTE_USER) @Valid final UserDto userDto,
                                   BindingResult bindingResult, final HttpServletRequest request, Model model) {
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

        model.addAttribute("message", "You have successfully register. We sent you an email with confirmation link.");
        return VIEW_SUCCESSFUL_REGISTER;
    }


    @RequestMapping("registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (tokenIsValid(verificationToken)) {
            activateUser(verificationToken);
            redirectAttributes.addFlashAttribute("message", "Your registration has been confirmed successfully.");
        } else {
            redirectAttributes.addFlashAttribute("expired", true);
            redirectAttributes.addFlashAttribute("token", verificationToken);
            redirectAttributes.addFlashAttribute("message", "Sorry, time for verification has expired. You can register again. Remember to confirm registration within 24 hours.");
            return "redirect:/confirmError";
        }
        return "redirect:/confirmSuccess";
    }

    private void activateUser(VerificationToken verificationToken) {
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userService.saveOrUpdate(user);
    }

    private boolean tokenIsValid(VerificationToken verificationToken) {
        if (verificationToken == null)
            return false;
        final Calendar cal = Calendar.getInstance();
        return cal.getTime().getTime() - verificationToken.getExpiryDate().getTime() < 0;
    }

    @RequestMapping("confirmSuccess")
    public String confirmSuccess() {
        return "confirmSuccess";
    }

    @RequestMapping("confirmError")
    public String confirmError() {
        return "confirmError";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String registerError(Model model) {
        model.addAttribute("message", "User with given username or email already exists. Try to sign up with different details :)");
        return "registerError";
    }
}
