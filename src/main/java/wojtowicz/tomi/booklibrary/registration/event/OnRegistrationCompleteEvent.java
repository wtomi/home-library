package wojtowicz.tomi.booklibrary.registration.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import wojtowicz.tomi.booklibrary.domain.User;

import java.util.Locale;

/**
 * Created by tommy on 7/16/2017.
 */
@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private User user;

    private String appUrl;

    private Locale locale;

    public OnRegistrationCompleteEvent(final User user, final String appUrl, final Locale locale) {
        super(user);

        this.user = user;
        this.appUrl = appUrl;
        this.locale = locale;
    }
}
