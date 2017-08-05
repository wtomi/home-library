package wojtowicz.tomi.booklibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import wojtowicz.tomi.booklibrary.utils.UrlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;

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

    @Transactional
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String registerPagePost(@ModelAttribute(MODEL_ATTRIBUTE_USER) @Valid final UserDto userDto,
                                   BindingResult bindingResult, final HttpServletRequest request, Model model) {
        if (bindingResult.hasErrors()) {
            return VIEW_REGISTER;
        }
        final User registeredUser = userService.registerNewUser(userDto);
        final String appUrl = UrlUtils.getAppUrl(request);
        applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser, appUrl, request.getLocale()));

        model.addAttribute("message", "You have successfully register. We sent you an email with confirmation link.");
        return VIEW_SUCCESSFUL_REGISTER;
    }

    @ExceptionHandler(MailException.class)
    public String mailException(Model model) {
        model.addAttribute("message", "Sorry, we were unable to send email to the given address");
        return "errorMassage";
    }

    @RequestMapping("registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token, RedirectAttributes redirectAttributes) throws ResourceNotFoundException {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (tokenNotNull(verificationToken) && tokenNotExpired(verificationToken)) {
            activateUser(verificationToken);
            redirectAttributes.addFlashAttribute("message", "Your registration has been confirmed successfully.");
        } else if (!tokenNotNull(verificationToken)) {
            redirectAttributes.addFlashAttribute("message", "Confirmation link is invalid");
            return "redirect:/confirmError";
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

    private boolean tokenNotNull(VerificationToken verificationToken) {
        return !(verificationToken == null);
    }

    private boolean tokenNotExpired(VerificationToken verificationToken) {
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
