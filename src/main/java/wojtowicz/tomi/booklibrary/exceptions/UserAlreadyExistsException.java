package wojtowicz.tomi.booklibrary.exceptions;

/**
 * Created by tommy on 7/22/2017.
 */
public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException() {
        super();
    }

    public  UserAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public  UserAlreadyExistsException(final String message) {
        super(message);
    }

    public  UserAlreadyExistsException(final Throwable cause) {
        super(cause);
    }
}
