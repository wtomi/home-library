package wojtowicz.tomi.booklibrary.adduser.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import wojtowicz.tomi.booklibrary.adduser.event.OnAddUserSuccess;
import wojtowicz.tomi.booklibrary.services.LibraryService;

@Component
public class AddUserListener implements ApplicationListener<OnAddUserSuccess> {

    private JavaMailSender javaMailSender;

    private Environment environment;

    private LibraryService libraryService;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Autowired
    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public void onApplicationEvent(OnAddUserSuccess event) {
        libraryService.createInvitation(event.getAddedUserEmail(), event.getLibrary());
        final SimpleMailMessage email = constructEmailMessage(event);
        javaMailSender.send(email);
    }

    private SimpleMailMessage constructEmailMessage(final OnAddUserSuccess event) {
        final String recipientAddress = event.getAddedUserEmail();
        final String subject = "Invitation to the library";
        final String confirmationUrl = event.getAppUrl() + "/invitation.html?library=" + event.getLibrary().getId();
        final String message = "Click link below to join library:\r\n" + confirmationUrl +
                "\nIf you don't have an account, please register here: " + event.getAppUrl() + "/register";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom(environment.getProperty("support.email"));
        return email;
    }
}



