package wojtowicz.tomi.booklibrary.adduser.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import wojtowicz.tomi.booklibrary.domain.Library;

@Getter
public class OnAddUserSuccess extends ApplicationEvent {

    private final String addedUserEmail;
    private final Library library;
    private final String appUrl;

    public OnAddUserSuccess(String addedUserEmail, Library library, String appUrl) {
        super(addedUserEmail);

        this.addedUserEmail = addedUserEmail;
        this.library = library;
        this.appUrl = appUrl;
    }
}