package wojtowicz.tomi.booklibrary.registration.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import wojtowicz.tomi.booklibrary.domain.User;
import wojtowicz.tomi.booklibrary.registration.event.OnRegistrationCompleteEvent;
import wojtowicz.tomi.booklibrary.services.UserService;

import java.util.UUID;

/**
 * Created by tommy on 7/16/2017.
 */
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>{

    private UserService userService;

    private MessageSource messageSource;

    private JavaMailSender javaMailSender;

    private Environment environment;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);

        final SimpleMailMessage email = constructEmailMessage(event, user, token);
        javaMailSender.send(email);
    }

    private SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final User user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
//        final String message = messageSource.getMessage("message.regSucc", null, event.getLocale());
        final String message = "Example message";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(environment.getProperty("support.email"));
        return email;
    }
}
